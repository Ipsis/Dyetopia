package ipsis.dyetopia.block;

import ipsis.dyetopia.block.plantlife.*;
import ipsis.dyetopia.fluid.DYTFluids;
import ipsis.dyetopia.item.plantlife.ItemBlockHeartDye;
import ipsis.dyetopia.item.plantlife.ItemBlockLeavesDye;
import ipsis.dyetopia.item.plantlife.ItemBlockLogDye;
import ipsis.dyetopia.item.plantlife.ItemBlockSaplingDye;
import ipsis.dyetopia.plugins.nei.UsageManager;
import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.dyetopia.reference.Names;
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

        blockCasing = new BlockCasing();
        blockController = new BlockController();
        blockSqueezer = new BlockSqueezer();
        blockMixer = new BlockMixer();
        blockValve = new BlockValve();

        blockPainter = new BlockPainter();
        blockStamper = new BlockStamper();
        blockFiller = new BlockFiller();

        blockSaplingDye = new BlockSaplingDye();
        blockLeavesDye = new BlockLeavesDye();
        blockLogDye = new BlockLogDye();
        blockHeartDye = new BlockHeartDye();
        blockRootPureDye = new BlockRootDye();
        blockPodRedDye = new BlockPodDye(0, Names.Blocks.BLOCK_POD_DYE_RED);
        blockPodYellowDye = new BlockPodDye(1, Names.Blocks.BLOCK_POD_DYE_YELLOW);
        blockPodBlueDye = new BlockPodDye(2, Names.Blocks.BLOCK_POD_DYE_BLUE);

        GameRegistry.registerBlock(blockCasing, "blockCasing");
        GameRegistry.registerBlock(blockController, "blockController");
        GameRegistry.registerBlock(blockSqueezer, "blockSqueezer");
        GameRegistry.registerBlock(blockMixer, "blockMixer");
        GameRegistry.registerBlock(blockValve, "blockValve");

        GameRegistry.registerBlock(blockPainter, "blockPainter");
        GameRegistry.registerBlock(blockStamper, "blockStamper");
        GameRegistry.registerBlock(blockFiller, "blockFiller");

        GameRegistry.registerBlock(blockSaplingDye, ItemBlockSaplingDye.class, Names.Blocks.BLOCK_SAPLING_DYE);
        GameRegistry.registerBlock(blockLeavesDye, ItemBlockLeavesDye.class, Names.Blocks.BLOCK_LEAVES_DYE);
        GameRegistry.registerBlock(blockLogDye, ItemBlockLogDye.class, Names.Blocks.BLOCK_LOG_DYE);
        GameRegistry.registerBlock(blockHeartDye, ItemBlockHeartDye.class, Names.Blocks.BLOCK_HEART_DYE);
        GameRegistry.registerBlock(blockRootPureDye, Names.Blocks.BLOCK_ROOT_DYE);

        GameRegistry.registerBlock(blockPodRedDye, Names.Blocks.BLOCK_POD_DYE_RED);
        GameRegistry.registerBlock(blockPodYellowDye, Names.Blocks.BLOCK_POD_DYE_YELLOW);
        GameRegistry.registerBlock(blockPodBlueDye, Names.Blocks.BLOCK_POD_DYE_BLUE);

        UsageManager.addUsage(blockSqueezer, new String[]{blockSqueezer.getUnlocalizedName() + "neiuse.0", blockSqueezer.getUnlocalizedName() + "neiuse.1"});
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

    public static BlockDYT blockCasing;
    public static BlockDYT blockController;
    public static BlockDYT blockSqueezer;
    public static BlockDYT blockMixer;
    public static BlockDYT blockValve;

    public static BlockDYT blockPainter;
    public static BlockDYT blockFiller;
    public static BlockDYT blockStamper;

    public static BlockSaplingDye blockSaplingDye;
    public static BlockLeavesDye blockLeavesDye;
    public static BlockLogDye blockLogDye;
    public static BlockHeartDye blockHeartDye;
    public static BlockRootDye blockRootPureDye;

    public static BlockPodDye blockPodRedDye;
    public static BlockPodDye blockPodYellowDye;
    public static BlockPodDye blockPodBlueDye;

}
