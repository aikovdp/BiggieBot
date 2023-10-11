package me.aikovdp.biggiebot.modules

import dev.minn.jda.ktx.interactions.components.link
import dev.minn.jda.ktx.interactions.components.row
import dev.minn.jda.ktx.messages.Embed
import dev.minn.jda.ktx.messages.MessageCreate
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class Starboard : ListenerAdapter() {
    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (!event.isFromGuild
            || event.emoji.asReactionCode != "⭐"
            || event.reaction.retrieveUsers().complete().size != 1
        ) {
            return
        }
        val starboardChannel = getStarboardChannel(event.guild) ?: return

        event.retrieveMessage().queue { sendToStarboard(starboardChannel, it) }
    }
}

fun getStarboardChannel(guild: Guild?): TextChannel? {
    return guild?.getTextChannelsByName("starboard", false)?.firstOrNull()
}

fun sendToStarboard(channel: TextChannel, message: Message) {
    channel.sendMessage(
        MessageCreate {
            content = "⭐ ${message.channel.asMention}"
            components += row(link(message.jumpUrl, "Jump to message"))
            embeds += Embed {
                description = message.contentRaw
                color = 0xFFAE30
                timestamp = message.timeCreated
                image = message.attachments.firstOrNull { it.isImage() }?.proxyUrl
                author {
                    name = message.author.name
                    iconUrl = message.author.effectiveAvatarUrl
                }
            }
        }
    ).queue()
}
