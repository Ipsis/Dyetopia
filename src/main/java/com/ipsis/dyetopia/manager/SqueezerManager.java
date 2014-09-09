package com.ipsis.dyetopia.manager;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.inventory.ComparableItemStackSafe;
import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.util.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.fluids.FluidStack;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SqueezerManager {

    /**
     *  Items that can be squeezed include dye items themselves
     *  ComparableItemStack uses the oreDictionary however only proocesses the first oreId returned
     *  Dye items map to ore "dye" and "dyeX"
     *  Therefore all dyes will match each other as they are all oredId "dye"
     *  ComparableItemStackSafe does not use the oreDictionary for the dyes
     *
     */
    private static HashMap<ComparableItemStackSafe, SqueezerRecipe> recipes = new HashMap<ComparableItemStackSafe, SqueezerRecipe>();

    private static final int RECIPE_ENERGY = 120;

    public static void initialise() {

        /* Dye Items - so you can squeeze the dyes themselves */
        for (int i = 0; i < 16; i++)
            addRecipe(new ItemStack(Items.dye, 1, i), new ItemStack(Items.dye, 1, i));

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

        /* Smelting Recipes */
        Map allsmelting = FurnaceRecipes.smelting().getSmeltingList();
        Iterator i = allsmelting.entrySet().iterator();
        while (i.hasNext()) {
            Map.Entry pairs = (Map.Entry) i.next();

            /**
             * If the source item is actually a block eg. cactus
             * then the damage value may not be correct.
             * We therefore create a new stack using the item for the block.
             * We may be using the damage value as part of the hashmap, so this
             * makes sure a lookup will work.
             *
             * Not very sure about this but ...
             *
             * ComparableItemStack will check the ItemDamage.
             * Vanilla smelting recipes from blocks are creating with damage of 32768
             *
             * eg. cactus itemdamage = 32768
             * whe placed into the squeezer it is 0
             */


            ItemStack in = ((ItemStack)pairs.getKey());
            ItemStack cleanItem = new ItemStack(in.getItem());
            ItemStack out = ((ItemStack)pairs.getValue()).copy();

            addRecipe(cleanItem, out);
        }
    }

    private static void addRecipe(ItemStack in, ItemStack out) {

        DyeRecipe r = DyeLiquidManager.getInstance().getRecipe(out);
        if (r != null) {
            recipes.put(new ComparableItemStackSafe(in), new SqueezerRecipe(in.copy(), r.getRedAmount(), r.getYellowAmount(), r.getBlueAmount(), r.getWhiteAmount(), RECIPE_ENERGY));
            LogHelper.info("[SqueezerManager]: " + in.getUnlocalizedName() + "->" + out.getUnlocalizedName());
        }
    }

    public static SqueezerRecipe getRecipe(ItemStack in) {

        if (in == null)
            return null;

        SqueezerRecipe r = recipes.get(new ComparableItemStackSafe(in));
        return r;
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

        public FluidStack getRedFluidStack(){ return this.red.copy(); }
        public FluidStack getYellowFluidStack(){ return this.yellow.copy(); }
        public FluidStack getBlueFluidStack(){ return this.blue.copy(); }
        public FluidStack getWhiteFluidStack(){ return this.white.copy(); }

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
