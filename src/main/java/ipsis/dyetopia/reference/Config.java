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
        public static final String SQUEEZER_RF_TICK = "squeezerRfTick";
        public static final String SQUEEZER_RF_TICK_TXT = "Squeezer RF/tick";
        public static final String MIXER_RF_TICK = "mixerRfTick";
        public static final String MIXER_RF_TICK_TXT = "Mixer RF/tick";

        public static final String MIXER_RF_RECIPE = "mixerRfRecipe";
        public static final String MIXER_RF_RECIPE_TXT = "Mixer recipe cost (RF)";
        public static final String SQUEEZER_RF_RECIPE = "squeezerRfRecipe";
        public static final String SQUEEZER_RF_RECIPE_TXT = "Squeezer recipe cost (RF)";
    }
}
