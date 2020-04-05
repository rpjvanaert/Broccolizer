package broccolizer.GameLogic;

public enum GameStates {
    CUPID("Cupid is linking 2 lovers!"),
    ORACLE("The Oracle is getting a prophecy...");



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
