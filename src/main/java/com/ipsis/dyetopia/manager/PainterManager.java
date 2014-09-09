
package com.ipsis.dyetopia.manager;


import cofh.lib.inventory.ComparableItemStackSafe;
import com.ipsis.dyetopia.util.*;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.*;

/**
 * Recipe parsing taken from Pahimar's EE3 RecipeHelper.java
 *
 */

public class PainterManager {

    private static HashMap<ComparableItemStackSafe, PainterRecipe[]>  recipeMap = new HashMap();

    public static void initialise() {

        List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
        for (IRecipe irecipe : allrecipes) {

            if (irecipe.getRecipeOutput() == null)
                continue;

            /* We want a dye + something */
            if (irecipe.getRecipeSize() <= 1)
                continue;

            /* No recipes that make dyes or food */
            if (OreDictHelper.isDye(irecipe.getRecipeOutput()) || irecipe.getRecipeOutput().getItem() instanceof ItemFood)
                continue;

            if (irecipe instanceof ShapelessRecipes || irecipe instanceof ShapedRecipes)
                handleVanilla(irecipe);
            /* else if (irecipe instanceof ShapedOreRecipe || irecipe instanceof ShapelessOreRecipe)
                handleOreDict(irecipe); */
        }

        debugDumpMap();
        OriginHelper.debugDumpMap();
    }

    public static void debugDumpMap() {

        Iterator iter = recipeMap.entrySet().iterator();
        while (iter.hasNext()) {

            Map.Entry pairs = (Map.Entry)iter.next();
            ComparableItemStackSafe output = (ComparableItemStackSafe)pairs.getKey();
            PainterRecipe[] rList = (PainterRecipe[])pairs.getValue();
            LogHelper.info("[PainterManager]: " + output.item.getUnlocalizedName());
            if (rList != null) {
                int x = 0;
                for (PainterRecipe r : rList) {
                    if (r != null)
                        LogHelper.info("[PainterManager]: " + x + "->" + r);
                    x++;
                }
            }
        }
    }

    private static void handleShapelessRecipe(ShapelessRecipes recipe) {

        PainterValidator v = new PainterValidator(recipe);
        if (!v.isValid())
            return;

        ItemStack input = v.getRecipeItem();
        input.stackSize = 1;

        ItemStack output = recipe.getRecipeOutput().copy();
        addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
        OriginHelper.addRecipe(output.copy(), input);
    }

    private static void handleShapedRecipe(ShapedRecipes recipe) {

        PainterValidator v = new PainterValidator(recipe);
        if (!v.isValid())
            return;

        ItemStack input = v.getRecipeItem();
        input.stackSize = 1;

        ItemStack output = recipe.getRecipeOutput().copy();
        addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
        OriginHelper.addRecipe(output.copy(), input);
    }

    private static void handleVanilla(IRecipe recipe) {

        PainterValidator v;

        /*if (recipe instanceof ShapedRecipes) {
            v = new PainterValidator((ShapedRecipes) recipe);
        } else */if (recipe instanceof ShapelessRecipes) {
            v = new PainterValidator((ShapelessRecipes) recipe);

            PainterHelper helper = new PainterHelper((ShapelessRecipes)recipe);
            helper.process();
            LogHelper.info("handleVanilla: " + helper);

        } else {
            return;
        }
        /*

        if (!v.isValid())
            return;

        ItemStack input = v.getRecipeItem();
        input.stackSize = 1;

        ItemStack output = recipe.getRecipeOutput().copy();
        addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
        OriginHelper.addRecipe(output.copy(), input); */
    }

    private static void handleOreDict(IRecipe recipe) {

        PainterValidator v;

        if (recipe instanceof ShapelessOreRecipe)
            v = new PainterValidator((ShapelessOreRecipe)recipe);
        else if (recipe instanceof ShapedOreRecipe)
            v = new PainterValidator((ShapedOreRecipe)recipe);
        else
            return;

        if (!v.isValid())
            return;

        ItemStack output = recipe.getRecipeOutput().copy();

        if (v.isSingleItem()) {
            ItemStack input = v.getRecipeItem();
            input.stackSize = 1;
            addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
            OriginHelper.addRecipe(output.copy(), input);
        } else if (v.isSingleOreItemList()) {

            List<ItemStack> inputs = v.getRecipeOreItemList();
            for (ItemStack input : inputs) {
                input.stackSize = 1;
                addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
                OriginHelper.addRecipe(output.copy(), input);
            }
        }
    }

    private static void handleShapelessOreRecipe(ShapelessOreRecipe recipe) {

        PainterValidator v = new PainterValidator(recipe);
        if (!v.isValid())
            return;

        ItemStack output = recipe.getRecipeOutput().copy();

        if (v.isSingleItem()) {
            ItemStack input = v.getRecipeItem();
            input.stackSize = 1;
            addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
            OriginHelper.addRecipe(output.copy(), input);
        } else if (v.isSingleOreItemList()) {

            List<ItemStack> inputs = v.getRecipeOreItemList();
            for (ItemStack input : inputs) {
                input.stackSize = 1;
                addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
                OriginHelper.addRecipe(output.copy(), input);
            }
        }
    }


    private static void handleShapedOreRecipe(ShapedOreRecipe recipe) {

        PainterValidator v = new PainterValidator(recipe);
        if (!v.isValid())
            return;

        ItemStack output = recipe.getRecipeOutput().copy();

        if (v.isSingleItem()) {
            ItemStack input = v.getRecipeItem();
            input.stackSize = 1;
            addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
            OriginHelper.addRecipe(output.copy(), input);
        } else if (v.isSingleOreItemList()) {

            List<ItemStack> inputs = v.getRecipeOreItemList();
            for (ItemStack input : inputs) {
                input.stackSize = 1;
                addRecipe(input, DyeHelper.DyeType.getDye(v.getRecipeDye()), output, v.getPureAmount());
                OriginHelper.addRecipe(output.copy(), input);
            }
        }

    }






    public static void addRecipe(ItemStack in, DyeHelper.DyeType dye, ItemStack out, int pureAmount) {

        if (in == null || out == null || pureAmount < 0)
            return;

        PainterRecipe[] rList;
        if (!recipeMap.containsKey(new ComparableItemStackSafe(in))) {
            rList = new PainterRecipe[DyeHelper.DyeType.VALID_DYES.length];
            recipeMap.put(new ComparableItemStackSafe(in), rList);
        } else {
            rList = recipeMap.get(new ComparableItemStackSafe(in));
        }

        rList[dye.ordinal()] = new PainterRecipe(in, dye, out, pureAmount);
    }

    public static boolean hasRecipe(ItemStack in, DyeHelper.DyeType dye) {

        if (in == null)
            return false;

        if (!recipeMap.containsKey(new ComparableItemStackSafe(in)))
            return false;

        PainterRecipe[] rList = recipeMap.get(new ComparableItemStackSafe(in));
        if (rList == null || rList[dye.ordinal()] == null)
            return false;

        return true;
    }

    public static PainterRecipe getRecipe(ItemStack in, DyeHelper.DyeType dye) {

        if (in == null)
            return null;

        if (!recipeMap.containsKey(new ComparableItemStackSafe(in)))
            return null;

        PainterRecipe[] rList = recipeMap.get(new ComparableItemStackSafe(in));
        if (rList == null || rList[dye.ordinal()] == null)
            return null;

        return rList[dye.ordinal()];
    }

    public static class PainterRecipe {

        private ItemStack in;
        private DyeHelper.DyeType dye;
        private ItemStack out;
        private int pureAmount;

        private PainterRecipe() { }

        public PainterRecipe(ItemStack in, DyeHelper.DyeType dye, ItemStack out, int pureAmount) {

            this.in = in;
            this.dye = dye;
            this.out = out;
            this.pureAmount = pureAmount;
        }

        public int getPureAmount() { return this.pureAmount; }
        public ItemStack getInput() { return this.in.copy(); }
        public DyeHelper.DyeType getDye() { return this.dye; }
        public ItemStack getOutput() { return this.out.copy(); }

        @Override
        public String toString() {

            return in + " + " + dye + " + " + pureAmount + "->" + out;
        }
    }

 }
