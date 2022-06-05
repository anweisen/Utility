package net.anweisen.utility.jda.bot.test;

import net.anweisen.utility.common.misc.StringUtils;
import net.anweisen.utility.database.Database;
import net.anweisen.utility.jda.command.context.CommandContext;
import net.anweisen.utility.jda.command.context.inject.Inject;
import net.anweisen.utility.jda.command.hook.Command;
import net.anweisen.utility.jda.command.hook.argument.Argument;
import net.anweisen.utility.jda.command.hook.argument.Description;
import net.anweisen.utility.jda.command.hook.argument.Optional;
import net.anweisen.utility.jda.command.hook.argument.choice.ChoiceString;
import net.anweisen.utility.jda.command.hook.argument.choice.type.ChoiceEnum;
import net.anweisen.utility.jda.command.hook.cooldown.Cooldown;
import net.anweisen.utility.jda.command.hook.cooldown.CooldownScope;
import net.anweisen.utility.jda.manager.command.CommandHandler;
import net.anweisen.utility.jda.manager.command.DefaultCommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;

/**
 * @author anweisen | https://github.com/anweisen
 * @since 1.0
 */
public class TestCommand {

  public static void main(String[] args) throws LoginException, InterruptedException {
    CommandHandler handler = new DefaultCommandHandler()
      .registerCommand(new TestCommand());

    JDA jda = JDABuilder.createDefault("NzE0MTk3OTQ0MDQzMzA3MTAw.XsrKqw.kT2yIwWPf3V8_Nt8bJXKMuvsWYk").build().awaitReady();
    jda.updateCommands().addCommands(handler.getGeneralSlashCommandData()).queue();
    for (Guild guild : jda.getGuilds()) {
      guild.updateCommands().addCommands(handler.getGuildSlashCommandData(guild)).queue(commands -> {
        for (net.dv8tion.jda.api.interactions.commands.Command command : commands) {

        }
      });
    }

    jda.addEventListener(new ListenerAdapter() {
      @Override
      public void onSlashCommand(@Nonnull SlashCommandEvent event) {
        event.deferReply().queue();
        handler.handleCommandEvent(event);
      }
    });
  }

  @Command(
    name = "test",
    description = "This is a simple test command using this library",
    cooldown = @Cooldown(scope = CooldownScope.PER_GUILD, time = 3)
//    permission = Permission.MESSAGE_SEND,
//    access = @AccessCheckers("team")
  )
  void onCommand(
    @Nonnull CommandContext context,
    @Inject Database database,
//    @Argument("number") @Description("The number") @MinValue(1) @MaxValue(5) int number,
    @Argument("type") @Description("The type yeahhh") @ChoiceString(name = "name1", value = "value1") String enumType,
    @Argument("user") @Description("hs") @Optional Member member
  ) {
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println(enumType);
    System.out.println(member);
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
  }

  public enum TestEnum implements ChoiceEnum {

    VALUE;

    @Nonnull
    @Override
    public String getDisplayName() {
      return StringUtils.getEnumName(this);
    }
  }

}
