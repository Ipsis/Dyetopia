package ipsis.dyetopia.reference;

public class Textures {

    public static final String RESOURCE_PREFIX = Reference.MOD_ID.toLowerCase() + ":";

    public static final class Gui {
        public static final String GUI_SHEET_LOCATION = "textures/gui/";

        public static final String FLUID_TANK = GUI_SHEET_LOCATION  + "elements/FluidTank.png";
        public static final String PROGRESS_LEFT_RIGHT = GUI_SHEET_LOCATION  + "elements/Progress_Mode1.png";
        public static final String ENERGY = GUI_SHEET_LOCATION  + "elements/Energy.png";
        public static final String TAB_RIGHT = GUI_SHEET_LOCATION  + "elements/Tab_Right.png";
        public static final String TAB_LEFT = GUI_SHEET_LOCATION  + "elements/Tab_Left.png";

        public static final String GUI_FILLER = RESOURCE_PREFIX + GUI_SHEET_LOCATION + "filler.png";
        public static final String GUI_MIXER = RESOURCE_PREFIX + GUI_SHEET_LOCATION + "mixer.png";
        public static final String GUI_PAINTER = RESOURCE_PREFIX + GUI_SHEET_LOCATION + "painter.png";
        public static final String GUI_SQUEEZER = RESOURCE_PREFIX + GUI_SHEET_LOCATION + "squeezer.png";
        public static final String GUI_STAMPER = RESOURCE_PREFIX + GUI_SHEET_LOCATION + "stamper.png";

        public static final int ENERGY_TAB_BACKGROUND = 0xFF8E56;
        public static final int INFO_TAB_BACKGROUND = 0xC6EAFF;
    }
}
