package com.ipsis.dyetopia.util;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictHelper {

    public static boolean isDye(ItemStack stack) {

        int[] ids = OreDictionary.getOreIDs(stack);
        if (ids.length != 0) {
            for (int id : ids) {
                /* dye is the generic dye ore name */
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("dye") && oreName.startsWith("dye"))
                    return true;
            }
        }

        return false;
    }
}
