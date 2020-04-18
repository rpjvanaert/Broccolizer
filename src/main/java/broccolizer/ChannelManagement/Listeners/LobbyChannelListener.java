package broccolizer.ChannelManagement.Listeners;

import broccolizer.App;
import broccolizer.BotStates;
import broccolizer.ChannelManagement.ChannelManager;
import broccolizer.GameLogic.*;
import broccolizer.Generators.GeneratorAnimalAPI;
import broccolizer.Generators.MemeGenerator;
import broccolizer.Information;
import broccolizer.ChannelManagement.TUI;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.MessageSet;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.HashMap;

public class LobbyChannelListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event){
        Message msg = event.getMessage();

        User requester = msg.getAuthor().asUser().get();

        TextChannel sentChannel = msg.getChannel();

        if (sentChannel.getIdAsString().equalsIgnoreCase(Information.getLobbyID())){
            if (msg.getContent().contains("!join") && App.state == BotStates.LOBBY){
                if (msg.getContent().equalsIgnoreCase("!join")) {
                    UserLobbyLogic.addPlayer(requester, sentChannel);
                } else if (!msg.getMentionedUsers().isEmpty()){
                    UserLobbyLogic.addPlayers(msg.getMentionedUsers(), sentChannel);
                }

            } else if (msg.getContent().equalsIgnoreCase("!clear") && App.state == BotStates.LOBBY){
                sentChannel.getMessages(1000).thenAcceptAsync(MessageSet::deleteAll);
                DiscordController.getInstance().resetInstance();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                TUI.sendLobbyInstruction(sentChannel);

            } else if (msg.getContent().equalsIgnoreCase("!start")){
                if (App.state == BotStates.LOBBY){
                    if (!UserLobbyLogic.assignRoles()){
                        sentChannel.sendMessage("Not enough players yet.");
                    } else {
                        App.state = BotStates.IN_GAME;
                        ChannelManager.setupChannels();
                        TUI.sendRoleChannelInstruction();
                        GameController.getInstance();
                    }
                }

            } else if (msg.getContent().equalsIgnoreCase("!info")){
                if (App.state == BotStates.LOBBY){
                    TUI.sendLobbyInstruction(sentChannel);
                } else if (App.state == BotStates.IN_GAME){
                    TUI.sendGameInstruction(sentChannel);
                }

            } else if (msg.getContent().equalsIgnoreCase("!leave") && App.state == BotStates.LOBBY){
                if (DiscordController.getInstance().removeUser(requester)){
                    sentChannel.sendMessage("You have left.");
                } else {
                    sentChannel.sendMessage("You are not in the lobby");
                }

            } else if (msg.getContent().equalsIgnoreCase("!players")){
                HashMap<User, Roles> players = DiscordController.getInstance().getUsers();
                if (players.keySet().isEmpty()){
                    sentChannel.sendMessage("There are no players in the lobby.");
                } else {
                    sentChannel.sendMessage("The players in current session:");
                    UserLobbyLogic.sendUsers(players, sentChannel);
                }

            } else if (msg.getContent().equalsIgnoreCase("!state")){
                sentChannel.sendMessage(App.state.getFancyName());

            } else if (msg.getContent().equalsIgnoreCase("!gameState")){
                if (App.state == BotStates.LOBBY){
                    sentChannel.sendMessage("Not yet in game!");
                } else {
                    sentChannel.sendMessage(GameController.getInstance().getGameState().getFancyName());
                }

            } else if (msg.getContent().equalsIgnoreCase("!meme")){
                new MessageBuilder().append(MemeGenerator.getInstance().getRandomTitle()).addFile(MemeGenerator.getInstance().getRandomMeme()).send(sentChannel);

            } else if (msg.getContent().equalsIgnoreCase("!dog")){
                new MessageBuilder().append(GeneratorAnimalAPI.getDogTitle()).addFile(GeneratorAnimalAPI.getDogURL()).send(sentChannel);

            } else if (msg.getContent().equalsIgnoreCase("!cat")){
                new MessageBuilder().append(GeneratorAnimalAPI.getCatTitle()).addFile(GeneratorAnimalAPI.getCatURL()).send(sentChannel);

            } else if (msg.getContent().contains("!dogs ") && msg.getContent().length() == 7){
                int times = Integer.parseInt(msg.getContent().substring(6));
                if (times > 0){
                    if (times > 5){
                        times = 5;
                    }
                    for (int i = 0; i < times; ++i){
                        new MessageBuilder().append(GeneratorAnimalAPI.getDogTitle()).addFile(GeneratorAnimalAPI.getDogURL()).send(sentChannel);
                    }
                }

            } else if (msg.getContent().contains("!cats ") && msg.getContent().length() == 7){
                int times = Integer.parseInt(msg.getContent().substring(6));
                if (times > 0){
                    if (times > 5){
                        times = 5;
                    }
                    for (int i = 0; i < times; ++i){
                        new MessageBuilder().append(GeneratorAnimalAPI.getCatTitle()).addFile(GeneratorAnimalAPI.getCatURL()).send(sentChannel);
                    }
                }
                // Check gameState VOTING and content on !vote
            } else if (GameController.getInstance().getGameState() == GameStates.VOTING){
                if (msg.getContent().contains("!vote") & msg.getMentionedUsers().size() == 1){
                    if (GameController.getInstance().vote(msg.getUserAuthor().get(), msg.getMentionedUsers().get(0))){

                    } else {
                        sentChannel.sendMessage("Vote error: user not found.");
                    }
                } else if (msg.getContent().equalsIgnoreCase("!votes")){
                    GameController.getInstance().sendVotes(sentChannel);
                } else if (msg.getContent().equalsIgnoreCase("!endVoting") && GameController.getInstance().votingReady()){
                User lynch = GameController.getInstance().endVoting();
                sentChannel.sendMessage(lynch + " has been lynched!");
                GameController.getInstance().killPlayer(lynch);
                GameController.getInstance().checkWin();
                GameController.getInstance().giveNextInstruction();
                } else {
                    sentChannel.sendMessage("Voting error");
                }
            }
        }
    }
}
