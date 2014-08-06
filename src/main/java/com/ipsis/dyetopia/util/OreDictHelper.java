package com.ipsis.dyetopia.util;

import cofh.util.ItemHelper;
import net.minecraft.item.ItemStack;

public class OreDictHelper {

    public static boolean isDye(ItemStack stack) {

        return ItemHelper.getOreName(stack).startsWith("dye");
    }
}
