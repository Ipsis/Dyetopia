package ipsis.dyetopia.reference;


public class Config {

    public static class Trees {
        public static final String ENABLED = "treesEnabled";
    }

    public static class Machines {
        public static final String TANK_CAPACITY = "tankCapacity";
        public static final String TANK_CAPACITY_TXT = "Capacity of the machine tanks (mB)";
        public static final String ENERGY_CAPACITY = "energyCapacity";
        public static final String ENERGY_CAPACITY_TXT = "Capacity of the machine energy storage (RF)";
        public static final String ENERGY_RX_TICK = "energyRxTick";
        public static final String ENERGY_RX_TICK_TXT = "Receive RF/tick";
        public static final String SQUEEZER_RF_TICK = "squeezerRfTick";
        public static final String SQUEEZER_RF_TICK_TXT = "Squeezer RF/tick";
        public static final String MIXER_RF_TICK = "mixerRfTick";
        public static final String MIXER_RF_TICK_TXT = "Mixer RF/tick";
        public static final String STAMPER_RF_TICK = "stamperRfTick";
        public static final String STAMPER_RF_TICK_TXT = "Stamper RF/tick";
        public static final String FILLER_RF_TICK = "fillerRfTick";
        public static final String FILLER_RF_TICK_TXT = "Filler RF/tick";
        public static final String FILLER_DYE_TICK = "fillerDyeTick";
        public static final String FILLER_DYE_TICK_TXT = "Filler Dye/tick (mB)";
        public static final String PAINTER_RF_TICK = "painterRfTick";
        public static final String PAINTER_RF_TICK_TXT = "Painter RF/tick";

        public static final String MIXER_RF_RECIPE = "mixerRfRecipe";
        public static final String MIXER_RF_RECIPE_TXT = "Mixer recipe cost (RF)";
        public static final String SQUEEZER_RF_RECIPE = "squeezerRfRecipe";
        public static final String SQUEEZER_RF_RECIPE_TXT = "Squeezer recipe cost (RF)";
        public static final String STAMPER_RF_RECIPE = "stamperRfRecipe";
        public static final String STAMPER_RF_RECIPE_TXT = "Stamper recipe cost (RF)";
        public static final String PAINTER_RF_RECIPE = "painterRfRecipe";
        public static final String PAINTER_RF_RECIPE_TXT = "Painter recipe cost (RF)";
    }

    public static class Items {

        public static final String DYEGUN_TANK_CAPACITY = "dyeGunTankCapacity";
        public static final String DYEGUN_TANK_CAPACITY_TXT = "Dye Gun Tank Capacity (mB)";
    }
}
