package ipsis.dyetopia.reference;

import java.sql.Ref;

public class Lang {

    /* Tabs */
    public static final String TAB_ENERGY_TITLE = "hud.dyetopia:energy";
    public static final String TAG_INFO_TITLE = "hud.dyetopia:information";

    /* Energy */
    public static final String ENERGY_DRAW = "hud.dyetopia:energy.draw";
    public static final String ENERGY_RX_TICK = "hud.dyetopia:energy.rxtick";
    public static final String ENERGY_STORED = "hud.dyetopia:energy.stored";

    /* Dye Tanks */
    public static final String TANK_RED = "hud.dyetopia:tank.red";
    public static final String TANK_YELLOW = "hud.dyetopia:tank.yellow";
    public static final String TANK_BLUE = "hud.dyetopia:tank.blue";
    public static final String TANK_WHITE = "hud.dyetopia:tank.white";
    public static final String TANK_PURE = "hud.dyetopia:tank.pure";

    /* Machine Info */
    public static final String SQUEEZER_INFO = "hud.dyetopia:info.squeezer";
    public static final int SQUEEZER_INFO_LEN = 2;
    public static final String MIXER_INFO = "hud.dyetopia:info.mixer";
    public static final int MIXER_INFO_LEN = 3;
    public static final String STAMPER_INFO = "hud.dyetopia:info.stamper";
    public static final int STAMPER_INFO_LEN = 3;
    public static final String PAINTER_INFO = "hud.dyetopia:info.painter";
    public static final int PAINTER_INFO_LEN = 3;
    public static final String FILLER_INFO = "hud.dyetopia:info.filler";
    public static final int FILLER_INFO_LEN = 2;

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