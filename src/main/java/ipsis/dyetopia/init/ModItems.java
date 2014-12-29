package ipsis.dyetopia.init;

import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.dyetopia.item.*;
import ipsis.dyetopia.reference.Names;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;

public class ModItems {

    public static void preInit() {

        bucketDyeRed = new ItemBucketDYT(ModBlocks.blockFluidDyeRed, "bucketDyeRed");
        bucketDyeYellow = new ItemBucketDYT(ModBlocks.blockFluidDyeYellow, "bucketDyeYellow");
        bucketDyeBlue = new ItemBucketDYT(ModBlocks.blockFluidDyeBlue, "bucketDyeBlue");
        bucketDyeWhite = new ItemBucketDYT(ModBlocks.blockFluidDyeWhite, "bucketDyeWhite");
        bucketDyePure = new ItemBucketDYT(ModBlocks.blockFluidDyePure, "bucketDyePure");

        itemEraser = new ItemEraser();
        itemDyeBlank = new ItemDyeBlank();
        itemDyeGun = new ItemDyeGun();

        itemDyemeal = new ItemDyemeal();
        itemDyeDrop = new ItemDyeDrop();
        itemDyeBeans = new ItemDyeBeans();
        itemDyeChunk = new ItemDyeChunk();

        GameRegistry.registerItem(bucketDyeRed, "item.bucketDyeRed");
        GameRegistry.registerItem(bucketDyeYellow, "item.bucketDyeYellow");
        GameRegistry.registerItem(bucketDyeBlue, "item.bucketDyeBlue");
        GameRegistry.registerItem(bucketDyeWhite, "item.bucketDyeWhite");
        GameRegistry.registerItem(bucketDyePure, "item.bucketDyePure");

        GameRegistry.registerItem(itemEraser, "item.eraser");
        GameRegistry.registerItem(itemDyeBlank, "item.dyeBlank");
        GameRegistry.registerItem(itemDyeGun, "item." + Names.Items.ITEM_DYE_GUN);

        GameRegistry.registerItem(itemDyemeal, "item.dyemeal");
        GameRegistry.registerItem(itemDyeDrop, "item." + Names.Items.ITEM_DYE_DROP);
        GameRegistry.registerItem(itemDyeBeans, "item." + Names.Items.ITEM_DYE_BEANS);
        GameRegistry.registerItem(itemDyeChunk, "item" + Names.Items.ITEM_DYE_CHUNK);

        /* debug only */
        itemDebug = new ItemDebug();
        GameRegistry.registerItem(itemDebug, "item.debug");
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
    public static ItemFluidContainerDYT itemDyeGun;

    public static ItemDYT itemDyemeal;
    public static ItemDYT itemDyeDrop;
    public static ItemDYT itemDyeBeans;
    public static ItemDYT itemDyeChunk;

    /* debug only */
    public static ItemDYT itemDebug;
}
