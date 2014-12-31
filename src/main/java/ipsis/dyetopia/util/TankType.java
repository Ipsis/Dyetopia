package ipsis.dyetopia.util;

import ipsis.dyetopia.reference.Lang;

public enum TankType {

    RED("redTank", Lang.TANK_RED),
    YELLOW("yellowTank", Lang.TANK_YELLOW),
    BLUE("blueTank", Lang.TANK_BLUE),
    WHITE("whiteTank", Lang.TANK_WHITE),
    PURE("pureTank", Lang.TANK_PURE);

    private String name;
    private String localisation;
    private TankType(String name, String localisation) {
        this.name = name;
        this.localisation = localisation;
    }

    public String getName() {
        return this.name;
    }

    public String getLocalisation() {
        return this.localisation;
    }
}
