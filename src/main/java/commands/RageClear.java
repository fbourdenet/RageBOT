package commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class RageClear implements Command {
    public void perform(@NotNull GuildMessageReceivedEvent event) throws IOException {
        List<User> listUserMentionned = event.getMessage().getMentionedUsers();

        // Si aucun des utilisateurs n'a été mentionné
        if (listUserMentionned.isEmpty()) return;

        listUserMentionned.forEach(userMentionned -> {
            Member memberMentioned = event.getGuild().getMemberById(userMentionned.getId());
            List<Member> listUserRageRole = event.getGuild().getMembersWithRoles(event.getGuild().getRolesByName("Rage", true));

            if (!listUserRageRole.contains(memberMentioned)) return;
            CommandManager.deleteInFile(userMentionned.getId());
        });
    }
}
