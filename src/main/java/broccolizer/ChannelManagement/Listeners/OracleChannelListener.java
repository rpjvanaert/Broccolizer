package broccolizer.ChannelManagement.Listeners;

import broccolizer.GameLogic.DiscordController;
import broccolizer.GameLogic.GameController;
import broccolizer.GameLogic.GameStates;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class OracleChannelListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        Message msg = messageCreateEvent.getMessage();
        TextChannel sentChannel = msg.getChannel();

        //Checks gameState and if includes !expose.
        if (GameController.getInstance().getGameState() == GameStates.ORACLE && msg.getContent().contains("!expose")){

            //Checks if only 1 User is mentioned.
            if (msg.getMentionedUsers().size() == 1){

                //Checks if mentioned user isn't dead and not oracle him/herself.
                if (!GameController.getInstance().isDead(msg.getMentionedUsers().get(0)) && !DiscordController.getInstance().getUsers().equals(msg.getUserAuthor().get())){

                    //Checks if User mentioned is in the game.
                    if(DiscordController.getInstance().getUsers().containsKey(msg.getMentionedUsers().get(0))){

                        //Checks if author is dead
                        if (GameController.getInstance().isDead(msg.getUserAuthor().get())){
                            sentChannel.sendMessage("Player " + msg.getMentionedUsers().get(0) + " has the role: " + DiscordController.getInstance().getUsers().get(msg.getUserAuthor().get()));
                        }
                    } else {
                        sentChannel.sendMessage("You can't expose someone who isn't in the game.");
                    }

                } else {
                    sentChannel.sendMessage("You can't expose yourself or a dead player!");
                }
            } else {
                sentChannel.sendMessage("You can't expose more or less than 1 player!");
            }
        } else {
            sentChannel.sendMessage("Use '!expose' when it's your turn!");
        }
    }
}
