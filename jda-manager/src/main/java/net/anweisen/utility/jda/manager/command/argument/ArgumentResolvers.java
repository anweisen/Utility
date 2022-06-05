package net.anweisen.utility.jda.manager.command.argument;

import net.anweisen.utility.jda.manager.command.registered.ArgumentInfo;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.CommandInteraction;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.data.DataObject;
import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Function;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public final class ArgumentResolvers {

  private ArgumentResolvers() {
  }

  @Nonnull
  public static OptionData applyDefaultOptionData(@Nonnull OptionData data, @Nonnull ArgumentInfo info) {
    if (info.getManualChoices() != null && !info.getManualChoices().isEmpty()) {
      for (Map.Entry<String, Object> entry : info.getManualChoices().entrySet()) {
        data.addChoices(new Command.Choice(DataObject.empty().put("name", entry.getKey()).put("value", entry.getValue())));
      }
    }
    return data;
  }

  @Nonnull
  public static OptionData createDefaultOptionData(@Nonnull OptionType type, @Nonnull ArgumentInfo info) {
    return applyDefaultOptionData(new OptionData(type, info.getName(), info.getDescription(), !info.isOptional()), info);
  }

  @Nonnull
  public static <T> ArgumentResolver<T> newResolver(@Nonnull Class<T> typeClass, @Nonnull OptionType optionType, @Nonnull Function<OptionMapping, T> mapper) {
    return new ArgumentResolver<T>() {
      @Override
      public boolean applies(@Nonnull Class<?> type) {
        return typeClass == type;
      }

      @Override
      public T resolve(@Nonnull ArgumentInfo info, @Nonnull CommandInteraction interaction) {
        return mapper.apply(interaction.getOption(info.getName()));
      }

      @Nonnull
      @Override
      public OptionData build(@Nonnull ArgumentInfo info) {
        return createDefaultOptionData(optionType, info);
      }
    };
  }
}
