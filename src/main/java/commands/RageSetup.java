package commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import java.awt.*;
import java.util.List;

public class RageSetup implements Command {
    @Override
    public void perform(@NotNull GuildMessageReceivedEvent event) {
        List<Role> rageRoles = event.getGuild().getRolesByName("Rage", true);

        // Creates Rage role
        if (rageRoles.isEmpty()) {
            event.getGuild().createRole().setName("Rage").setColor(Color.RED).setMentionable(true).queue();
        }
    }
}