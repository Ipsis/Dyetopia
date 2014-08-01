package com.ipsis.dyetopia.item;

import com.ipsis.dyetopia.block.DYTBlocks;
import com.ipsis.dyetopia.util.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class DYTItems {

    public static void preInit() {

        bucketDyeRed = new ItemBucketDYT(DYTBlocks.blockFluidDyeRed, "bucketDyeRed");
        bucketDyeYellow = new ItemBucketDYT(DYTBlocks.blockFluidDyeYellow, "bucketDyeYellow");
        bucketDyeBlue = new ItemBucketDYT(DYTBlocks.blockFluidDyeBlue, "bucketDyeBlue");
        bucketDyeWhite = new ItemBucketDYT(DYTBlocks.blockFluidDyeWhite, "bucketDyeWhite");
        bucketDyePure = new ItemBucketDYT(DYTBlocks.blockFluidDyePure, "bucketDyePure");

        GameRegistry.registerItem(bucketDyeRed, "item.bucketDyeRed");
        GameRegistry.registerItem(bucketDyeYellow, "item.bucketDyeYellow");
        GameRegistry.registerItem(bucketDyeBlue, "item.bucketDyeBlue");
        GameRegistry.registerItem(bucketDyeWhite, "item.bucketDyeWhite");
        GameRegistry.registerItem(bucketDyePure, "item.bucketDyePure");
    }

    public static void initialize() {

        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("dyered", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(bucketDyeRed), new ItemStack(Items.bucket));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("dyeyellow", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(bucketDyeYellow), new ItemStack(Items.bucket));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("dyeblue", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(bucketDyeBlue), new ItemStack(Items.bucket));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("dyewhite", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(bucketDyeWhite), new ItemStack(Items.bucket));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack("dyepure", FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(bucketDyePure), new ItemStack(Items.bucket));
    }

    public static void postInit() {

    }

    public static ItemBucketDYT bucketDyeRed;
    public static ItemBucketDYT bucketDyeYellow;
    public static ItemBucketDYT bucketDyeBlue;
    public static ItemBucketDYT bucketDyeWhite;
    public static ItemBucketDYT bucketDyePure;
}
