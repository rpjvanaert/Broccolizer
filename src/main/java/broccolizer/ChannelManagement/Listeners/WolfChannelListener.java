package broccolizer.ChannelManagement.Listeners;

import broccolizer.GameLogic.DiscordController;
import broccolizer.GameLogic.GameController;
import broccolizer.GameLogic.GameStates;
import broccolizer.GameLogic.Roles;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.io.File;

public class WolfChannelListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        Message msg = messageCreateEvent.getMessage();
        TextChannel sentChannel = msg.getChannel();

        //Checks if GameState is WOLVES and on including '!kill'
        if (GameController.getInstance().getGameState() == GameStates.WOLVES && msg.getContent().contains("!kill")){

            //Checks if only 1 user is mentioned and in game.
            if (msg.getMentionedUsers().size() == 1 && DiscordController.getInstance().getUsers().containsKey(msg.getMentionedUsers().get(0))){


                //Checks if mentioned user isn't dead
                if (!GameController.getInstance().isDead(msg.getMentionedUsers().get(0))){

                    //Checks if mentioned user isnt a wolf
                    if (DiscordController.getInstance().getUsers().get(msg.getMentionedUsers().get(0)) != Roles.WOLF){

                    } else {
                        sentChannel.sendMessage("A wolf can't kill a wolf!");
                    }
                } else {
                    sentChannel.sendMessage("Can't kill a dead person!", new File("memesIncluded/alreadyDead.jpg"));
                }
            }
        }
    }
}
