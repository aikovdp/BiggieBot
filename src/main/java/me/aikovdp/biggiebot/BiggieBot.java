package me.aikovdp.biggiebot;

import me.aikovdp.biggiebot.modules.Starboard;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;


public class BiggieBot extends ListenerAdapter {

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(args[0])
                .addEventListeners(new BiggieBot(), new Starboard())
                .build();

        jda.updateCommands()
                .addCommands(Commands.slash("ping", "Ping the bot"))
                .queue();
    }


    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getName().equals("ping")) {
            event.reply("pong").queue();
        }
    }
}
