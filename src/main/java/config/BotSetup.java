package config;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.EnumSet;

public class BotSetup extends ListenerAdapter {
    private static final String EMOJI_RAGE = "U+1f621";
    private static final String CHANNEL_NAME_ROLE = "rage-role";
    private static final String CHANNEL_NAME_BOT = "rage-bot";

    @Override
    public void onRoleCreate(@NotNull RoleCreateEvent event) {
        if (event.getRole().getName().equals("Rage")) {
            createChannelRageRole(event);
            createChannelRageBot(event);
        }
    }

    @Override
    public void onTextChannelCreate(@NotNull TextChannelCreateEvent event) {
        if (event.getChannel().getName().equals(CHANNEL_NAME_ROLE)) {
            createMessageChannelRageRole(event);
        }
    }

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        String channel = event.getChannel().getName();
        String emote = event.getReaction().getReactionEmote().getAsCodepoints();
        long messageReactionId = event.getReaction().getMessageIdLong();
        long latestMessageId = event.getChannel().getLatestMessageIdLong();
        Role rageRole = event.getGuild().getRolesByName("Rage", true).get(0);

        if (!channel.equals(CHANNEL_NAME_ROLE)) return;
        if (!emote.equals(EMOJI_RAGE)) return;
        if (messageReactionId != latestMessageId) return;
        event.getGuild().addRoleToMember(event.getMember().getId(), rageRole).queue();
    }

    @Override
    public void onMessageReactionRemove(@NotNull MessageReactionRemoveEvent event) {
        String emote = event.getReaction().getReactionEmote().getAsCodepoints();
        Role rageRole = event.getGuild().getRolesByName("Rage", true).get(0);

        if (!emote.equals(EMOJI_RAGE)) return;
        event.getGuild().removeRoleFromMember(event.getMember().getId(), rageRole).queue();
    }

    public void createChannelRageRole(@NotNull RoleCreateEvent event) {
        Category firstCategory = event.getGuild().getCategories().get(0);
        event.getGuild().createTextChannel(CHANNEL_NAME_ROLE, firstCategory)
                .addRolePermissionOverride(event.getGuild().getPublicRole().getIdLong(), null, EnumSet.of(Permission.MESSAGE_WRITE))
                .queue();
    }

    public void createMessageChannelRageRole(@NotNull TextChannelCreateEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setColor(Color.RED);
        embed.setTitle(":book:  RageBOT - Sélectionne ton rôle");
        embed.appendDescription("Clique sur la réaction :rage: pour utiliser les commandes du bot dans `#rage-bot`");

        event.getChannel().sendMessage(embed.build()).queue(message -> message.addReaction(EMOJI_RAGE).queue());
    }

    public void createChannelRageBot(@NotNull RoleCreateEvent event) {
        Role rageRole = event.getGuild().getRolesByName("Rage", true).get(0);

        Category firstCategory = event.getGuild().getCategories().get(0);
        event.getGuild()
                .createTextChannel(CHANNEL_NAME_BOT, firstCategory)
                .addRolePermissionOverride(event.getGuild().getPublicRole().getIdLong(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                .addRolePermissionOverride(rageRole.getIdLong(), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_HISTORY), null)
                .queue();
    }


}
