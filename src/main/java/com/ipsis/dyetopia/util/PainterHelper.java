package com.ipsis.dyetopia.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

public class PainterHelper {

    private Object recipe;
    private RecipeType recipeType;
    private boolean validRecipe;

    private PainterHelper() {

        this.singleDye = false;
        this.recipeDye = null;
        this.recipeDyeCount = 0;

        this.singleItem = false;
        this.recipeItem = null;
        this.recipeItemCount = 0;
    }

    public PainterHelper(ShapelessRecipes recipe) {

        super();
        this.recipe = recipe;
        this.recipeType = RecipeType.SHAPELESS_VANILLA;
        this.validRecipe = false;
    }

    public boolean getValidRecipe() {
        return this.validRecipe;
    }

    public void process() {

        boolean valid = false;
        if (this.recipeType == RecipeType.SHAPELESS_VANILLA)
            valid = processShapelessVanilla();

        if (valid) {
            LogHelper.info("process is a valid recipe");
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
     * Look for a single item type in the recipe
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

    /***************************************
     *
     * Shapeless Vanilla
     */
    private boolean processShapelessVanilla() {

        if (!passShapelessVanilla(1))
            return false;

        if (!passShapelessVanilla(2))
            return false;

        return true;
    }

    private boolean passShapelessVanilla(int pass) {

        boolean valid = true;

        for (Object object : ((ShapelessRecipes)recipe).recipeItems) {

            if (!(object instanceof ItemStack))
                continue;

            ItemStack itemStack = ((ItemStack)object).copy();
            if (itemStack.stackSize > 1)
                itemStack.stackSize = 1;

            if (pass == 1 && !pass1(itemStack)) {
                valid = false;
                break;
            } else if (pass == 2 && !pass2(itemStack)) {
                valid = false;
                break;
            }
        }

        return valid;
    }

    @Override
    public String toString() {

        return recipeType + " " + singleDye + " " + recipeDye + " " + recipeDyeCount + " " +
                singleItem + " " + recipeItem + " " + recipeItemCount;
    }

    private static enum RecipeType {

        SHAPELESS_VANILLA,
        SHAPED_VANILLA,
        SHAPELESS_OREDICT,
        SHAPED_OREDICT
    }

}
