package broccolizer.GameLogic;

public enum GameStates {
    NIGHT("Night"),
    DAY("Day");


    private String fancyName;

    GameStates(String fancyName) { this.fancyName = fancyName; }

    public String getFancyName() { return this.fancyName; }

    public static GameStates getGameState(String fancyName){
        for (GameStates gameState : GameStates.values()){
            if (gameState.getFancyName().equalsIgnoreCase(fancyName)){
                return gameState;
            }
        }
        return null;
    }
}
