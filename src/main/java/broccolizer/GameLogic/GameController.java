package broccolizer.GameLogic;

import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Arrays;

public class GameController {

    private static GameStates gameState;

    private static boolean doneCupid;
    private static boolean lifePotion;
    private static boolean deathPotion;
    private static ArrayList<User> cupidBonded;

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
        cupidBonded = new ArrayList<>(2);
        dead = new ArrayList<>();
        gameState = GameStates.CUPID;
    }

    public static boolean bondCupid(User bond1, User bond2){
        if (cupidBonded.isEmpty()){
            cupidBonded.addAll(Arrays.asList(bond1, bond2));
            bond1.sendMessage("You are bonded with " + bond2.getMentionTag() + "! Have a lovely honeymoon!:hearts:");
            bond2.sendMessage("You are bonded with " + bond1.getMentionTag() + "! Have a lovely honeymoon!:hearts:");
            return true;
        }
        return false;
    }

    public static boolean isDead(User user){
        return dead.contains(user);
    }

    public static boolean killPlayer(User user){
        if (!dead.contains(user)){
            if (cupidBonded.contains(user)){
                cupidBonded.forEach((user1 -> dead.add(user1)));
            } else {
                dead.add(user);
            }
            return true;
        } else {
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
                                    "You may not share this information directly with other villagers.\n" +
                                    "Convince them, Good luck."
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
