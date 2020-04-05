package broccolizer.GameLogic;

public class GameController {

    private static GameStates gameState;

    private static boolean doneCupid;

    private static GameController instance;

    public static GameController getInstance(){
        if (instance == null){
            instance = new GameController();
        }
        return instance;
    }

    public GameController(){
        doneCupid = false;
    }

    public static boolean cupidDone(){
        if (doneCupid){
            return true;
        } else {
            doneCupid = true;
            return false;
        }
    }
}
