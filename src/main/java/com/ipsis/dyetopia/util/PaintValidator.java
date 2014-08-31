package com.ipsis.dyetopia.util;

import cofh.lib.inventory.ComparableItemStackSafe;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Verifies that a recipe is valid for painting
 *
 * only 2 item types
 * - 1 dye item
 * - 1 or more items of a single type
 */
public class PaintValidator {

    private int itemTypeCount;
    private int inputItemCount;
    private ItemStack dye;
    private ItemStack input;

    public void addItemStack(ItemStack in) {

        if (OreDictHelper.isDye(in)) {

            if (dye == null) {
                itemTypeCount++;
                dye = in.copy();
            } else {
                itemTypeCount++;
            }
        } else {

            if (input == null) {
                itemTypeCount++;
                input = in.copy();
            } else {

                if (input.isItemEqual(in)) {
                    inputItemCount++;
                } else {
                    itemTypeCount++;
                }
            }
        }
    }

    public boolean isValid() {

        if (dye == null || input == null)
            return false;

        if (itemTypeCount == 2 && inputItemCount > 0)
            return true;

        return false;
    }


    public int getItemCount() {

        return inputItemCount;
    }

    public ItemStack getInputItem() {

        return input.copy();
    }

    public ItemStack getInputDye() {

        return dye.copy();
    }
}
