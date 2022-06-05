package net.anweisen.utility.jda.manager.command.handle;

import net.anweisen.utility.jda.command.context.CommandContext;
import net.anweisen.utility.jda.manager.services.ServiceProvider;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class ReflectionCommandHandle implements CommandHandle {

  private final Object instance;
  private final Method method;
  private final List<ParameterCreator> parameters;

  public ReflectionCommandHandle(@Nonnull Object instance, @Nonnull Method method, @Nonnull List<ParameterCreator> parameters) {
    this.instance = instance;
    this.method = method;
    this.parameters = parameters;
  }

  @Override
  @SuppressWarnings("deprecation")
  public void execute(@Nonnull CommandContext context, @Nonnull ServiceProvider serviceProvider) throws Exception {
    Object[] args = new Object[parameters.size()];
    for (int i = 0; i < parameters.size(); i++) {
      args[i] = parameters.get(i).create(context, serviceProvider);
    }

    if (!method.isAccessible()) method.setAccessible(true);
    method.invoke(instance, args);
  }

  public interface ParameterCreator {

    Object create(@Nonnull CommandContext context, @Nullable ServiceProvider serviceProvider);

  }

}
