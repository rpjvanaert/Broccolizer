package broccolizer.GameLogic;

import org.javacord.api.entity.user.User;

import java.util.HashSet;

public class PlayerManager {
    private static HashSet<User> inGame;

    private static PlayerManager instance;

    public static PlayerManager getInstance(){
        if (instance == null){
            instance = new PlayerManager();
        }
        return instance;
    }

    private PlayerManager(){
        inGame = new HashSet<>();
    }

    public static void addUser(User user){
        if (!inGame.contains(user)){
            inGame.add(user);
        }
    }

    public static HashSet<User> getUsers(){
        return inGame;
    }
}
