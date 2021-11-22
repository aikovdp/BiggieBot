package me.aikovdp.biggiebot;

import me.aikovdp.biggiebot.modules.Starboard;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.SlashCommandEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;


public class BiggieBot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(args[0])
                .addEventListeners(new BiggieBot(), new Starboard())
                .build();

        jda.updateCommands().addCommands(
                new CommandData("ping", "Ping the bot"),
                new CommandData("poker", "Start a new poker session")
                        .addOption(OptionType.CHANNEL, "channel", "The channel to start the session", true),
                new CommandData("chess", "Start a new chess session")
                        .addOption(OptionType.CHANNEL, "channel", "The channel to start the session", true),
                new CommandData("checker", "Start a new checkers session")
                        .addOption(OptionType.CHANNEL, "channel", "The channel to start the session", true)
        ).queue();
    }


    @Override
    public void onSlashCommand(@NotNull SlashCommandEvent event) {
        switch (event.getName()) {
            case "ping" -> event.reply("pong").queue();
            case "poker" -> event.getOption("channel").getAsGuildChannel()
                    .createInvite()
                    .setTargetApplication("755827207812677713")
                    .queue(invite -> event.reply(invite.getUrl()).queue());
            case "chess" -> event.getOption("channel").getAsGuildChannel()
                    .createInvite()
                    .setTargetApplication("832012586023256104")
                    .queue(invite -> event.reply(invite.getUrl()).queue());
            case "checker" -> event.getOption("channel").getAsGuildChannel()
                    .createInvite()
                    .setTargetApplication("413643239187546113")
                    .queue(invite -> event.reply(invite.getUrl()).queue());
        }
    }
}
