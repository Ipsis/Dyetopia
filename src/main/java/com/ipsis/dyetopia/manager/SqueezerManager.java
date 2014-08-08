package com.ipsis.dyetopia.manager;

import cofh.lib.inventory.ComparableItemStack;
import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.util.LogHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.List;

public class SqueezerManager {

    private static HashMap<ComparableItemStack, SqueezerRecipe> recipes = new HashMap<ComparableItemStack, SqueezerRecipe>();

    private static final int RECIPE_ENERGY = 120;

    public static void initialise() {

   		/* Shapeless recipes */
        List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
        for (IRecipe irecipe : allrecipes) {
            if (irecipe instanceof ShapelessRecipes) {

				/*
				 * Only add shapeless recipes where single item creates a dye
				 * (can be 1 or more of the same dye)
				 */
                ShapelessRecipes r = (ShapelessRecipes) irecipe;
                if (r.getRecipeSize() == 1) {
                    ItemStack out = irecipe.getRecipeOutput();
                    ItemStack in = (ItemStack) (r.recipeItems.get(0));
                    addRecipe(in, out);
                }
            }
        }
    }

    private static void addRecipe(ItemStack in, ItemStack out) {

        DyeRecipe r = DyeLiquidManager.getInstance().getRecipe(out);
        if (r != null) {
            recipes.put(new ComparableItemStack(in), new SqueezerRecipe(in, r.getRedAmount(), r.getYellowAmount(), r.getBlueAmount(), r.getYellowAmount(), RECIPE_ENERGY));
            LogHelper.info("addSqueezerRecipe: " + in.getUnlocalizedName() + " -> " + r);
        }
    }

    public static SqueezerRecipe getRecipe(ItemStack in) {

        return recipes.get(new ComparableItemStack(in));
    }

    public static class SqueezerRecipe implements IFactoryRecipe {

        private ItemStack input;
        private FluidStack red;
        private FluidStack yellow;
        private FluidStack blue;
        private FluidStack white;
        private int energy;

        private SqueezerRecipe() { }

        public SqueezerRecipe(ItemStack input, int red, int yellow, int blue, int white, int energy) {

            this.input = input;
            this.red = new FluidStack(DYTFluids.fluidDyeRed, red);
            this.yellow = new FluidStack(DYTFluids.fluidDyeYellow, yellow);
            this.blue = new FluidStack(DYTFluids.fluidDyeBlue, blue);
            this.white = new FluidStack(DYTFluids.fluidDyeWhite, white);
            this.energy = energy;
        }

        public int getRedAmount() { return this.red.amount; }
        public int getYellowAmount() { return this.yellow.amount; }
        public int getBlueAmount() { return this.blue.amount; }
        public int getWhiteAmount() { return this.white.amount; }

        public FluidStack getRedFluidStack(){ return this.red; }
        public FluidStack getYellowFluidStack(){ return this.yellow; }
        public FluidStack getBlueFluidStack(){ return this.blue; }
        public FluidStack getWhiteFluidStack(){ return this.white; }

        @Override
        public String toString() {
            return input.getUnlocalizedName() + "-> red: " + getRedAmount() + " yellow:" + getYellowAmount() + " blue:" + getBlueAmount() + " white:" + getWhiteAmount();
        }

        /**
         * IFactoryRecipe
         */
        @Override
        public int getEnergy() {
            return this.energy;
        }
    }
}
