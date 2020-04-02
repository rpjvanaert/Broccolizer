package broccolizer.GameLogic;

import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class GameManager {

    private static HashMap<User, Roles> inGame;
    private static ArrayList<User> players;
    private static GameStates gameState;

    private static Server server;

    private static GameManager instance;

    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    private GameManager(){
        inGame = new HashMap<>();
        players = new ArrayList<>();
        gameState = GameStates.NIGHT;
    }

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        GameManager.server = server;
    }

    public static boolean addUser(User user){
        if (!inGame.keySet().contains(user)){
            inGame.put(user, Roles.UNASSIGNED);
            players.add(user);
            return true;
        }
        return false;
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
            amountWolves = 4;
        } else {
            amountWolves = 3;
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
        instance = new GameManager();
    }

    public static HashMap<User, Roles> getUsers(){
        return inGame;
    }

    public static List<User> getWolves(){
        ArrayList<User> wolves = new ArrayList<>();
        for (User eachUser : players){
            if (inGame.get(eachUser) == Roles.WOLF){
                wolves.add(eachUser);
            }
        }
        return wolves;
    }
}
