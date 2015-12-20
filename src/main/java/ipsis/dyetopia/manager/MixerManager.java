package ipsis.dyetopia.manager;

import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.reference.Settings;
import net.minecraftforge.fluids.FluidStack;

public class MixerManager {

    private static MixerRecipe recipe;

    public static void initialise() {
        recipe = new MixerRecipe(
                Settings.Machines.mixerAmountRed,
                Settings.Machines.mixerAmountYellow,
                Settings.Machines.mixerAmountBlue,
                Settings.Machines.mixerAmountWhite, 1000);
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

        private MixerRecipe() { }

        public MixerRecipe(int red, int yellow, int blue, int white, int pure) {
            this.red = new FluidStack(ModFluids.fluidDyeRed, red);
            this.yellow = new FluidStack(ModFluids.fluidDyeYellow, yellow);
            this.blue = new FluidStack(ModFluids.fluidDyeBlue, blue);
            this.white = new FluidStack(ModFluids.fluidDyeWhite, white);
            this.pure = new FluidStack(ModFluids.fluidDyePure, pure);
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
            return this.pure.amount;
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
            return Settings.Machines.mixerRfRecipe;
        }
    }
}
