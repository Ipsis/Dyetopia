package ipsis.dyetopia.gui.container;

public class ProgressBar {

    private ProgressBar() { }

    public static final int ID_GENERIC_START = 0;
    public static final int ID_GENERIC_END = 99;

    public static final int ID_TANK_FLUID_ID_START = 100;
    public static final int ID_TANK_FLUID_ID_END = 199;

    public static final int ID_TANK_FLUID_AMOUNT_START = 200;
    public static final int ID_TANK_FLUID_AMOUNT_END = 299;

    public static final int ID_ENERGY_START = 300;
    public static final int ID_ENERGY_END = 399;

    /* Fluids are per tank */
    private static final int TAG_FLUID_ID = 0x8000;
    private static final int TAG_FLUID_AMOUNT = 0x4000;

    private static final int TAG_ENERGY = 0x2000;

    private static final int ID_MASK = 0xFFFF;

    /* Energy ids */
    public static final int ID_ENERGY_STORED = 0x0;
    public static final int ID_ENERGY_CONSUMED = 0x1;
    public static final int ID_ENERGY_RECIPE = 0x2;

    public static int createIDFluidId(int tank) { return (TAG_FLUID_ID | tank) & ID_MASK; }
    public static int createIDFluidAmount(int tank) { return (TAG_FLUID_AMOUNT | tank) & ID_MASK; }
    public static int createIDEnergy(int id) { return (TAG_ENERGY | id) & ID_MASK; }
    public static int createIDGeneric(int id) { return (id) & ID_MASK; }

    public static ID_TYPE getIDType(int id) {
        if ((id & TAG_FLUID_ID) == TAG_FLUID_ID) return ID_TYPE.ID_FLUID_ID;
        if ((id & TAG_FLUID_AMOUNT) == TAG_FLUID_AMOUNT) return ID_TYPE.ID_FLUID_AMOUNT;
        if ((id & TAG_ENERGY) == TAG_ENERGY) return ID_TYPE.ID_ENERGY;
        return ID_TYPE.ID_GENERIC;
    }

    public static int getIDValue(int id) {
        if ((id & TAG_FLUID_ID) == TAG_FLUID_ID) return (id & ~TAG_FLUID_ID);
        if ((id & TAG_FLUID_AMOUNT) == TAG_FLUID_AMOUNT) return (id & ~TAG_FLUID_AMOUNT);
        if ((id & TAG_ENERGY) == TAG_ENERGY) return (id & ~TAG_ENERGY);
        return id;
    }

    public static enum ID_TYPE {
        ID_GENERIC,
        ID_FLUID_ID,
        ID_FLUID_AMOUNT,
        ID_ENERGY
    };

}
