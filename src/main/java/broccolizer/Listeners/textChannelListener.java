package broccolizer.Listeners;

import broccolizer.GameLogic.GameLobbyLogic;
import broccolizer.GameLogic.GameManager;
import broccolizer.TUI;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

public class textChannelListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event){
        Message msg = event.getMessage();

        User requester = msg.getAuthor().asUser().get();

        TextChannel sentChannel = msg.getChannel();
        System.out.println(sentChannel.toString());

        switch (sentChannel.getIdAsString()){
            case "694900308484030534":
                if (msg.getContent().equalsIgnoreCase("!join")){
                    GameLobbyLogic.addToPlayers(requester, sentChannel);

                } else if (msg.getContent().equalsIgnoreCase("!clear")){
                    sentChannel.getMessages(1000).thenAcceptAsync(MessageSet::deleteAll);
                    GameManager.getInstance().resetInstance();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TUI.sendLobbyInstruction(sentChannel);

                } else if (msg.getContent().equalsIgnoreCase("!start")){
                    sentChannel.sendMessage("STARTING");

                } else if (msg.getContent().equalsIgnoreCase("!info")){
                    TUI.sendLobbyInstruction(sentChannel);

                } else if (msg.getContent().equalsIgnoreCase("!leave")){
                    if (GameManager.getInstance().removeUser(requester)){
                        sentChannel.sendMessage("You have left.");
                    } else {
                        sentChannel.sendMessage("You are not in the lobby");
                    }

                } else if (msg.getContent().equalsIgnoreCase("!players")){
                    sentChannel.sendMessage("The players in current session:");
                    GameLobbyLogic.sendUsers(GameManager.getInstance().getUsers(), sentChannel);

                }
                break;
        }
    }

}
