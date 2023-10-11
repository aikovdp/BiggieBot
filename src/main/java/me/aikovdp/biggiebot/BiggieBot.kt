package me.aikovdp.biggiebot

import me.aikovdp.biggiebot.modules.Starboard
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.commands.build.Commands

fun main() {
    BiggieBot()
}

class BiggieBot : ListenerAdapter() {
    init {
        val jda = JDABuilder.createLight(System.getenv("DISCORD_BOT_TOKEN"))
                .addEventListeners(this, Starboard())
                .build()
        jda.updateCommands()
                .addCommands(Commands.slash("ping", "Ping the bot"))
                .queue()
    }
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name == "ping") {
            event.reply("pong").queue()
        }
    }
}
