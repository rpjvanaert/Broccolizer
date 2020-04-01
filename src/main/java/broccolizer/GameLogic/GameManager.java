package broccolizer.GameLogic;

import org.javacord.api.entity.user.User;

import java.util.HashSet;

public class GameManager {

    private static HashSet<User> inGame;

    private static GameManager instance;

    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    private GameManager(){
        inGame = new HashSet<>();
    }

    public static boolean addUser(User user){
        if (!inGame.contains(user)){
            inGame.add(user);
            return true;
        }
        return false;
    }

    public static boolean removeUser(User user){
        if (inGame.contains(user)){
            inGame.remove(user);
            return true;
        }
        return false;
    }

    public static void resetInstance(){
        instance = new GameManager();
    }

    public static HashSet<User> getUsers(){
        return inGame;
    }
}
