package broccolizer.ChannelManagement.Listeners;

import broccolizer.GameLogic.DiscordController;
import broccolizer.GameLogic.GameController;
import broccolizer.GameLogic.GameStates;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.List;

public class CupidChannelListener implements MessageCreateListener {
    @Override
    public void onMessageCreate(MessageCreateEvent messageCreateEvent) {
        Message msg = messageCreateEvent.getMessage();
        TextChannel sentChannel = msg.getChannel();
        //Checks gameState and on !bond
        if (GameController.getInstance().getGameState() == GameStates.CUPID && msg.getContent().contains("!bond")){
            List<User> mentioned = msg.getMentionedUsers();
            //Checks if they are users in this game and mentioned size
            if (DiscordController.getInstance().getUsers().containsKey(mentioned) && mentioned.size() == 2){
                //Checks if not cupid himself.
                if (!mentioned.contains(msg.getUserAuthor().get())){
                    GameController.getInstance().bondCupid(mentioned.get(0), mentioned.get(1));
                    GameController.getInstance().giveNextInstruction();
                } else {
                    sentChannel.sendMessage("You can't bond yourself with someone else, cupid...\n" +
                            "Try again.");
                }
            } else {
                sentChannel.sendMessage("You can't bond yourself or more than 2 people.");
            }
        } else {
            sentChannel.sendMessage("Please include '!bond' in your message and mention 2 players to bond.");
        }
    }
}
