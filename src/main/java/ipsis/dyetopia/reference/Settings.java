package ipsis.dyetopia.reference;

public class Settings {

    public static class Trees {
        public static boolean enabled;
    }

    public static class Machines {
        public static final int DEF_TANK_CAPACITY = 16000;
        public static final int DEF_ENERGY_CAPACITY = 50000;
        public static final int DEF_RX_RF_TICK = 40;

        /* Squeezer */
        public static final int DEF_SQUEEZER_RF_TICK = 10;
        public static final int DEF_SQUEEZER_RF_RECIPE = 120;

        /* Mixer */
        public static final int DEF_MIXER_RF_TICK = 10;
        public static final int DEF_MIXER_RF_RECIPE = 120;

        public static final int DEF_MIXER_MB_RED = 1000;
        public static final int DEF_MIXER_MB_YELLOW = 1000;
        public static final int DEF_MIXER_MB_BLUE = 1000;
        public static final int DEF_MIXER_MB_WHITE = 1000;

        /* Stamper */
        public static final int DEF_STAMPER_RF_TICK = 10;
        public static final int DEF_STAMPER_RF_RECIPE = 120;

        /* Filler */
        public static final int DEF_FILLER_RF_TICK = 10;
        public static final int DEF_FILLER_DYE_TICK = 5;

        /* Painter */
        public static final int DEF_PAINTER_RF_TICK = 10;
        public static final int DEF_PAINTER_RF_RECIPE = 120;


        public static int tankCapacity;
        public static int energyCapacity;
        public static int energyRxTick;

        public static int squeezerRfTick;
        public static int squeezerRfRecipe;
        public static int mixerRfTick;
        public static int mixerRfRecipe;
        public static int stamperRfTick;
        public static int stamperRfRecipe;
        public static int fillerRfTick;
        public static int fillerDyeTick;
        public static int painterRfTick;
        public static int painterRfRecipe;

        public static int mixerAmountRed;
        public static int mixerAmountYellow;
        public static int mixerAmountBlue;
        public static int mixerAmountWhite;

    }

    public static class Items {

        public static final int DEF_DYEGUN_TANK_CAPACITY = 72 * 64;

        public static int dyeGunTankCapacity;
    }
}
