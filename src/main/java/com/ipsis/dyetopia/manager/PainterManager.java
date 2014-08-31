
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
 */

public class PainterManager {

    private static HashMap<ComparableItemStackSafe, PainterRecipe[]>  recipeMap = new HashMap();

    public static void initialise() {

        List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
        for (IRecipe irecipe : allrecipes) {

            if (irecipe instanceof ShapelessRecipes)
                handleShapelessRecipe((ShapelessRecipes)irecipe);
            else if (irecipe instanceof ShapedRecipes)
                handleShapedRecipe((ShapedRecipes)irecipe);
            else if (irecipe instanceof ShapedOreRecipe)
                handleShapedOreRecipe((ShapedOreRecipe)irecipe);
            else if (irecipe instanceof ShapelessOreRecipe)
                handleShapelessOreRecipe((ShapelessOreRecipe)irecipe);
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
            LogHelper.info("[PainterManager] recipeMap: " + output.item.getUnlocalizedName());
            if (rList != null) {
                int x = 0;
                for (PainterRecipe r : rList) {
                    LogHelper.info(x + ":" + r);
                    x++;
                }
            }
        }
    }

    private static void handleShapelessRecipe(ShapelessRecipes recipe) {

         /* No recipes that make dyes or food */
        if (OreDictHelper.isDye(recipe.getRecipeOutput()) || recipe.getRecipeOutput().getItem() instanceof ItemFood)
            return;

        PaintValidator validator = new PaintValidator();

        for (Object object : recipe.recipeItems)
        {
            if (object instanceof ItemStack)
            {
                ItemStack itemStack = ((ItemStack) object).copy();

                if (itemStack.stackSize > 1)
                    itemStack.stackSize = 1;

                validator.addItemStack(itemStack);
            }
        }

        if (validator.isValid())
            tryAdd(recipe.getRecipeOutput(), validator);
    }

    private static void handleShapedRecipe(ShapedRecipes recipe) {

         /* No recipes that make dyes or food */
        if (OreDictHelper.isDye(recipe.getRecipeOutput()) || recipe.getRecipeOutput().getItem() instanceof ItemFood)
            return;

        PaintValidator validator = new PaintValidator();

        for (int i = 0; i < recipe.recipeItems.length; i++)
        {
            if (recipe.recipeItems[i] instanceof ItemStack)
            {
                ItemStack itemStack = recipe.recipeItems[i].copy();

                if (itemStack.stackSize > 1)
                    itemStack.stackSize = 1;

                validator.addItemStack(itemStack);
            }
        }

        if (validator.isValid())
            tryAdd(recipe.getRecipeOutput(), validator);
    }


    private static void handleShapedOreRecipe(ShapedOreRecipe recipe) {

         /* No recipes that make dyes or food */
        if (OreDictHelper.isDye(recipe.getRecipeOutput()) || recipe.getRecipeOutput().getItem() instanceof ItemFood)
            return;

        PaintValidator validator = new PaintValidator();

        for (int i = 0; i < recipe.getInput().length; i++) {

            /* If the element is a list, then it is an OreStack */
            if (recipe.getInput()[i] instanceof ArrayList) {

                ArrayList l = (ArrayList)recipe.getInput()[i];

                /* Unique items first! */
                if (l.size() == 1) {
                    ItemStack itemStack = ((ItemStack)l.get(0)).copy();
                    if (itemStack.stackSize > 1)
                        itemStack.stackSize = 1;

                    validator.addItemStack(itemStack);
                }
            } else if (recipe.getInput()[i] instanceof ItemStack) {

                ItemStack itemStack = ((ItemStack) recipe.getInput()[i]).copy();

                if (itemStack.stackSize > 1)
                    itemStack.stackSize = 1;

                validator.addItemStack(itemStack);
            }
        }


        if (validator.isValid())
            tryAdd(recipe.getRecipeOutput(), validator);
    }

    private static void handleShapelessOreRecipe(ShapelessOreRecipe recipe) {

         /* No recipes that make dyes or food */
        if (OreDictHelper.isDye(recipe.getRecipeOutput()) || recipe.getRecipeOutput().getItem() instanceof ItemFood)
            return;

        PaintValidator validator = new PaintValidator();

        for (Object object : recipe.getInput()) {

            /* If the element is a list, then it is an OreStack */
            if (object instanceof ArrayList) {

                ArrayList l = (ArrayList)object;

                /* Unique items first! */
                if (l.size() == 1) {
                    ItemStack itemStack = ((ItemStack)l.get(0)).copy();
                    if (itemStack.stackSize > 1)
                        itemStack.stackSize = 1;

                    validator.addItemStack(itemStack);
                }
            } else if (object instanceof ItemStack) {

                ItemStack itemStack = ((ItemStack) object).copy();

                if (itemStack.stackSize > 1)
                    itemStack.stackSize = 1;

                validator.addItemStack(itemStack);
            }
        }

        if (validator.isValid())
            tryAdd(recipe.getRecipeOutput(), validator);
    }


    private static void tryAdd(ItemStack out, PaintValidator validator) {

        if (validator.getInputDye() != null && validator.getInputItem() != null && validator.getItemCount() > 0) {

            /* Add a paint and an origin recipe */
            addRecipe(validator.getInputItem(), DyeHelper.DyeType.getDye(validator.getInputDye()), out.copy(), validator.getItemCount());
            OriginHelper.addRecipe(out.copy(), validator.getInputItem());
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
