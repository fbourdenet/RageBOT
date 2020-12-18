package commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.role.RoleCreateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;

interface Command {
    void perform(@NotNull GuildMessageReceivedEvent event) throws IOException;
}

public class CommandManager extends ListenerAdapter {

    private final Map<String, Command> commands = new HashMap<>();

    // Ctor
    public CommandManager() {
        commands.put("!rage", new Rage());
        // commands.put("!rage user", new RageUser());
        // commands.put("!rage ranking", new RageRank());
        // TODO : commande qui assigne le rôle de rageux
        // commands.put("!rage close", new RageRank());
        // commands.put("!rage random", new RageRandom());
        commands.put("!rage clear", new RageClear());
        commands.put("!rage setup", new RageSetup());
        commands.put("!rage help", new RageHelp());
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        performCommand(event);
    }

    public void performCommand(@NotNull GuildMessageReceivedEvent event) {
        String messageReceived = event.getMessage().getContentRaw();
        List<User> listUserMentionned = event.getMessage().getMentionedUsers();

        if (messageReceived.startsWith("!rage")) {
            String[] messageReceivedSplit = messageReceived.split(" ");
            StringBuilder messageArguments = new StringBuilder();

            int lengthMessageReceived = messageReceivedSplit.length;
            int nbMessageArguments = lengthMessageReceived - listUserMentionned.size();

            for (int i = 0; i < nbMessageArguments ; i++) {
                messageArguments.append(messageReceivedSplit[i]+" ");
            }

            try {
                commands.get(messageArguments.toString().strip()).perform(event);
            } catch (Exception e) { System.out.println("error"); }
        } else return;
    }

    //TODO: crée un fichier rage.txt quand le bot est est utilisé pour la première fois

    public static void writeInFile(String idUser) {
        try {
            // True allows to append to the file
            FileWriter rageFile = new FileWriter("rage.txt", true);

            // Writes the name into the file
            rageFile.write(idUser + "\n");
            rageFile.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void deleteInFile(String idUser) {
        try {
            List<String> lines = new ArrayList<String>();
            String line = null;

            File rageFile = new File("rage.txt");
            FileReader frRageFile = new FileReader(rageFile);
            BufferedReader brRageFile = new BufferedReader(frRageFile);

            while ((line = brRageFile.readLine()) != null) {
                if (!line.contains(idUser)) {
                    lines.add(line);
                }
            }
            frRageFile.close();
            brRageFile.close();

            FileWriter rageFile2 = new FileWriter(rageFile);
            BufferedWriter out = new BufferedWriter(rageFile2);
            for(String s : lines)
                out.write(s + "\n");
            out.flush();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
