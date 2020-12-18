package commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class RageHelp implements Command {
    @Override
    public void perform(@NotNull GuildMessageReceivedEvent event) {
        EmbedBuilder embedHelp = new EmbedBuilder();

        embedHelp.setTitle(":book:  RageBOT - Aide");
        embedHelp.appendDescription("**Commencer à utiliser RageBOT :**");
        embedHelp.appendDescription("\n1) Tape la commande `!rage setup` pour initialiser le bot");
        embedHelp.appendDescription("\n2) Cherche le nouveau salon textuel appelé `#rage-role`");
        embedHelp.appendDescription("\n3) Réagis avec l'emote :rage: sur le message s'y trouvant");
        embedHelp.appendDescription("\n4) Cherche le nouveau salon textuel appelé `#rage-bot`");
        embedHelp.appendDescription("\n5) Exécute toutes les commandes depuis `#rage-bot` afin de pouvoir utiliser le bot");
        embedHelp.appendDescription("\n\n**Commandes disponibles :**");
        embedHelp.appendDescription("\n`!rage setup` - Installation du bot");
        embedHelp.appendDescription("\n`!rage @user` - Ajoute 1 de rage à l'utilisateur mentionné");
        embedHelp.appendDescription("\n`!rage user @user` - Voir le nombre de rage de l'utilisateur mentionné");
        embedHelp.appendDescription("\n`!rage clear @user` - Supprime la totalité des rages de l'utilisateur");
        embedHelp.appendDescription("\n`!rage ranking` - Affiche le classement de rage du serveur");
        embedHelp.appendDescription("\n`!rage help` - Affiche l'aide");
        embedHelp.appendDescription("\n`!rage random` - à venir");


        event.getChannel().sendMessage(embedHelp.build()).queue();
    }
}

