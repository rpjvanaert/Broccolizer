package broccolizer.GameLogic;

public enum Roles {
    UNASSIGNED("Unassigned", false),
    WOLF("Werewolf", false),
    VILLAGER("Villager", false),
    WITCH("Witch", true),
    ORACLE("Oracle", true),
    CUPID("Cupid", true),

    ;


    private String fancyName;
    private boolean maxOne;

    Roles(String fancyName, boolean maxOne){
        this.fancyName = fancyName;
        this.maxOne = maxOne;
    }

    public String getFancyName() {
        return fancyName;
    }

    public boolean getMaxOne() {
        return maxOne;
    }

    public static Roles getRole(String fancyName){
        for (Roles role : Roles.values()){
            if (role.getFancyName().equalsIgnoreCase(fancyName)){
                return role;
            }
        }
        return null;
    }
}
