package ipsis.dyetopia.init;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class ModFluids {

    public static void preInit() {

        /* Keep naming in the format of the oredict type/info */
        fluidDyeRed = new Fluid("dyered");
        fluidDyeYellow = new Fluid("dyeyellow");
        fluidDyeBlue = new Fluid("dyeblue");
        fluidDyeWhite = new Fluid("dyewhite");
        fluidDyePure = new Fluid("dyepure");

        FluidRegistry.registerFluid(fluidDyeRed);
        FluidRegistry.registerFluid(fluidDyeYellow);
        FluidRegistry.registerFluid(fluidDyeBlue);
        FluidRegistry.registerFluid(fluidDyeWhite);
        FluidRegistry.registerFluid(fluidDyePure);
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static Fluid fluidDyeRed;
    public static Fluid fluidDyeYellow;
    public static Fluid fluidDyeBlue;
    public static Fluid fluidDyeWhite;
    public static Fluid fluidDyePure;
}
