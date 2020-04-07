package broccolizer;

public enum BotStates {
    LOBBY("Lobbying"),
    IN_GAME("In-Game");

    private String fancyName;

    BotStates(String fancyName){
        this.fancyName = fancyName;
    }

    public String getFancyName(){
        return this.fancyName;
    }

    public static BotStates getBotState(String fancyName){
        for (BotStates botState : BotStates.values()){
            if (botState.getFancyName().equalsIgnoreCase(fancyName)){
                return botState;
            }
        }
        return null;
    }
}

