package ipsis.dyetopia.reference;

public class Settings {

    public static class Trees {
        public static boolean enabled;
    }

    public static class Machines {
        public static final int DEF_TANK_CAPACITY = 16000;
        public static final int DEF_ENERGY_CAPACITY = 50000;

        /* Squeezer */
        public static final int DEF_SQUEEZER_RF_TICK = 10;
        public static final int DEF_SQUEEZER_RF_RECIPE = 120;

        /* Mixer */
        public static final int DEF_MIXER_RF_TICK = 10;
        public static final int DEF_MIXER_RF_RECIPE = 120;

        /* Stamper */
        public static final int DEF_STAMPER_RF_TICK = 10;
        public static final int DEF_STAMPER_RF_RECIPE = 120;
        //public static final int DEF_STAMPER_PURE_COST = 72;

        /* Filler */
        public static final int DEF_FILLER_RF_TICK = 10;
        public static final int DEF_FILLER_RF_RECIPE = 120;

        /* Painter */
        public static final int DEF_PAINTER_RF_TICK = 10;
        public static final int DEF_PAINTER_RF_RECIPE = 120;



        public static int tankCapacity;
        public static int energyCapacity;

        public static int squeezerRfTick;
        public static int squeezerRfRecipe;
        public static int mixerRfTick;
        public static int mixerRfRecipe;
        public static int stamperRfTick;
        public static int stamperRfRecipe;
        public static int stamperPureCost;
        public static int fillerRfTick;
        public static int fillerRfRecipe;
        public static int painterRfTick;
        public static int painterRfRecipe;

    }

    public static class Items {

        public static final int DEF_DYEGUN_TANK_CAPACITY = 72 * 64;

        public static int dyeGunTankCapacity;
    }
}
