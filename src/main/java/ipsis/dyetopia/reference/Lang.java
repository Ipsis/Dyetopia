package ipsis.dyetopia.reference;

public class Lang {

    /* Dye Tanks */
    public static final String TANK_RED = "hud.dyetopia:tank.red";
    public static final String TANK_YELLOW = "hud.dyetopia:tank.yellow";
    public static final String TANK_BLUE = "hud.dyetopia:tank.blue";
    public static final String TANK_WHITE = "hud.dyetopia:tank.white";
    public static final String TANK_PURE = "hud.dyetopia:tank.pure";

    /**
     * New stuff below
     */

    /* General */
    private static final String TAG_TOOLTIP = "tooltip." + Reference.MOD_ID + ":";
    private static final String TAG_GUI = "gui." + Reference.MOD_ID + ":";
    private static final String TAG_CONFIG = "config." + Reference.MOD_ID + ":";
    private static final String TAG_TAB = "itemGroup." + Reference.MOD_ID + ":";

    /* Config */
    public static class Config {

        private static final String TREES_ENABLED = TAG_CONFIG + "trees.enabled";
        private static final String MACHINES_TANK_CAPACITY = TAG_CONFIG + "machines.tank.capacity";
        private static final String MACHINES_ENERGY_CAPACITY = TAG_CONFIG + "machines.energy.capacity";
        private static final String MACHINES_ENERGY_RX = TAG_CONFIG + "machines.energy.rxTick";
        private static final String MACHINES_ENERGY_SQUEEZER_RF_TICK = TAG_CONFIG + "machines.energy.squeezer.rfTick";
        private static final String MACHINES_ENERGY_MIXER_RF_TICK = TAG_CONFIG + "machines.energy.mixer.rfTick";
        private static final String MACHINES_ENERGY_STAMPER_RF_TICK = TAG_CONFIG + "machines.energy.stamer.rfTick";
        private static final String MACHINES_ENERGY_FILLER_RF_TICK = TAG_CONFIG + "machines.energy.filler.rfTick";
        private static final String MACHINES_ENERGY_PAINTER_RF_TICK = TAG_CONFIG + "machines.energy.painter.rfTick";

        private static final String MACHINES_DYE_FILLER_MB_TICK = TAG_CONFIG + "machines.dye.filler.dbTick";
    }

    public static class Gui {

        public static final String TAB_INFO = TAG_GUI + "tab.info";
        public static final String TAB_ENERGY = TAG_GUI + "tab.energy";

        public static final String ENERGY_DRAW = TAG_GUI + "energy.draw";
        public static final String ENERGY_RX_TICK = TAG_GUI + "energy.rxtick";
        public static final String ENERGY_STORED = TAG_GUI + "energy.stored";

        public static final String INFO_SQUEEZER = TAG_GUI + "info.squeezer";
        public static final String INFO_MIXER = TAG_GUI + "info.mixer";
        public static final String INFO_STAMPER = TAG_GUI + "info.stamper";
        public static final String INFO_PAINTER = TAG_GUI + "info.painter";
        public static final String INFO_FILLER = TAG_GUI + "info.filler";
    }

    public static class Tooltips {

        public static final String ITEM_DYE_BEANS = TAG_TOOLTIP + Names.Items.DYE_BEANS;
        public static final String ITEM_DYE_BLANK = TAG_TOOLTIP + Names.Items.DYE_BLANK;
        public static final String ITEM_DYE_CHUNK = TAG_TOOLTIP + Names.Items.DYE_CHUNK;
        public static final String ITEM_DYE_DROP = TAG_TOOLTIP + Names.Items.DYE_DROP;
        public static final String ITEM_DYE_GUN = TAG_TOOLTIP + Names.Items.DYE_GUN;
        public static final String ITEM_DYEMEAL = TAG_TOOLTIP + Names.Items.DYEMEAL;
        public static final String ITEM_ERASER = TAG_TOOLTIP + Names.Items.ERASER;

        public static final String BLOCK_SAPLING = TAG_TOOLTIP + Names.Blocks.BLOCK_SAPLING_DYE;
        public static final String BLOCK_ROOT_DYE = TAG_TOOLTIP + Names.Blocks.BLOCK_ROOT_DYE;

        public static final String BLOCK_CASING = TAG_TOOLTIP + Names.Blocks.BLOCK_MACHINE_CASING;
        public static final String BLOCK_CONTROLLER = TAG_TOOLTIP + Names.Blocks.BLOCK_MACHINE_CONTROLLER;
        public static final String BLOCK_FILLER = TAG_TOOLTIP + Names.Blocks.BLOCK_MACHINE_FILLER;
        public static final String BLOCK_MIXER = TAG_TOOLTIP + Names.Blocks.BLOCK_MACHINE_MIXER;
        public static final String BLOCK_PAINTER = TAG_TOOLTIP + Names.Blocks.BLOCK_MACHINE_PAINTER;
        public static final String BLOCK_SQUEEZER = TAG_TOOLTIP + Names.Blocks.BLOCK_MACHINE_SQUEEZER;
        public static final String BLOCK_STAMPER = TAG_TOOLTIP + Names.Blocks.BLOCK_MACHINE_STAMPER;
        public static final String BLOCK_VALVE = TAG_TOOLTIP + Names.Blocks.BLOCK_MACHINE_VALVE;

        public static final String HAS_DETAIL = TAG_TOOLTIP + "hasDetail";
        public static final String MULTIBLOCK = TAG_TOOLTIP + "multiblock";
        public static final String REQUIRES_RF = TAG_TOOLTIP + "requires.rf";
        public static final String REQUIRES_DYE = TAG_TOOLTIP + "requires.dye";
    }

}