package broccolizer.GameLogic;

public enum Roles {
    UNASSIGNED("Unassigned"),
    WOLF("Werewolf"),
    VILLAGER("Villager"),
    WITCH("Witch"),
    ORACLE("Oracle"),
    CUPID("Cupid"),
    LITTLE_GIRL("Little Girl");
    ;


    private String fancyName;

    Roles(String fancyName){
        this.fancyName = fancyName;
    }

    public String getFancyName() {
        return fancyName;
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
