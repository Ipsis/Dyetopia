package ipsis.dyetopia.reference;


public class Config {

    public static class Trees {
        public static final String ENABLED = "treesEnabled";
    }

    public static class Machines {
        public static final String TANK_CAPACITY = "tankCapacity";
        public static final String ENERGY_CAPACITY = "energyCapacity";
        public static final String ENERGY_RX_TICK = "energyRxTick";
        public static final String SQUEEZER_RF_TICK = "squeezerRfTick";
        public static final String MIXER_RF_TICK = "mixerRfTick";
        public static final String STAMPER_RF_TICK = "stamperRfTick";
        public static final String FILLER_RF_TICK = "fillerRfTick";
        public static final String FILLER_DYE_TICK = "fillerDyeTick";
        public static final String PAINTER_RF_TICK = "painterRfTick";

        public static final String MIXER_RF_RECIPE = "mixerRfRecipe";
        public static final String SQUEEZER_RF_RECIPE = "squeezerRfRecipe";
        public static final String STAMPER_RF_RECIPE = "stamperRfRecipe";
        public static final String PAINTER_RF_RECIPE = "painterRfRecipe";

        public static final String MIXER_RECIPE_RED = "mixerAmountRed";
        public static final String MIXER_RECIPE_YELLOW = "mixerAmountYellow";
        public static final String MIXER_RECIPE_BLUE = "mixerAmountBlue";
        public static final String MIXER_RECIPE_WHITE = "mixerAmountWhite";
    }

    public static class Items {

        public static final String DYEGUN_TANK_CAPACITY = "dyeGunTankCapacity";
    }

    public static enum SupportedMods {

        APPLIED_ENERGISTICS("ae2"),
        BIBLIOCRAFT("bibliocraft"),
        BUILDCRAFT("buildcraft"),
        CHISEL("chisel"),
        FORESTRY("forestry"),
        HARVESTCRAFT("harvestcraft"),
        MFR("mfr"),
        OPENBLOCKS("openblocks"),
        PROJECTRED("projectred"),
        RAILCRAFT("railcraft"),
        TINKERS("tconstruct"),
        THAUMCRAFT("thaumcraft"),
        THERMAL_EXPANSION("thermalexp"),
        EXTRA_UTILS("xutils");

        private String filename;
        private SupportedMods(String filename) { this.filename = filename; }

        public String getFilename() { return this.filename; }
    }
}
