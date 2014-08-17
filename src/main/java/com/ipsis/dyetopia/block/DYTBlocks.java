package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.plugins.nei.UsageManager;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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

        blockCasing = new BlockCasing();
        blockController = new BlockController();
        blockSqueezer = new BlockSqueezer();
        blockMixer = new BlockMixer();
        blockValve = new BlockValve();

        GameRegistry.registerBlock(blockCasing, "blockCasing");
        GameRegistry.registerBlock(blockController, "blockController");
        GameRegistry.registerBlock(blockSqueezer, "blockSqueezer");
        GameRegistry.registerBlock(blockMixer, "blockMixer");
        GameRegistry.registerBlock(blockValve, "blockValve");

        UsageManager.addUsage(blockSqueezer, new String[]{blockSqueezer.getUnlocalizedName() + "neiuse.0", blockSqueezer.getUnlocalizedName() + "neiuse.1" });
    }

    public static void initialize() {

        GameRegistry.addShapelessRecipe(new ItemStack(blockSqueezer), new ItemStack(Items.dye), new ItemStack(Items.flint));
    }

    public static void postInit() {

    }

    public static BlockFluidDYT blockFluidDyeRed;
    public static BlockFluidDYT blockFluidDyeYellow;
    public static BlockFluidDYT blockFluidDyeBlue;
    public static BlockFluidDYT blockFluidDyeWhite;
    public static BlockFluidDYT blockFluidDyePure;

    public static BlockDYT blockCasing;
    public static BlockDYT blockController;
    public static BlockDYT blockSqueezer;
    public static BlockDYT blockMixer;
    public static BlockDYT blockValve;
}
