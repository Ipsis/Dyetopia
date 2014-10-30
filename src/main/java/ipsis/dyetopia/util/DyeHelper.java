package ipsis.dyetopia.util;

import cofh.lib.util.helpers.ColorHelper;
import ipsis.dyetopia.item.DYTItems;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class DyeHelper {

    public static enum DyeType {

        BLACK(0, "dyeBlack", ColorHelper.DYE_BLACK),
        RED(1, "dyeRed", ColorHelper.DYE_RED),
        GREEN(2, "dyeGreen", ColorHelper.DYE_GREEN),
        BROWN(3, "dyeBrown", ColorHelper.DYE_BROWN),
        BLUE(4, "deyeBlue", ColorHelper.DYE_BLUE),
        PURPLE(5, "dyePurple", ColorHelper.DYE_PURPLE),
        CYAN(6, "dyeCyan", ColorHelper.DYE_CYAN),
        LIGHTGRAY(7, "dyeLightGray", ColorHelper.DYE_LIGHT_GRAY),
        GRAY(8, "dyeGray", ColorHelper.DYE_GRAY),
        PINK(9, "dyePink", ColorHelper.DYE_PINK),
        LIME(10, "dyeLime", ColorHelper.DYE_LIME),
        YELLOW(11, "dyeYellow", ColorHelper.DYE_YELLOW),
        LIGHTBLUE(12, "dyeLightBlue", ColorHelper.DYE_LIGHT_BLUE),
        MAGENTA(13, "dyeMagenta", ColorHelper.DYE_MAGENTA),
        ORANGE(14, "dyeOrange", ColorHelper.DYE_ORANGE),
        WHITE(15, "dyeWhite", ColorHelper.DYE_WHITE);

        public static DyeType[] VALID_DYES = {
            BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHTGRAY, GRAY,
            PINK, LIME, YELLOW, LIGHTBLUE, MAGENTA, ORANGE, WHITE };

        private int dmg;
        private String oreDict;
        private int colorCode;
        private DyeType(int dmg, String oreDict, int code) { this.dmg = dmg; this.oreDict = oreDict; this.colorCode = code;}

        public int getDmg() { return this.dmg; }
        public String getOreDict() { return this.oreDict; }
        public int getColorCode() { return this.colorCode; }

        public static DyeType getDye(ItemStack in) {

            if (in != null && OreDictHelper.isDye(in) &&  in.getItemDamage() >= 0 && in.getItemDamage() < VALID_DYES.length)
                return VALID_DYES[in.getItemDamage()];

            return WHITE;
        }

        public DyeType getNext() {

            int ord = this.ordinal();
            ord++;
            if (ord >= VALID_DYES.length)
                ord = 0;

            return VALID_DYES[ord];
        }

        public static DyeType getDyeType(int id) {

            if (id < 0 || id >= VALID_DYES.length)
                return WHITE;

            return VALID_DYES[id];
        }

        public ItemStack getStack() {

            return new ItemStack(DYTItems.itemDyeChunk, 1, this.dmg);
        }

        public IIcon getIcon() {

            return DYTItems.itemDyeChunk.getIconFromDamage(this.dmg);
        }

        public static ItemStack getStack(String oreName) {

            for (DyeType d : VALID_DYES) {
                if (d.getOreDict().equals(oreName)) {
                    return d.getStack();
                }
            }

            return null;
        }
    }

}
