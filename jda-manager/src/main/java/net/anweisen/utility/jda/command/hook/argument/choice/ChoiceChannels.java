package net.anweisen.utility.jda.command.hook.argument.choice;

import net.dv8tion.jda.api.entities.ChannelType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ChoiceChannels {

  ChannelType[] value();

}
