package ipsis.dyetopia.item;

import ipsis.dyetopia.block.DYTBlocks;
import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.dyetopia.reference.Names;
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

        itemEraser = new ItemEraser();
        itemDyeBlank = new ItemDyeBlank();
        itemDyemeal = new ItemDyemeal();
        itemDyeDrop = new ItemDyeDrop();

        GameRegistry.registerItem(bucketDyeRed, "item.bucketDyeRed");
        GameRegistry.registerItem(bucketDyeYellow, "item.bucketDyeYellow");
        GameRegistry.registerItem(bucketDyeBlue, "item.bucketDyeBlue");
        GameRegistry.registerItem(bucketDyeWhite, "item.bucketDyeWhite");
        GameRegistry.registerItem(bucketDyePure, "item.bucketDyePure");

        GameRegistry.registerItem(itemEraser, "item.eraser");
        GameRegistry.registerItem(itemDyeBlank, "item.dyeBlank");
        GameRegistry.registerItem(itemDyemeal, "item.dyemeal");
        GameRegistry.registerItem(itemDyeDrop, "item." + Names.Items.ITEM_DYE_DROP);
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

    public static ItemDYT itemEraser;
    public static ItemDYT itemDyeBlank;

    public static ItemDYT itemDyemeal;
    public static ItemDYT itemDyeDrop;
}
