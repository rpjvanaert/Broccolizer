package broccolizer.GameLogic;

public enum GameStates {
    CUPID("Cupid is linking 2 lovers!"),
    ORACLE("The Oracle is getting a prophecy..."),
    WOLVES("The werewolves are searching for prey!"),
    WITCH("The witch is deriving power from the evil!"),
    VOTING("The villagers are voting a player to lynch!")
    ;



    private String fancyName;

    GameStates(String fancyName) { this.fancyName = fancyName; }

    public String getFancyName() { return this.fancyName; }
}
