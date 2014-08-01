package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.fluid.DYTFluids;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;

public class DYTBlocks {

    public static void preInit() {

        blockFluidDyeRed = new BlockFluidDYT(DYTFluids.fluidDyeRed, Material.water, "dyered");
        blockFluidDyeYellow = new BlockFluidDYT(DYTFluids.fluidDyeYellow, Material.water, "dyeyellow");
        blockFluidDyeBlue = new BlockFluidDYT(DYTFluids.fluidDyeBlue, Material.water, "dyeblue");
        blockFluidDyeWhite = new BlockFluidDYT(DYTFluids.fluidDyeWhite, Material.water, "dyewhite");
        blockFluidDyePure = new BlockFluidDYT(DYTFluids.fluidDyePure, Material.water, "dyepure");


        GameRegistry.registerBlock(blockFluidDyeRed, "blockDyeRed");
        GameRegistry.registerBlock(blockFluidDyeYellow, "blockDyeYellow");
        GameRegistry.registerBlock(blockFluidDyeBlue, "blockDyeBlue");
        GameRegistry.registerBlock(blockFluidDyeWhite, "blockDyeWhite");
        GameRegistry.registerBlock(blockFluidDyePure, "blockDyePure");
    }

    public static void initialize() {

    }

    public static void postInit() {

    }

    public static BlockFluidDYT blockFluidDyeRed;
    public static BlockFluidDYT blockFluidDyeYellow;
    public static BlockFluidDYT blockFluidDyeBlue;
    public static BlockFluidDYT blockFluidDyeWhite;
    public static BlockFluidDYT blockFluidDyePure;
}
