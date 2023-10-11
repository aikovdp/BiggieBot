package me.aikovdp.biggiebot.modules;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.List;

public class Starboard extends ListenerAdapter {
    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (!event.isFromGuild()
                || !event.getEmoji().getAsReactionCode().equals("⭐")
                || event.getReaction().retrieveUsers().complete().size() != 1)
            return;

        List<TextChannel> starboardChannels = event.getGuild().getTextChannelsByName("starboard", false);
        if (starboardChannels.isEmpty())
            return;
        TextChannel starboardChannel = starboardChannels.get(0);

        Message message = event.retrieveMessage().complete();
        String imageUrl = message.getAttachments()
                .stream()
                .filter(Message.Attachment::isImage)
                .findFirst()
                .map(Message.Attachment::getProxyUrl)
                .orElse(null);


        MessageEmbed embed = new EmbedBuilder()
                .setAuthor(message.getAuthor().getAsTag(), null, message.getAuthor().getEffectiveAvatarUrl())
                .setDescription(message.getContentRaw())
                .setColor(0xFFAE30)
                .setTimestamp(message.getTimeCreated())
                .setImage(imageUrl)
                .build();

        Message response = new MessageBuilder()
                .setContent("⭐ " + event.getChannel().getAsMention())
                .setActionRows(ActionRow.of(Button.link(message.getJumpUrl(), "Jump to message")))
                .setEmbeds(embed)
                .build();
        starboardChannel.sendMessage(response).queue();
    }
}
