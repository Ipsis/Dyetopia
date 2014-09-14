package com.ipsis.dyetopia.util;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class DyeHelper {

    public static enum DyeType {

        BLACK(0, "dyeBlack"),
        RED(1, "dyeRed"),
        GREEN(2, "dyeGreen"),
        BROWN(3, "dyeBrown"),
        BLUE(4, "deyeBlue"),
        PURPLE(5, "dyePurple"),
        CYAN(6, "dyeCyan"),
        LIGHTGRAY(7, "dyeLightGray"),
        GRAY(8, "dyeGray"),
        PINK(9, "dyePink"),
        LIME(10, "dyeLime"),
        YELLOW(11, "dyeYellow"),
        LIGHTBLUE(12, "dyeLightBlue"),
        MAGENTA(13, "dyeMagenta"),
        ORANGE(14, "dyeOrange"),
        WHITE(15, "dyeWhite");

        public static DyeType[] VALID_DYES = {
            BLACK, RED, GREEN, BROWN, BLUE, PURPLE, CYAN, LIGHTGRAY, GRAY,
            PINK, LIME, YELLOW, LIGHTBLUE, MAGENTA, ORANGE, WHITE };

        private int dmg;
        private String oreDict;
        private DyeType(int dmg, String oreDict) { this.dmg = dmg; this.oreDict = oreDict; }

        public int getDmg() { return this.dmg; }
        public String getOreDict() { return this.oreDict; }

        public static DyeType getDye(ItemStack in) {

            if (in != null && OreDictHelper.isDye(in) &&  in.getItemDamage() >= 0 && in.getItemDamage() < VALID_DYES.length)
                return VALID_DYES[in.getItemDamage()];

            return WHITE;
        }

        public static DyeType getDyeType(int id) {

            if (id < 0 || id >= VALID_DYES.length)
                return WHITE;

            return VALID_DYES[id];
        }

        public ItemStack getStack() {

            return new ItemStack(Items.dye, 1, this.dmg);
        }

        public IIcon getIcon() {

            return Items.dye.getIconFromDamage(this.dmg);
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
