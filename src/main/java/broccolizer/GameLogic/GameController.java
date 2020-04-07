package broccolizer.GameLogic;

import org.javacord.api.entity.user.User;

import java.util.ArrayList;

public class GameController {

    private static GameStates gameState;

    private static boolean doneCupid;
    private static boolean lifePotion;
    private static boolean deathPotion;

    private static ArrayList<User> dead;

    private static GameController instance;

    public static GameController getInstance(){
        if (instance == null){
            instance = new GameController();
        }
        return instance;
    }

    public GameController(){
        doneCupid = false;
        lifePotion = true;
        deathPotion = true;
        dead = new ArrayList<>();
    }

    public static boolean cupidDone(){
        if (doneCupid){
            return true;
        } else {
            doneCupid = true;
            return false;
        }
    }

    public static GameStates getGameState(){ return gameState; }

    private static void nextGameState(){
        switch (gameState){
            case CUPID:
            case VOTING:
                gameState = GameStates.ORACLE;
                break;
            case ORACLE:
                gameState = GameStates.WOLVES;
                break;
            case WOLVES:
                gameState = GameStates.WITCH;
                break;
            case WITCH:
                gameState = GameStates.VOTING;
        }
    }

    public static void giveNextInstruction(){
        nextGameState();
        switch (gameState){
            case ORACLE:
                if (!dead.contains(DiscordController.getInstance().getUserWithRole(Roles.ORACLE))){
                    DiscordController.getInstance().getOracleChannel().sendMessage(
                            "It's time to choose your prophecy\n" +
                                    "You can get a identity of 1 player.\n" +
                                    "!expose <@MentionTag> to reveal the identity of the player\n" +
                                    "You may not share this information directly with other villagers"
                    );
                } else {
                    giveNextInstruction();
                }
                break;
            case WOLVES:
                DiscordController.getInstance().getWolvesChannel().sendMessage(
                        "It's is time to FEAST!!!\n" +
                                ":wolf:\n" +
                                "Discuss among the wolves who to kill...\n" +
                                "When decided type !kill <@MentionTag>\n" +
                                "Only 1 kill each night.\n" +
                                "Howl to the moon!! :full_moon:"
                );
                break;
            case VOTING:
                DiscordController.getInstance().getLobbyChannel().sendMessage(
                    "Hello there Millers Hollow!\n" +
                            "It's time to vote a player to lynch!\n" +
                            "For whoever is alive;\n" +
                            "Type \"!vote <@MentionTag>\" to vote the player to lynch.\n" +
                            "Discuss and when all have voted and set,\n" +
                            "Command \"!conclude\" when finished.\n" +
                            ":homes: Millers Hollow! :homes:"
                );
                break;
            case WITCH:
                if (!dead.contains(DiscordController.getUserWithRole(Roles.WITCH))){
                    DiscordController.getInstance().getLobbyChannel().sendMessage(
                            "The wolves have slain.\n" +
                                    "It's now your turn, Witch. :woman_mage:"
                    );
                } else {
                    giveNextInstruction();
                }
        }
    }
}
