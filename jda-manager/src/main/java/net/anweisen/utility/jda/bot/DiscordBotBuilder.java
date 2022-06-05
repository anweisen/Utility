package net.anweisen.utility.jda.bot;

import net.anweisen.utility.document.Document;
import net.anweisen.utility.document.Documents;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class DiscordBotBuilder {

  private EnumSet<GatewayIntent> intents = EnumSet.noneOf(GatewayIntent.class);
  private EnumSet<CacheFlag> cacheFlags = EnumSet.noneOf(CacheFlag.class);
  private Document defaultConfig = Documents.newJsonDocument();
  private Collection<Object> listeners = new ArrayList<>();
  private MemberCachePolicy memberCachePolicy;
  private boolean requireDatabase;

  @Nonnull
  @CheckReturnValue
  public DiscordBotBuilder intents(@Nonnull GatewayIntent... intents) {
    this.intents.addAll(Arrays.asList(intents));
    return this;
  }

  @Nonnull
  @CheckReturnValue
  public DiscordBotBuilder cache(@Nonnull CacheFlag... flags) {
    cacheFlags.addAll(Arrays.asList(flags));
    return this;
  }

  @Nonnull
  @CheckReturnValue
  public DiscordBotBuilder defaultConfig(@Nonnull Document config) {
    this.defaultConfig = config;
    return this;
  }

  @Nonnull
  @CheckReturnValue
  public DiscordBotBuilder listener(@Nonnull Object listener) {
    listeners.add(listener);
    return this;
  }

  @Nonnull
  @CheckReturnValue
  public DiscordBotBuilder listeners(@Nonnull Object... listeners) {
    this.listeners.addAll(Arrays.asList(listeners));
    return this;
  }

  @Nonnull
  @CheckReturnValue
  public DiscordBotBuilder cacheMembers(@Nonnull MemberCachePolicy memberCachePolicy) {
    this.memberCachePolicy = memberCachePolicy;
    return this;
  }

  @Nonnull
  @CheckReturnValue
  public DiscordBotBuilder requireDatabase() {
    requireDatabase = true;
    return this;
  }

  @Nullable
  public MemberCachePolicy getMemberCachePolicy() {
    return memberCachePolicy;
  }

  @Nonnull
  public Collection<Object> getListeners() {
    return listeners;
  }

  @Nonnull
  public EnumSet<GatewayIntent> getIntents() {
    return intents;
  }

  @Nonnull
  public EnumSet<CacheFlag> getCacheFlags() {
    return cacheFlags;
  }

  @Nonnull
  public Document getDefaultConfig() {
    return defaultConfig;
  }

  public boolean getRequireDatabase() {
    return requireDatabase;
  }

}
