package broccolizer.GameLogic;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.util.HashMap;
import java.util.List;

public class UserLobbyLogic {
        public static boolean addPlayer(User player, TextChannel castChannel){
            if (DiscordController.getInstance().addUser(player)){
                return true;
            } else {
                return false;
            }
        }

        public static boolean addPlayers(List<User> usersList, TextChannel castChannel){
            boolean works = true;
            for(User each : usersList){
                if (!DiscordController.getInstance().addUser(each)){
                    works = false;
                }
            }
            return works;
        }


    public static void sendUsers(HashMap<User,Roles> users, TextChannel castChannel){
        for (User each : users.keySet()){
            castChannel.sendMessage(" - " + each.getName() + " (" + each.getMentionTag() + ").");
        }
    }

    public static boolean assignRoles(){
            if (DiscordController.getInstance().getUsers().size() >= 9){
                DiscordController.getInstance().assignRoles();
                return true;
            }
            return false;
    }
}
