package ipsis.dyetopia.reference;

public class Lang {

    /* General */
    private static final String TAG_TOOLTIP = "tooltip." + Reference.MOD_ID + ":";
    private static final String TAG_GUI = "gui." + Reference.MOD_ID + ":";
    private static final String TAG_CONFIG = "config." + Reference.MOD_ID + ":";
    private static final String TAG_MESSAGE = "message." + Reference.MOD_ID + ":";

    public static final String TAG_TAB = "itemGroup." + Reference.MOD_ID + ":" + Reference.MOD_NAME;

    /* Config */
    public static class Config {

        public static final String TREES_ENABLED = TAG_CONFIG + "trees.enabled";
        public static final String MACHINES_TANK_CAPACITY = TAG_CONFIG + "machines.tank.capacity";
        public static final String MACHINES_ENERGY_CAPACITY = TAG_CONFIG + "machines.energy.capacity";
        public static final String MACHINES_ENERGY_RX_TICK = TAG_CONFIG + "machines.energy.rxTick";
        public static final String MACHINES_ENERGY_SQUEEZER_RF_TICK = TAG_CONFIG + "machines.energy.squeezer.rfTick";
        public static final String MACHINES_ENERGY_MIXER_RF_TICK = TAG_CONFIG + "machines.energy.mixer.rfTick";
        public static final String MACHINES_ENERGY_STAMPER_RF_TICK = TAG_CONFIG + "machines.energy.stamper.rfTick";
        public static final String MACHINES_ENERGY_FILLER_RF_TICK = TAG_CONFIG + "machines.energy.filler.rfTick";
        public static final String MACHINES_ENERGY_PAINTER_RF_TICK = TAG_CONFIG + "machines.energy.painter.rfTick";

        public static final String ITEM_DYEGUN_CAPACITY = TAG_CONFIG + Names.Items.DYE_GUN + ".capacity";

        public static final String MACHINES_RECIPE_SQUEEZER_ENERGY = TAG_CONFIG + "machines.recipe.squeezer.energy";
        public static final String MACHINES_RECIPE_MIXER_ENERGY = TAG_CONFIG + "machines.recipe.mixer.energy";
        public static final String MACHINES_RECIPE_STAMPER_ENERGY = TAG_CONFIG + "machines.recipe.stamper.energy";
        public static final String MACHINES_RECIPE_PAINTER_ENERGY = TAG_CONFIG + "machines.recipe.painter.energy";

        public static final String MACHINES_DYE_FILLER_MB_TICK = TAG_CONFIG + "machines.dye.filler.dbTick";

        public static final String MACHINES_RECIPE_MIXER_RED_AMOUNT = TAG_CONFIG + "machines.recipe.mixer.amount.red";
        public static final String MACHINES_RECIPE_MIXER_YELLOW_AMOUNT = TAG_CONFIG + "machines.recipe.mixer.amount.yellow";
        public static final String MACHINES_RECIPE_MIXER_BLUE_AMOUNT = TAG_CONFIG + "machines.recipe.mixer.amount.blue";
        public static final String MACHINES_RECIPE_MIXER_WHITE_AMOUNT = TAG_CONFIG + "machines.recipe.mixer.amount.white";

    }

    public static class Gui {

        public static final String TAB_INFO = TAG_GUI + "tab.info";
        public static final String TAB_ENERGY = TAG_GUI + "tab.energy";
        public static final String TAB_TANKS = TAG_GUI + "tab.tanks";

        public static final String ENERGY_DRAW = TAG_GUI + "energy.draw";
        public static final String ENERGY_RX_TICK = TAG_GUI + "energy.rxtick";
        public static final String ENERGY_STORED = TAG_GUI + "energy.stored";

        public static final String INFO_SQUEEZER = TAG_GUI + "info.squeezer";
        public static final String INFO_MIXER = TAG_GUI + "info.mixer";
        public static final String INFO_STAMPER = TAG_GUI + "info.stamper";
        public static final String INFO_PAINTER = TAG_GUI + "info.painter";
        public static final String INFO_FILLER = TAG_GUI + "info.filler";

        public static final String TANKS_RED = TAG_GUI + "tanks.red";
        public static final String TANKS_YELLOW = TAG_GUI + "tanks.yellow";
        public static final String TANKS_BLUE = TAG_GUI + "tanks.blue";
        public static final String TANKS_WHITE = TAG_GUI + "tanks.white";
        public static final String TANKS_PURE = TAG_GUI + "tanks.pure";

        /* Just hook into the block names for now */
        public static final String TITLE_SQUEEZER = "tile." + Reference.MOD_ID + ":" + Names.Blocks.BLOCK_MACHINE_SQUEEZER + ".name";
        public static final String TITLE_MIXER = "tile." + Reference.MOD_ID + ":" + Names.Blocks.BLOCK_MACHINE_MIXER + ".name";
        public static final String TITLE_STAMPER = "tile." + Reference.MOD_ID + ":" + Names.Blocks.BLOCK_MACHINE_STAMPER + ".name";
        public static final String TITLE_PAINTER = "tile." + Reference.MOD_ID + ":" + Names.Blocks.BLOCK_MACHINE_PAINTER + ".name";
        public static final String TITLE_FILLER = "tile." + Reference.MOD_ID + ":" + Names.Blocks.BLOCK_MACHINE_FILLER + ".name";
    }


    public static class Tooltips {

        public static final String ITEM_DYE_BEANS = TAG_TOOLTIP + Names.Items.DYE_BEANS;
        public static final String ITEM_DYE_BLANK = TAG_TOOLTIP + Names.Items.DYE_BLANK;
        public static final String ITEM_DYE_CHUNK = TAG_TOOLTIP + Names.Items.DYE_CHUNK;
        public static final String ITEM_DYE_DROP = TAG_TOOLTIP + Names.Items.DYE_DROP;
        public static final String ITEM_DYE_GUN = TAG_TOOLTIP + Names.Items.DYE_GUN;
        public static final String ITEM_DYEMEAL = TAG_TOOLTIP + Names.Items.DYEMEAL;
        public static final String ITEM_ERASER = TAG_TOOLTIP + Names.Items.ERASER;

        public static final String CREATIVE_MODE = TAG_TOOLTIP + "creativeOnly";

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

    public static class Messages {

        public static final String NO_RECOLOR = TAG_MESSAGE + "noRecolor";
        public static final String PAINTER_ONLY = TAG_MESSAGE + "painterOnly";
        public static final String NOT_ENOUGH_DYE = TAG_MESSAGE + "notEnoughDye";
    }

}