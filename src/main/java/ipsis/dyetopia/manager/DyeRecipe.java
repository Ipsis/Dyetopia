package ipsis.dyetopia.manager;

import ipsis.dyetopia.init.ModFluids;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class DyeRecipe {

    private ItemStack dye;
    private FluidStack red;
    private FluidStack yellow;
    private FluidStack blue;
    private FluidStack white;

    public DyeRecipe(ItemStack dye, int red, int yellow, int blue, int white) {

        this.dye = dye;
        this.red = new FluidStack(ModFluids.fluidDyeRed, red);
        this.yellow = new FluidStack(ModFluids.fluidDyeYellow, yellow);
        this.blue = new FluidStack(ModFluids.fluidDyeBlue, blue);
        this.white = new FluidStack(ModFluids.fluidDyeWhite, white);
    }

    public int getRedAmount() { return this.red.amount; }
    public int getYellowAmount() { return this.yellow.amount; }
    public int getBlueAmount() { return this.blue.amount; }
    public int getWhiteAmount() { return this.white.amount; }

    public FluidStack getRed(){ return this.red; }
    public FluidStack getYellow(){ return this.yellow; }
    public FluidStack getBlue(){ return this.blue; }
    public FluidStack getWhite(){ return this.white; }

    @Override
    public String toString() {
        return dye.getUnlocalizedName() + "-> red: " + getRedAmount() + " yellow:" + getYellowAmount() + " blue:" + getBlueAmount() + " white:" + getWhiteAmount();
    }
}
