package me.aikovdp.biggiebot.modules

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.interactions.components.ActionRow
import net.dv8tion.jda.api.interactions.components.buttons.Button

class Starboard : ListenerAdapter() {
    override fun onMessageReactionAdd(event: MessageReactionAddEvent) {
        if (!event.isFromGuild
                || event.emoji.asReactionCode != "⭐"
                || event.reaction.retrieveUsers().complete().size != 1) {
            return
        }
        val starboardChannel = event.getGuild().getTextChannelsByName("starboard", false).first() ?: return
        val message = event.retrieveMessage().complete()
        val imageUrl = message.attachments.firstOrNull { it.isImage() }?.proxyUrl
        val embed = EmbedBuilder()
                .setAuthor(message.author.asTag, null, message.author.getEffectiveAvatarUrl())
                .setDescription(message.contentRaw)
                .setColor(0xFFAE30)
                .setTimestamp(message.timeCreated)
                .setImage(imageUrl)
                .build()
        val response = MessageBuilder()
                .setContent("⭐ " + event.channel.asMention)
                .setActionRows(ActionRow.of(Button.link(message.jumpUrl, "Jump to message")))
                .setEmbeds(embed)
                .build()
        starboardChannel.sendMessage(response).queue()
    }
}
