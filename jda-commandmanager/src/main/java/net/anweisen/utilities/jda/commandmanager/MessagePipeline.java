package net.anweisen.utilities.jda.commandmanager;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public interface MessagePipeline {

	@Nonnull
	@CheckReturnValue
	MessageAction reply();

	@Nonnull
	@CheckReturnValue
	MessageAction reply(@Nonnull CharSequence message);

	@Nonnull
	@CheckReturnValue
	MessageAction reply(@Nonnull EmbedBuilder message);

	@Nonnull
	@CheckReturnValue
	MessageAction reply(@Nonnull MessageEmbed message);

	@Nonnull
	@CheckReturnValue
	MessageAction send();

	@Nonnull
	@CheckReturnValue
	MessageAction send(@Nonnull CharSequence message);

	@Nonnull
	@CheckReturnValue
	MessageAction send(@Nonnull EmbedBuilder message);

	@Nonnull
	@CheckReturnValue
	MessageAction send(@Nonnull MessageEmbed message);

//	@Nonnull
//	@CheckReturnValue
//	MessageAction sendPrivate();
//
//	@Nonnull
//	@CheckReturnValue
//	MessageAction sendPrivate(@Nonnull CharSequence message);
//
//	@Nonnull
//	@CheckReturnValue
//	MessageAction sendPrivate(@Nonnull EmbedBuilder message);
//
//	@Nonnull
//	@CheckReturnValue
//	MessageAction sendPrivate(@Nonnull MessageEmbed message);

}
