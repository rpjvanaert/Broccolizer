package broccolizer.GameLogic;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.util.HashSet;

public class GameLobbyLogic {
        public static void addToPlayers(User player, TextChannel castChannel){
        if (GameManager.getInstance().addUser(player)){
            castChannel.sendMessage("Welcome to the game of the-werewolves-of-millers-hollow\n" +
                    "Send \"!join\" to join the lobby!\n" +
                    "or \"!leave\" to leave the lobby!\n" +
                    "The players in current session:");
            sendUsers(GameManager.getInstance().getUsers(), castChannel);
        } else {
            castChannel.sendMessage("Couldn't add player. Might be in already");
        }
    }

    public static void sendUsers(HashSet<User> users, TextChannel castChannel){
        for (User each : users){
            castChannel.sendMessage(" - " + each.getName() + " (" + each.getMentionTag() + ").");
        }
    }
}
