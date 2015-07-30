package ipsis.dyetopia.gui.container;

public class ProgressBar {

    private ProgressBar() { }

    public static final int ID_GENERIC_START = 0;
    public static final int ID_GENERIC_END = 99;


    public static final int ID_ENERGY_START = 300;
    public static final int ID_ENERGY_END = 399;

    private static final int TAG_ENERGY = 0x2000;

    private static final int ID_MASK = 0xFFFF;

    /* Energy ids */
    public static final int ID_ENERGY_STORED = 0x0;
    public static final int ID_ENERGY_CONSUMED = 0x1;
    public static final int ID_ENERGY_RECIPE = 0x2;

    public static int createIDEnergy(int id) { return (TAG_ENERGY | id) & ID_MASK; }
    public static int createIDGeneric(int id) { return (id) & ID_MASK; }

    public static ID_TYPE getIDType(int id) {
        if ((id & TAG_ENERGY) == TAG_ENERGY) return ID_TYPE.ID_ENERGY;
        return ID_TYPE.ID_GENERIC;
    }

    public static int getIDValue(int id) {
        if ((id & TAG_ENERGY) == TAG_ENERGY) return (id & ~TAG_ENERGY);
        return id;
    }

    public enum ID_TYPE {
        ID_GENERIC,
        ID_ENERGY
    }

}
