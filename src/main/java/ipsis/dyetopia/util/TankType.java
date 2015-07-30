package ipsis.dyetopia.util;

import ipsis.dyetopia.reference.Lang;

public enum TankType {

    RED("redTank", Lang.Gui.TANKS_RED),
    YELLOW("yellowTank", Lang.Gui.TANKS_YELLOW),
    BLUE("blueTank", Lang.Gui.TANKS_BLUE),
    WHITE("whiteTank", Lang.Gui.TANKS_WHITE),
    PURE("pureTank", Lang.Gui.TANKS_PURE);

    private String name;
    private String localisation;
    TankType(String name, String localisation) {
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
