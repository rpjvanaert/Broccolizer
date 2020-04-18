package broccolizer.GameLogic;

import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class DiscordController {

    private static HashMap<User, Roles> inGame;
    private static ArrayList<User> players;

    private static TextChannel wolvesChannel;
    private static TextChannel witchChannel;
    private static TextChannel oracleChannel;
    private static TextChannel cupidChannel;
    private static TextChannel lobbyChannel;

    private static Server server;

    private static DiscordController instance;

    public static DiscordController getInstance(){
        if (instance == null){
            instance = new DiscordController();
        }
        return instance;
    }

    private DiscordController(){
        inGame = new HashMap<>();
        players = new ArrayList<>();

        wolvesChannel = null;
        witchChannel = null;
        oracleChannel = null;
        cupidChannel = null;
        lobbyChannel = null;
    }

    public static void reset(){ instance = null;}

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        DiscordController.server = server;
    }

    public static boolean addUser(User user){
        if (!inGame.keySet().contains(user)){
            inGame.put(user, Roles.UNASSIGNED);
            players.add(user);
            return true;
        }
        return false;
    }

    public static User getUserWithRole(Roles role){
        for(User user : inGame.keySet()){
            if (inGame.get(user) == role){
                return user;
            }
        }
        return null;
    }

    public static boolean removeUser(User user){
        if (inGame.keySet().contains(user)){
            inGame.remove(user);
            players.remove(user);
            return true;
        }
        return false;
    }

    public static void assignRoles(){
        assignRole(Roles.WITCH);
        assignRole(Roles.CUPID);
        assignRole(Roles.ORACLE);
        int amountWolves;
        if (players.size() >= 12){
            amountWolves = 3;
        } else {
            amountWolves = 2;
        }
        for (int i = 0; i < amountWolves; ++i){
            assignRole(Roles.WOLF);
        }

        while(inGame.values().contains(Roles.UNASSIGNED)){
            assignRole(Roles.VILLAGER);
        }
    }

    public static void assignRole(Roles role){
        boolean assigned = false;
        Random rng = new Random();
        while(!assigned){
            User player = players.get(rng.nextInt(players.size()));

            if (Roles.getRole(inGame.get(player).getFancyName()) == Roles.UNASSIGNED){
                inGame.put(player, role);
                player.sendMessage("You are assigned: " + role.getFancyName());
                System.out.println(player.getName() + " assigned to " + role.getFancyName());
                assigned = true;
            }
        }
    }

    public static void resetInstance(){
        instance = new DiscordController();
    }

    public static HashMap<User, Roles> getUsers(){
        return inGame;
    }

    public static TextChannel getLobbyChannel() {
        return lobbyChannel;
    }

    public static void setLobbyChannel(TextChannel lobbyChannel) {
        DiscordController.lobbyChannel = lobbyChannel;
    }

    public static TextChannel getWolvesChannel() {
        return wolvesChannel;
    }

    public static void setWolvesChannel(TextChannel wolvesChannel) {
        DiscordController.wolvesChannel = wolvesChannel;
    }

    public static TextChannel getWitchChannel() {
        return witchChannel;
    }

    public static void setWitchChannel(TextChannel witchChannel) {
        DiscordController.witchChannel = witchChannel;
    }

    public static TextChannel getOracleChannel() {
        return oracleChannel;
    }

    public static void setOracleChannel(TextChannel oracleChannel) {
        DiscordController.oracleChannel = oracleChannel;
    }

    public static TextChannel getCupidChannel() {
        return cupidChannel;
    }

    public static void setCupidChannel(TextChannel cupidChannel) {
        DiscordController.cupidChannel = cupidChannel;
    }
}
