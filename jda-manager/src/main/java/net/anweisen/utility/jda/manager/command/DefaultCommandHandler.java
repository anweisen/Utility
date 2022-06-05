package net.anweisen.utility.jda.manager.command;

import net.anweisen.utility.common.collection.pair.Tuple;
import net.anweisen.utility.common.misc.ReflectionUtils;
import net.anweisen.utility.jda.command.context.CommandContext;
import net.anweisen.utility.jda.command.context.inject.Inject;
import net.anweisen.utility.jda.command.hook.Command;
import net.anweisen.utility.jda.command.hook.CommandScope;
import net.anweisen.utility.jda.command.hook.argument.Argument;
import net.anweisen.utility.jda.command.hook.argument.Description;
import net.anweisen.utility.jda.command.hook.argument.Optional;
import net.anweisen.utility.jda.command.hook.argument.choice.ChoiceChannels;
import net.anweisen.utility.jda.command.hook.argument.choice.ChoiceInt;
import net.anweisen.utility.jda.command.hook.argument.choice.ChoiceNumber;
import net.anweisen.utility.jda.command.hook.argument.choice.ChoiceString;
import net.anweisen.utility.jda.command.hook.argument.limit.MaxValue;
import net.anweisen.utility.jda.command.hook.argument.limit.MinValue;
import net.anweisen.utility.jda.manager.command.access.CommandAccessProvider;
import net.anweisen.utility.jda.manager.command.argument.ArgumentResolver;
import net.anweisen.utility.jda.manager.command.argument.ArgumentResolverRegistry;
import net.anweisen.utility.jda.manager.command.argument.DefaultArgumentResolverRegistry;
import net.anweisen.utility.jda.manager.command.cooldown.CooldownMemory;
import net.anweisen.utility.jda.manager.command.cooldown.DefaultCooldownMemory;
import net.anweisen.utility.jda.manager.command.handle.CommandHandle;
import net.anweisen.utility.jda.manager.command.handle.ReflectionCommandHandle;
import net.anweisen.utility.jda.manager.command.registered.ArgumentInfo;
import net.anweisen.utility.jda.manager.command.registered.CommandInfo;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultCommandHandler implements CommandHandler {

  protected final Map<String, Tuple<CommandInfo, CommandHandle>> commands = new ConcurrentHashMap<>();
  protected final ArgumentResolverRegistry argumentResolverRegistry;
  protected final CooldownMemory cooldownMemory;

  public DefaultCommandHandler() {
    argumentResolverRegistry = new DefaultArgumentResolverRegistry();
    cooldownMemory = new DefaultCooldownMemory();
  }

  @Override
  public void handleCommandEvent(@Nonnull SlashCommandEvent event) {
    String name = getCommandName(event);
    Tuple<CommandInfo, CommandHandle> pair = commands.get(name);

    try {
      pair.getSecond().execute(new CommandContext() {
        @Nonnull
        @Override
        public CommandInteraction getInteraction() {
          return event;
        }
      }, null);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Nonnull
  @Override
  public Collection<CommandData> getGeneralSlashCommandData() {
    return commands.entrySet().stream()
      .filter(entry -> entry.getValue().getFirst().getScope() == CommandScope.EVERYWHERE)
      .map(entry -> {
        CommandInfo command = entry.getValue().getFirst();
        return new CommandData(entry.getKey(), command.getDescription())
          .setDefaultEnabled(command.getPermission() == Permission.UNKNOWN && command.getAccessCheckers().isEmpty())
          .addOptions(command.getArguments().stream()
            .map(argument -> argument.getResolver().build(argument))
            .collect(Collectors.toList())
          );
      })
      .collect(Collectors.toList());
  }

  @Nonnull
  @Override
  public Collection<CommandData> getGuildSlashCommandData(@Nonnull Guild guild) {
    return commands.entrySet().stream()
      .filter(entry -> entry.getValue().getFirst().getScope() == CommandScope.GUILDS)
      .map(entry -> {
        CommandInfo command = entry.getValue().getFirst();
        return new CommandData(entry.getKey(), command.getDescription())
          .setDefaultEnabled(command.getPermission() == Permission.UNKNOWN && command.getAccessCheckers().isEmpty())
          .addOptions(command.getArguments().stream()
            .map(argument -> argument.getResolver().build(argument))
            .collect(Collectors.toList())
          );
      })
      .collect(Collectors.toList());
  }

  @Nonnull
  @Override
  public CommandHandler registerCommand(@Nonnull Object command) {
    Collection<Method> methods = ReflectionUtils.getMethodsAnnotatedWith(command.getClass(), Command.class);
    for (Method method : methods) {
      Command annotation = method.getAnnotation(Command.class);

      List<ArgumentInfo> arguments = new ArrayList<>();
      List<ReflectionCommandHandle.ParameterCreator> handleParameters = new ArrayList<>(); // TODO this here is ugly tbh
      for (Parameter parameter : method.getParameters()) {
        Class<?> type = parameter.getType();
        if (type == CommandContext.class) {
          handleParameters.add((context, serviceProvider) -> context); // TODO see above
        } else if (parameter.isAnnotationPresent(Argument.class)) {
          Argument argumentAnnotation = parameter.getAnnotation(Argument.class);

          ArgumentResolver<?> resolver = argumentResolverRegistry.find(type);
          if (resolver == null)
            throw new IllegalArgumentException("Command " + command.getClass().getName() + "." + method.getName() + " has an unknown parameter type: " + parameter);
          if (!parameter.isAnnotationPresent(Description.class))
            throw new IllegalArgumentException("Command " + command.getClass().getName() + "." + method.getName() + ": Argument " + argumentAnnotation.value() + " does not have a description");

          String description = parameter.getAnnotation(Description.class).value();
          boolean optional = parameter.isAnnotationPresent(Optional.class);
          Collection<ChannelType> channelTypes = parameter.isAnnotationPresent(ChoiceChannels.class) ? Arrays.asList(parameter.getAnnotation(ChoiceChannels.class).value()) : null;
          Number maxValue = parameter.isAnnotationPresent(MaxValue.class) ? parameter.getAnnotation(MaxValue.class).value() : null;
          Number minValue = parameter.isAnnotationPresent(MinValue.class) ? parameter.getAnnotation(MinValue.class).value() : null;
          Map<String, Object> manualChoices = new HashMap<>();
          for (ChoiceString choice : parameter.getAnnotationsByType(ChoiceString.class))
            manualChoices.put(choice.name(), choice.value());
          for (ChoiceInt choice : parameter.getAnnotationsByType(ChoiceInt.class))
            manualChoices.put(choice.name(), choice.value());
          for (ChoiceNumber choice : parameter.getAnnotationsByType(ChoiceNumber.class))
            manualChoices.put(choice.name(), choice.value());

          ArgumentInfo info = new ArgumentInfo(
            argumentAnnotation.value(),
            description,
            optional,
            maxValue,
            minValue,
            manualChoices,
            channelTypes,
            type,
            resolver
          );
          arguments.add(info);
          handleParameters.add((context, serviceProvider) -> resolver.resolve(info, context.getInteraction())); // TODO see above
        } else if (parameter.isAnnotationPresent(Inject.class)) {
          handleParameters.add((context, serviceProvider) -> serviceProvider == null ? null : serviceProvider.provide(type)); // TODO see above & nullable check
        } else {
          throw new IllegalArgumentException("Command " + command.getClass().getName() + "." + method.getName() + " has an illegal parameter: " + parameter);
        }
      }

      CommandInfo info = new CommandInfo(
        annotation.name(),
        annotation.description(),
        annotation.scope(),
        Arrays.stream(annotation.cooldown()).map(cooldown ->
          new CommandInfo.CommandCooldownInfo(cooldown.scope(), Duration.ofMillis(cooldown.unit().toMillis(cooldown.time())))
        ).collect(Collectors.toList()),
        annotation.permission(),
        Arrays.asList(annotation.access().value()),
        arguments
      );
      registerCommandHandle(info, new ReflectionCommandHandle(command, method, handleParameters));

    }

    return this;
  }

  protected String getCommandName(@Nonnull CommandInteraction interaction) {
    StringBuilder builder = new StringBuilder(interaction.getName());
    if (interaction.getSubcommandGroup() != null)
      builder.append('/').append(interaction.getSubcommandGroup());
    if (interaction.getSubcommandName() != null)
      builder.append('/').append(interaction.getSubcommandName());
    return builder.toString();
  }

  @Nonnull
  @Override
  public CommandHandler registerCommandHandle(@Nonnull CommandInfo info, @Nonnull CommandHandle handle) {
    Tuple<CommandInfo, CommandHandle> tuple = Tuple.of(info, handle);
    for (String name : info.getName()) {
      commands.put(name, tuple);
    }
    return this;
  }

  @Nonnull
  @Override
  public Set<Tuple<CommandInfo, CommandHandle>> getRegisteredCommands() {
    return new HashSet<>(commands.values());
  }

  @Nonnull
  @Override
  public CommandHandler registerAccessProvider(@Nonnull String name, @Nonnull CommandAccessProvider provider) {
    return null;
  }

  @Nullable
  @Override
  public CommandAccessProvider getAccessProvider(@Nonnull String name) {
    return null;
  }

  @Nonnull
  @Override
  public CooldownMemory getCooldownMemory() {
    return null;
  }
}
