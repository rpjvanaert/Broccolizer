package broccolizer.Listeners;

import broccolizer.App;
import broccolizer.BotStates;
import broccolizer.GameLogic.GameLobbyLogic;
import broccolizer.GameLogic.GameManager;
import broccolizer.GameLogic.Roles;
import broccolizer.TUI;
import org.javacord.api.entity.Permissionable;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class textChannelListener implements MessageCreateListener {

    String wolfChatID;
    @Override
    public void onMessageCreate(MessageCreateEvent event){
        Message msg = event.getMessage();

        User requester = msg.getAuthor().asUser().get();

        TextChannel sentChannel = msg.getChannel();
        System.out.println(sentChannel.toString());

        switch (sentChannel.getIdAsString()){
            case "694900308484030534":
                if (msg.getContent().contains("!join") && App.state == BotStates.LOBBY){
                    if (msg.getContent().equalsIgnoreCase("!join")) {
                        GameLobbyLogic.addPlayer(requester, sentChannel);
                    } else if (!msg.getMentionedUsers().isEmpty()){
                        GameLobbyLogic.addPlayers(msg.getMentionedUsers(), sentChannel);
                    }

                } else if (msg.getContent().equalsIgnoreCase("!clear") && App.state == BotStates.LOBBY){
                    sentChannel.getMessages(1000).thenAcceptAsync(MessageSet::deleteAll);
                    GameManager.getInstance().resetInstance();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TUI.sendLobbyInstruction(sentChannel);

                } else if (msg.getContent().equalsIgnoreCase("!start")){
                    if (App.state == BotStates.LOBBY){
                        if (!GameLobbyLogic.assignRoles()){
                            sentChannel.sendMessage("Not enough players yet.");
                        } else {
                            App.state = BotStates.ROLE_ASSIGNMENT;
                            sentChannel.sendMessage(App.state.getFancyName());
                        }
                    } else if (App.state == BotStates.ROLE_ASSIGNMENT){
                        App.state = BotStates.IN_GAME;
                        TextChannel channel = null;
                        Permissions wolfPerm = new PermissionsBuilder().setAllDenied().build();
                        try {
                            channel = new ServerTextChannelBuilder(GameManager.getInstance().getServer()).addPermissionOverwrite(GameManager.getInstance().getWolves(), ).setName("wolves").create().get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }

                        wolfChatID = channel.getIdAsString();
                    }

                } else if (msg.getContent().equalsIgnoreCase("!info")){
                    if (App.state == BotStates.LOBBY){
                        TUI.sendLobbyInstruction(sentChannel);
                    }

                } else if (msg.getContent().equalsIgnoreCase("!leave") && App.state == BotStates.LOBBY){
                    if (GameManager.getInstance().removeUser(requester)){
                        sentChannel.sendMessage("You have left.");
                    } else {
                        sentChannel.sendMessage("You are not in the lobby");
                    }

                } else if (msg.getContent().equalsIgnoreCase("!players")){
                    HashMap<User, Roles> players = GameManager.getInstance().getUsers();
                    if (players.keySet().isEmpty()){
                        sentChannel.sendMessage("There are no players in the lobby.");
                    } else {
                        sentChannel.sendMessage("The players in current session:");
                        GameLobbyLogic.sendUsers(players, sentChannel);
                    }
                } else if (msg.getContent().equalsIgnoreCase("!state")){
                    sentChannel.sendMessage(App.state.getFancyName());
                } else if (msg.getContent().equalsIgnoreCase("!gameState")){

                }
                break;

            case wolfChatID:

        }
    }

}
