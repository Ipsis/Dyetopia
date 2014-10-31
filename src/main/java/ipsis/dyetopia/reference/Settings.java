package ipsis.dyetopia.reference;

public class Settings {

    public static class Trees {
        public static boolean enabled;
    }

    public static class Machines {
        public static final int DEF_TANK_CAPACITY = 40000;
        public static final int DEF_ENERGY_CAPACITY = 50000;
        public static final int DEF_SQUEEZER_RF_TICK = 10;
        public static final int DEF_MIXER_RF_TICK = 10;
        public static final int DEF_SQUEEZER_RF_RECIPE = 120;
        public static final int DEF_MIXER_RF_RECIPE = 120;

        public static int tankCapacity;
        public static int energyCapacity;
        public static int squeezerRfTick;
        public static int mixerRfTick;
        public static int squeezerRfRecipe;
        public static int mixerRfRecipe;
    }
}
