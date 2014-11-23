package ipsis.dyetopia.util;

import cofh.lib.util.helpers.ColorHelper;
import ipsis.dyetopia.reference.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

public class DyeHelper {

    /**
     * The proportion of RYBW that is produced for each dye type
     * Stored in 72ths (LCM of 2,3,4,6,8,9!)
     */
    private static int LCM = 72;
    public static int getLCM() { return LCM; }


    private static final String[] DYE_OREDICT_NAME = {
            "dyeBlack", "dyeRed", "dyeGreen", "dyeBrown", "dyeBlue", "dyePurple", "dyeCyan", "dyeLightGray",
            "dyeGray", "dyePink", "dyeLime", "dyeYellow", "dyeLightBlue", "dyeMagenta", "dyeOrange", "dyeWhite"
    };

    public static enum DyeType {

        BLACK(      0,  DYE_OREDICT_NAME[0],    ColorHelper.DYE_BLACK,      LCM/3,  LCM/3,  LCM/3,          0),
        RED(        1,  DYE_OREDICT_NAME[1],    ColorHelper.DYE_RED,          LCM,      0,      0,          0),
        GREEN(      2,  DYE_OREDICT_NAME[2],    ColorHelper.DYE_GREEN,          0,  LCM/2,  LCM/2,          0),
        BROWN(      3,  DYE_OREDICT_NAME[3],    ColorHelper.DYE_BROWN,  3*(LCM/4),  LCM/8,  LCM/8,          0),
        BLUE(       4,  DYE_OREDICT_NAME[4],    ColorHelper.DYE_BLUE,           0,      0,    LCM,          0),
        PURPLE(     5,  DYE_OREDICT_NAME[5],    ColorHelper.DYE_PURPLE,     LCM/2,      0,  LCM/2,          0),
        CYAN(       6,  DYE_OREDICT_NAME[6],    ColorHelper.DYE_CYAN,           0,      0,  LCM/4,  3*(LCM/4)),
        LIGHTGRAY(  7,  DYE_OREDICT_NAME[7],    ColorHelper.DYE_LIGHT_GRAY, LCM/9,  LCM/9,  LCM/9,  2*(LCM/3)),
        GRAY(       8,  DYE_OREDICT_NAME[8],    ColorHelper.DYE_GRAY,       LCM/6,  LCM/6,  LCM/6,      LCM/2),
        PINK(       9,  DYE_OREDICT_NAME[9],    ColorHelper.DYE_PINK,       LCM/2,      0,      0,      LCM/2),
        LIME(       10, DYE_OREDICT_NAME[10],   ColorHelper.DYE_LIME,           0,  LCM/4,  LCM/4,      LCM/2),
        YELLOW(     11, DYE_OREDICT_NAME[11],   ColorHelper.DYE_YELLOW,         0,    LCM,      0,          0),
        LIGHTBLUE(  12, DYE_OREDICT_NAME[12],   ColorHelper.DYE_LIGHT_BLUE,     0,      0,  LCM/2,      LCM/2),
        MAGENTA(    13, DYE_OREDICT_NAME[13],   ColorHelper.DYE_MAGENTA,    LCM/2,      0,  LCM/4,      LCM/4),
        ORANGE(     14, DYE_OREDICT_NAME[14],   ColorHelper.DYE_ORANGE,     LCM/2,  LCM/2,      0,          0),
        WHITE(      15, DYE_OREDICT_NAME[15],   ColorHelper.DYE_WHITE,          0,      0,      0,        LCM),

        INVALID(    99,        "INVALID DYE",   ColorHelper.DYE_WHITE,          0,      0,      0,          0);

        public static DyeType[] VALID_DYES = {
                BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHTGRAY, GRAY,
                PINK, LIME, YELLOW, LIGHTBLUE, MAGENTA, ORANGE, WHITE
        };

        /* The damage value. The is the vanilla dmg->dye value */
        private int dmg;

        /* The ore dictionary name of the dye */
        private String oreDictName;

        /* The CoFHLib color code */
        private int colorCode;

        /* The color proportions of RYBW */
        private int red;
        private int yellow;
        private int blue;
        private int white;

        private DyeType(int dmg, String oreDictName, int code, int red, int yellow, int blue, int white) {
            this.dmg = dmg;
            this.oreDictName = oreDictName;
            this.colorCode = code;
            this.red = red;
            this.yellow = yellow;
            this.blue = blue;
            this.white = white;
        }

        public int getDmg() { return this.dmg; }
        public String getOreDictName() { return this.oreDictName; }
        public int getColorCode() { return this.colorCode; }

        public DyeType getNext() {

            int ord = this.ordinal();
            ord++;
            if (ord >= VALID_DYES.length)
                ord = 0;

            return VALID_DYES[ord];
        }

        public ItemStack getStack() {

            return new ItemStack(Items.dye, 1, this.dmg);
        }

        public IIcon getIcon() {

            return Items.dye.getIconFromDamage(this.dmg);
        }

        public int getRed() { return red; }
        public int getYellow() { return yellow; }
        public int getBlue() { return blue; }
        public int getWhite() { return white; }

        public String getDisplayName() {

            return StatCollector.translateToLocal("general." + Reference.MOD_ID + ":" + oreDictName);
        }

        /* Get the dye type for the input stack */
        public static DyeType getDye(ItemStack in) {

            if (in != null && OreDictHelper.isDye(in, false) &&  in.getItemDamage() >= 0 && in.getItemDamage() < VALID_DYES.length)
                return VALID_DYES[in.getItemDamage()];

            return INVALID;
        }

        /* Get the dye type from the ore dictionary name */
        public static DyeType getDyeFromOreDict(String dyeName) {

            for (DyeType r : VALID_DYES)
                if (r.getOreDictName().equals(dyeName))
                    return r;

            return INVALID;
        }

        /* Get the dye type from the damage value */
        public static DyeType getDye(int id) {

            if (id < 0 || id >= VALID_DYES.length)
                return INVALID;

            return VALID_DYES[id];
        }

        public static ItemStack getStack(String oreName) {

            for (DyeType d : VALID_DYES) {
                if (d.getOreDictName().equals(oreName)) {
                    return d.getStack();
                }
            }

            return null;
        }
    }

}
