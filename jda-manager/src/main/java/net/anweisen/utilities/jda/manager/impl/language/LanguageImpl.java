package net.anweisen.utilities.jda.manager.impl.language;

import net.anweisen.utilities.common.config.Document;
import net.anweisen.utilities.jda.manager.language.Language;
import net.anweisen.utilities.jda.manager.language.Message;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class LanguageImpl implements Language {

	protected final Map<String, Message> messages = new HashMap<>();
	protected final String identifier;
	protected String[] names;

	public LanguageImpl(@Nonnull String identifier) {
		this.identifier = identifier;
	}

	@Nonnull
	@Override
	public Message getMessage(@Nonnull String name) {
		return messages.computeIfAbsent(name, MessageImpl::new);
	}

	@Nonnull
	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Nonnull
	@Override
	public String[] getNames() {
		return names;
	}

	@Override
	public void read(@Nonnull Document document) {
		names = document.getStringList("name").toArray(new String[0]);
		if (names.length == 0)
			throw new IllegalArgumentException("Names of language ('" + identifier + "') cannot be empty");

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
