package broccolizer.GameLogic;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class GameController {

    private static GameStates gameState;

    private static boolean doneCupid;
    private static boolean lifePotion;
    private static boolean deathPotion;
    private static ArrayList<User> cupidBonded;

    private static ArrayList<User> dying;
    private static ArrayList<User> dead;

    private static HashMap<User, User> votes;

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
        dying = new ArrayList<>();
        gameState = GameStates.CUPID;
        votes = new HashMap<>();
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

    public static boolean getLifePotion(){ return lifePotion; }

    public static boolean getDeathPotion() { return deathPotion; }

    public static boolean isDead(User user){
        return dead.contains(user);
    }

    public static boolean isDying(User user) { return dying.contains(user); }

    public static boolean playerDying(User user){
        if (
                DiscordController.getInstance().getUsers().keySet().contains(user) && !dead.contains(user) && !dying.contains(user)
        ){
            if (cupidBonded.contains(user)){
                cupidBonded.forEach((user1 -> dying.add(user1)));
            } else {
                dying.add(user);
            }
            return true;
        } else {
            return false;
        }
    }

    public static void checkWin(){
        if (noWolvesAlive() || noNonWolvesAlive()){
            if (noWolvesAlive()){
                DiscordController.getInstance().getLobbyChannel().sendMessage("Villagers won!");
            } else {
                DiscordController.getInstance().getLobbyChannel().sendMessage("Wolves won!");
            }
            instance = null;
            DiscordController.getInstance().reset();
        } else {
        }
    }

    private static boolean noWolvesAlive() {
        int amount;
        HashMap<User, Roles> players = DiscordController.getInstance().getUsers();
        ArrayList<User> wolves = new ArrayList<>();

        for (User each : players.keySet()){
            if (players.get(each) == Roles.WOLF){
                wolves.add(each);
            }
        }

        boolean allDead = true;
        for (User each : wolves){
            if (!dead.contains(each)){
                allDead = false;
            }
        }
        return allDead;
    }

    private static boolean noNonWolvesAlive() {
        int amount;
        HashMap<User, Roles> players = DiscordController.getInstance().getUsers();
        ArrayList<User> wolves = new ArrayList<>();

        for (User each : players.keySet()){
            if (players.get(each) != Roles.WOLF){
                wolves.add(each);
            }
        }

        boolean allDead = true;
        for (User each : wolves){
            if (!dead.contains(each)){
                allDead = false;
            }
        }
        return allDead;
    }

    public static void tickDying(){
        dead.addAll(dying);
        dying = new ArrayList<>();
    }

    public static boolean revivePlayer(User user){
        if (isDead(user)){
            dead.remove(user);
        } else if (isDying(user)){
            dying.remove(user);
        } else {
            return false;
        }
        return true;
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

    public static boolean vote(User voter, User voted){
        if (DiscordController.getInstance().getUsers().keySet().contains(voter) && DiscordController.getInstance().getUsers().keySet().contains(voted) && !voter.equals(voted)){
            votes.put(voter,voted);
            return false;
        }
        return false;
    }

    public static boolean votingReady(){
        if (votes.keySet().size() > 3){
            return true;
        } else {
            return false;
        }
    }

    public static User endVoting() {
        User mostVoted = null;
        int mostAmount = 0;
        for (User each : votes.values()){
            int amount = Collections.frequency(votes.values(), each);
            if (amount > mostAmount){
                mostVoted = each;
            }
        }
        votes = new HashMap<>();
        return mostVoted;
    }

    public void sendVotes(TextChannel sentChannel) {
        HashMap<User, Integer> voted = new HashMap<>();
        for (User each : votes.values()){
            if (!voted.keySet().contains(each)){
                voted.put(each, 1);
            } else {
                voted.put(each, voted.get(each) + 1);
            }
        }
        for (User each : voted.keySet()){
            sentChannel.sendMessage(each.getMentionTag() + " has " + voted.get(each) + " votes");
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
                tickDying();
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
                User witch = DiscordController.getUserWithRole(Roles.WITCH);
                if (!dead.contains(witch)){
                    DiscordController.getInstance().getWitchChannel().sendMessage(
                            "The wolves have slain.\n" +
                                    "It's now your turn, Witch. :woman_mage:\n" +
                                    "You can use your death and life potion both (seperate) once.\n" +
                                    "Type '!skip'"
                    );
                    User dyingPlayer = dying.get(0);
                    if (dyingPlayer.equals(witch)){
                        DiscordController.getInstance().getWitchChannel().sendMessage(
                                "The wolves have killed you, but with your last breath\n" +
                                        "you can still use your potions!\n" +
                                        "Including on yourself, the life potion!"
                        );
                    } else {
                        DiscordController.getInstance().getWitchChannel().sendMessage(
                                "The wolves have killed " + dyingPlayer.getMentionTag()
                        );
                    }
                    if (lifePotion){
                        DiscordController.getInstance().getWitchChannel().sendMessage(
                                "You can revive whoever is dead!" +
                                        "\nType !revive <@MentionTag> to revive the player."
                        );
                    }
                    if (deathPotion){
                        DiscordController.getInstance().getWitchChannel().sendMessage(
                                "You can kill whoever is alive!\n" +
                                        "Type !kill <@MentionTag> to kill the player."
                        );
                    }
                } else {
                    giveNextInstruction();
                }
        }
    }
}
