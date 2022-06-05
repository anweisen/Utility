package net.anweisen.utility.jda.manager.command.argument;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DefaultArgumentResolverRegistry implements ArgumentResolverRegistry {

  private static final Collection<ArgumentResolver<?>> defaults = Arrays.asList(
    ArgumentResolvers.newResolver(User.class,     OptionType.USER,        OptionMapping::getAsUser),
    ArgumentResolvers.newResolver(Member.class,   OptionType.USER,        OptionMapping::getAsMember),
    ArgumentResolvers.newResolver(Role.class,     OptionType.ROLE,        OptionMapping::getAsRole),
    ArgumentResolvers.newResolver(String.class,   OptionType.STRING,      OptionMapping::getAsString)
  );

  private final Collection<ArgumentResolver<?>> resolvers = new CopyOnWriteArrayList<>(defaults);

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> ArgumentResolver<T> find(@Nonnull Class<T> type) {
    for (ArgumentResolver<?> resolver : resolvers) {
      if (resolver.applies(type))
        return (ArgumentResolver<T>) resolver;
    }
    return null;
  }

  @Override
  public void register(@Nonnull ArgumentResolver<?> resolver) {
    resolvers.add(resolver);
  }
}
