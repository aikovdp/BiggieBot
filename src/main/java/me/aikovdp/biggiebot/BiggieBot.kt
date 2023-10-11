package me.aikovdp.biggiebot

import dev.minn.jda.ktx.events.onCommand
import dev.minn.jda.ktx.events.onContext
import dev.minn.jda.ktx.interactions.commands.message
import dev.minn.jda.ktx.interactions.commands.slash
import dev.minn.jda.ktx.interactions.commands.updateCommands
import dev.minn.jda.ktx.jdabuilder.light
import me.aikovdp.biggiebot.modules.Starboard
import me.aikovdp.biggiebot.modules.getStarboardChannel
import me.aikovdp.biggiebot.modules.sendToStarboard
import net.dv8tion.jda.api.entities.Message

fun main() {
    val jda = light(System.getenv("DISCORD_BOT_TOKEN"), enableCoroutines = true) {
        addEventListeners(Starboard())
    }

    jda.updateCommands {
        slash("ping", "Ping the bot")
        message("Send to starboard")
    }.queue()

    jda.onCommand("ping") {
        it.reply("pong").queue()
    }

    jda.onContext<Message>("Send to starboard") {
        sendToStarboard(
            getStarboardChannel(it.guild) ?: return@onContext,
            it.target
        )
        it.reply("Sent to starboard!").setEphemeral(true).queue()
    }
}
