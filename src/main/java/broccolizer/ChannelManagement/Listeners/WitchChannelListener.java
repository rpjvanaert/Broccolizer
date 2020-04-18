package broccolizer.ChannelManagement.Listeners;

import broccolizer.GameLogic.DiscordController;
import broccolizer.GameLogic.GameController;
import broccolizer.GameLogic.GameStates;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.ArrayList;
import java.util.List;

public class WitchChannelListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        Message msg = messageCreateEvent.getMessage();
        TextChannel sentChannel = msg.getChannel();
        List<User> mentioned = msg.getMentionedUsers();

        if (GameController.getInstance().getGameState() == GameStates.WOLVES && (msg.getContent().contains("!kill") || msg.getContent().contains("!revive"))){

            if (mentioned.size() == 1 && DiscordController.getInstance().getUsers().containsKey(mentioned.get(0))){

                if (msg.getContent().contains("!kill") && GameController.getInstance().getDeathPotion()){

                    if (!GameController.getInstance().isDead(mentioned.get(0)) || !GameController.getInstance().isDying(mentioned.get(0))){
                        if (GameController.getInstance().playerDying(mentioned.get(0))){
                            GameController.getInstance().giveNextInstruction();
                        } else {
                            sentChannel.sendMessage("Kill error");
                        }
                    } else {
                        sentChannel.sendMessage("Player is already dead or dying.");
                    }
                } else if (msg.getContent().contains("!revive") && GameController.getInstance().getLifePotion()){

                    if (GameController.getInstance().isDead(mentioned.get(0)) || GameController.getInstance().isDying(mentioned.get(0))){
                        if (GameController.getInstance().revivePlayer(mentioned.get(0))){
                            GameController.getInstance().giveNextInstruction();
                        } else {
                            sentChannel.sendMessage("Revive error");
                        }
                    } else {
                        sentChannel.sendMessage("Player is NOT dead or dying.");
                    }

                } else if (msg.getContent().equalsIgnoreCase("!skip")){

                } else {
                    sentChannel.sendMessage("Can't do that.");
                }
            } else {
                sentChannel.sendMessage("The one user you want to kill or revive must be in the game.");
            }
        }
    }
}
