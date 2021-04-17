package net.anweisen.utilities.jda.commandmanager.impl.language;

import net.anweisen.utilities.commons.config.Document;
import net.anweisen.utilities.jda.commandmanager.language.Language;
import net.anweisen.utilities.jda.commandmanager.language.Message;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class LanguageImpl implements Language {

	private final Map<String, Message> messages = new HashMap<>();
	private final String name;

	public LanguageImpl(@Nonnull String name) {
		this.name = name;
	}

	@Nonnull
	@Override
	public Message getMessage(@Nonnull String name) {
		return messages.computeIfAbsent(name, MessageImpl::new);
	}

	@Nonnull
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void read(@Nonnull Document document) {
		for (String path : document.keys()) {
			Message message = getMessage(path);
			if (document.isList(path)) {
				message.setValue(document.getStringList(path).toArray(new String[0]));
			} else {
				message.setValue(document.getString(path));
			}
		}
	}

}
