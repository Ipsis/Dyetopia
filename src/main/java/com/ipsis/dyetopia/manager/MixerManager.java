package com.ipsis.dyetopia.manager;

import com.ipsis.dyetopia.fluid.DYTFluids;
import net.minecraftforge.fluids.FluidStack;

public class MixerManager {

    private static final int RECIPE_ENERGY = 60;
    private static MixerRecipe recipe;

    public static void initialise() {
        recipe = new MixerRecipe(1000, 1000, 1000, 1000, 1000, RECIPE_ENERGY);
    }

    public static MixerRecipe getRecipe() {
        return recipe;
    }

    public static class MixerRecipe implements IFactoryRecipe {

        private FluidStack red;
        private FluidStack yellow;
        private FluidStack blue;
        private FluidStack white;
        private FluidStack pure;
        private int energy;

        private MixerRecipe() { }

        public MixerRecipe(int red, int yellow, int blue, int white, int pure, int energy) {
            this.red = new FluidStack(DYTFluids.fluidDyeRed, red);
            this.yellow = new FluidStack(DYTFluids.fluidDyeYellow, yellow);
            this.blue = new FluidStack(DYTFluids.fluidDyeBlue, blue);
            this.white = new FluidStack(DYTFluids.fluidDyeWhite, white);
            this.pure = new FluidStack(DYTFluids.fluidDyePure, pure);
            this.energy = energy;
        }

        public int getRedAmount() {
            return this.red.amount;
        }

        public int getYellowAmount() {
            return this.yellow.amount;
        }

        public int getBlueAmount() {
            return this.blue.amount;
        }

        public int getWhiteAmount() {
            return this.white.amount;
        }

        public int getPureAmount() {
            return this.white.amount;
        }

        public FluidStack getRedFluidStack() {
            return this.red.copy();
        }

        public FluidStack getYellowFluidStack() {
            return this.yellow.copy();
        }

        public FluidStack getBlueFluidStack() {
            return this.blue.copy();
        }

        public FluidStack getWhiteFluidStack() {
            return this.white.copy();
        }

        public FluidStack getPureFluidStack() {
            return this.pure.copy();
        }

        @Override
        public String toString() {
            return "pure: " + getPureAmount() + " -> red: " + getRedAmount() + " yellow:" + getYellowAmount() + " blue:" + getBlueAmount() + " white:" + getWhiteAmount();
        }

        /**
         * IFactoryRecipe
         */
        @Override
        public int getEnergy() {
            return MixerManager.RECIPE_ENERGY;
        }
    }
}
