package broccolizer.Listeners;

import broccolizer.GameLogic.PlayerManager;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.Messageable;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;

public class setupListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event){
        Message msg = event.getMessage();

        User requester = msg.getAuthor().asUser().get();

        TextChannel sentChannel = msg.getChannel();
        System.out.println(sentChannel.toString());

        switch (sentChannel.getIdAsString()){
            case "694900308484030534":
                PlayerManager.getInstance().addUser(requester);
                sentChannel.sendMessage("Welcome to the game of the-werewolves-of-millers-hollow\n" +
                        "Send \"!joinGameLobby\" to join the lobby!\n" +
                        "The players in current session:");
                for (User each : PlayerManager.getInstance().getUsers()){
                    sentChannel.sendMessage(" - " + each.getName() + " (" + each.getMentionTag() + ").");
                    each.sendMessage("The players in current session:");
                    for (User eachMember : PlayerManager.getInstance().getUsers()){
                        each.sendMessage(" - " + eachMember.getName() + " (" + eachMember.getMentionTag() + ").");
                    }
                }
                break;
        }
    }

}
