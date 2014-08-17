package com.ipsis.dyetopia.manager;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.util.helpers.ItemHelper;
import com.ipsis.dyetopia.util.LogHelper;
import com.ipsis.dyetopia.util.OreDictHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

public class DyeLiquidManager {

    private static final DyeLiquidManager instance = new DyeLiquidManager();

    /* This allows lookup of the dye recipe for any oredict dye */
    private HashMap<Integer, DyeRecipe> map;

    private DyeLiquidManager() {
        map = new HashMap<Integer, DyeRecipe>();
    }

    public static final DyeLiquidManager getInstance() { return instance; }

    private void addRecipe(String name, int oreId, ItemStack stack, int idx) {
        map.put(oreId, new DyeRecipe(stack, DYE_PROPS[idx][0], DYE_PROPS[idx][1], DYE_PROPS[idx][2], DYE_PROPS[idx][3]));
        LogHelper.info(String.format("DyeLiquidManager: dye%s:%d->%d/%d/%d/%d",
                        name, oreId, DYE_PROPS[idx][0], DYE_PROPS[idx][1], DYE_PROPS[idx][2], DYE_PROPS[idx][3]));
    }

    public void initialize() {

        for (int i = 0; i < 16; i++) {
            if (DYE_PROPS[i][0] + DYE_PROPS[i][1] + DYE_PROPS[i][2] + DYE_PROPS[i][3] != LCM)
                LogHelper.error("DyeLiquidManager: init - dye proportions broken");
        }

        String[] dyes = {
                "Black", "Red", "Green", "Brown",
                "Blue", "Purple", "Cyan", "LightGray",
                "Gray", "Pink", "Lime", "Yellow",
                "LightBlue", "Magenta", "Orange", "White"
        };

        for (int i = 0; i < dyes.length; i++)
            addRecipe(dyes[i], OreDictionary.getOreID("dye" + dyes[i]), new ItemStack(Items.dye, 1, i), i);
    }

    public DyeRecipe getRecipe(ItemStack s) {

        if (!OreDictHelper.isDye(s))
            return null;

        /* This needs to find the correct oredict dye name to lookup */
        int[] ids = OreDictionary.getOreIDs(s);
        for (int id : ids) {
            if (map.containsKey(id))
                return map.get(id);
        }

        return null;
    }

    /*************
     * Ratio map
     */

    /**
     * The proportion of RYBW that is produced for each dye type
     * Stored in 72ths (LCM of 2,3,4,6,8,9!)
     */
    private static final int LCM = 72;
    public static final int DYE_BASE_AMOUNT = LCM;
    private static final int[][] DYE_PROPS = new int[][] {
		/*     Red,     Yellow,      Blue,     White */

            {     LCM/3,     LCM/3,     LCM/3,         0 },		/* Black */
            {       LCM,	     0,         0,         0 },		/* Red */
            {         0,      LCM/2,    LCM/2,         0 },		/* Green */
            { 3*(LCM/4),      LCM/8,    LCM/8,         0 },		/* Brown */
            {         0,          0,      LCM,         0 },		/* Blue */
            {     LCM/2,          0,    LCM/2,         0 },		/* Purple */
            {         0,          0,    LCM/4, 3*(LCM/4) },		/* Cyan */
            {     LCM/9,      LCM/9,    LCM/9, 2*(LCM/3) },		/* Light Gray */
            {     LCM/6,      LCM/6,    LCM/6,     LCM/2 },		/* Gray */
            {     LCM/2,          0,        0,     LCM/2 },		/* Pink */
            {         0,      LCM/4,    LCM/4,     LCM/2 },		/* Lime */
            {         0,        LCM,        0,         0 },		/* Yellow */
            {         0,          0,    LCM/2,     LCM/2 },		/* Light Blue */
            {     LCM/2,          0,    LCM/4,     LCM/4 },		/* Magenta */
            {     LCM/2,      LCM/2,        0,         0 },		/* Orange */
            {         0,          0,        0,       LCM }		/* White */
    };

}
