package com.ipsis.dyetopia.util;

import cofh.lib.inventory.ComparableItemStackSafe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class PainterValidator {

    private boolean singleDyeType;
    private boolean singleItemType;
    private boolean singleItemOreType;

    private int dyeCount;
    private int itemCount;

    private ArrayList<ComparableItemStackSafe> recipeOreItemList;

    private boolean validRecipe;

    private PainterValidator() {
        this.singleDyeType = false;
        this.singleItemType = false;
        this.singleItemOreType = false;
        this.validRecipe = false;
    };


    public boolean isValid() {

        return this.validRecipe;
    }

    public ItemStack getRecipeDye() {

        return recipeDye.copy();
    }

    public ItemStack getRecipeItem() {

        return recipeItem.copy();
    }

    public ArrayList<ItemStack> getRecipeOreItemList() {

        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        for (ComparableItemStackSafe s : recipeOreItemList)
            items.add(s.toItemStack());

        return items;
    }

    public boolean isSingleItem() {

        return this.singleItemType;
    }

    public boolean isSingleOreItemList() {

        return this.singleItemOreType;
    }

    public int getPureAmount() {

        if (!isValid())
            return 0;

        return (100 * dyeCount) / itemCount;

    }

    private boolean handleItemStack(ItemStack itemStack) {

        if (OreDictHelper.isDye(itemStack)) {

            if (recipeDye == null) {
                recipeDye = itemStack;
                recipeDye.stackSize = 1;
                dyeCount = 1;
                singleDyeType = true;
            } else {
                if (recipeDye.isItemEqual(itemStack)) {
                    dyeCount++;
                } else {
                    singleDyeType = false;
                    return false;
                }
            }
        } else {

            if (recipeOreItemList != null) {
                return false;
            } else {

                if (recipeItem == null) {
                    recipeItem = itemStack;
                    recipeItem.stackSize = 1;
                    singleItemType = true;
                    itemCount = 1;
                } else {
                    if (recipeItem.isItemEqual(itemStack)) {
                        itemCount++;
                    } else {
                        singleItemType = false;
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean handleOreList(ArrayList oreList) {

        if (recipeItem != null)
            return false;

        if (recipeOreItemList == null) {
            recipeOreItemList = new ArrayList(oreList.size());
            for (Object object : oreList) {
                if (object instanceof ItemStack)
                    recipeOreItemList.add(new ComparableItemStackSafe(((ItemStack)object).copy()));
            }
            singleItemOreType = true;
        } else {

            /* Is is the same list of itemstacks */
            int count = 0;
            for (Object object : oreList) {
                if (object instanceof ItemStack) {
                    if (!recipeOreItemList.contains(new ComparableItemStackSafe((ItemStack)object)))
                        break;
                    else
                        count++;
                }
            }

            if (count == recipeOreItemList.size()) {
                itemCount++;
            } else {
                singleItemOreType = false;
                return false;
            }
        }

        return true;
    }

    private void validate() {

        validRecipe = false;

        if (singleDyeType && recipeDye != null) {

            LogHelper.info("Validate:" + singleDyeType + " " + recipeDye + " " +
                    singleItemType + " " + recipeItem + " " + itemCount + " " +
                    singleItemOreType + " " + recipeOreItemList);

            if (singleItem) {
                if (recipeItem != null && recipeItemCount > 0)
                    validRecipe = true;
            } else if (singleItemOreType) {
                if (recipeOreItemList != null && recipeOreItemList.size() > 0 && itemCount > 0)
                    validRecipe = true;
            }
        }
    }

    /**
     * Look for a single dye type in the recipe
     */
    private boolean singleDye;
    private ItemStack recipeDye;
    private int recipeDyeCount;

    private boolean pass1(ItemStack itemStack) {

        if (itemStack == null)
            return true;

        if (!OreDictHelper.isDye(itemStack))
            return true;

        if (recipeDye == null) {

            recipeDye = itemStack.copy();
            recipeDye.stackSize = 1;
            singleDye = true;
            recipeDyeCount = 1;

        } else {

            if (recipeDye.isItemEqual(itemStack)) {
                recipeDyeCount++;
            } else {
                /* Recipe has more than one dye */
                singleDye = false;
                return false;
            }
        }

        return true;
    }

    /**
     * Look for a single item
     */

    private boolean singleItem;
    private ItemStack recipeItem;
    private int recipeItemCount;

    private boolean pass2(ItemStack itemStack) {

        if (itemStack == null)
            return true;

        if (OreDictHelper.isDye(itemStack))
            return true;

        if (recipeItem == null) {

            recipeItem = itemStack.copy();
            recipeItem.stackSize = 1;
            singleItem = true;
            recipeItemCount = 1;

        } else {

            if (recipeItem.isItemEqual(itemStack)) {
                recipeItemCount++;
            } else {
                /* Recipe has more than one item */
                singleItem = false;
                return false;
            }
        }

        return true;
    }

    public PainterValidator(ShapelessRecipes recipe) {

        super();

        boolean invalidRecipe = false;

        /* Pass 1 find the dyes */
        for (Object object : recipe.recipeItems) {

            if (!(object instanceof ItemStack))
                continue;

            ItemStack itemStack = ((ItemStack)object).copy();
            if (itemStack.stackSize > 1)
                itemStack.stackSize = 1;

            if (!pass1(itemStack)) {
                invalidRecipe = true;
                break;
            }
        }

        /* Pass 2 find the items */
        if (!invalidRecipe && singleDye) {

            for (Object object : recipe.recipeItems) {

                if (!(object instanceof ItemStack))
                    continue;

                ItemStack itemStack = ((ItemStack)object).copy();
                if (itemStack.stackSize > 1)
                    itemStack.stackSize = 1;

                if (!pass2(itemStack)) {
                    invalidRecipe = true;
                    break;
                }
            }

            validate();

            /* Pass 3 find the ore items */
            if (!invalidRecipe && singleItem) {

            }
        }
    }

    public PainterValidator(ShapedRecipes recipe) {

        super();

        for (int i = 0; i < recipe.recipeItems.length; i++)
        {
            if (!(recipe.recipeItems[i] instanceof ItemStack))
                continue;

            ItemStack itemStack = recipe.recipeItems[i].copy();
            if (itemStack.stackSize > 1)
                itemStack.stackSize = 1;

            if (!handleItemStack(itemStack))
                break;
        }

        validate();
    }

    public PainterValidator(ShapelessOreRecipe recipe) {

        super();

        /* First pass look for the dye */
        for (Object object : recipe.getInput()) {

            if (object instanceof ItemStack) {

                ItemStack itemStack = ((ItemStack) object).copy();
                if (itemStack.stackSize > 1)
                    itemStack.stackSize = 1;

                if (!handleItemStack(itemStack))
                    break;
            }
        }

        if (singleDyeType && recipeDye != null) {

            LogHelper.info("ShaplessOreRecipe: " + recipeDye);
            for (Object object : recipe.getInput()) {

                if (object instanceof ArrayList) {

                    ArrayList l = (ArrayList)object;
                    if (l.size() == 0 || !handleOreList(l))
                        break;
                }
            }
        }

        validate();
    }

    public PainterValidator(ShapedOreRecipe recipe) {

        super();

        /* First pass look for the dye */
        for (int i = 0; i < recipe.getInput().length; i++) {

            if (recipe.getInput()[i] instanceof ItemStack) {

                ItemStack itemStack = ((ItemStack) recipe.getInput()[i]).copy();
                if (itemStack.stackSize > 1)
                    itemStack.stackSize = 1;

                if (!handleItemStack(itemStack))
                    break;
            }
        }

        if (singleDyeType && recipeDye != null) {

            LogHelper.info("ShapedOreRecipe: " + recipeDye);
            for (int i = 0; i < recipe.getInput().length; i++) {

                if (recipe.getInput()[i] instanceof ArrayList) {

                    ArrayList l = (ArrayList)recipe.getInput()[i];
                    if (l.size() == 0 || !handleOreList(l))
                        break;
                }
            }
        }

        validate();
    }


}
