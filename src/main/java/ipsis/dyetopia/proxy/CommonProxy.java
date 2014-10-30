package ipsis.dyetopia.proxy;

import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.handler.BucketHandler;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.tileentity.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy implements IProxy {

    public void initTileEntities() {

        GameRegistry.registerTileEntity(TileEntityMultiBlockBase.class, "tile.multiBlockBase");
        GameRegistry.registerTileEntity(TileEntitySqueezer.class, "tile.squeezer");
        GameRegistry.registerTileEntity(TileEntityMixer.class, "tile.mixer");
        GameRegistry.registerTileEntity(TileEntityValve.class, "tile.valve");
        GameRegistry.registerTileEntity(TileEntityController.class, "tile.controller");
        GameRegistry.registerTileEntity(TileEntityCasing.class, "tile.casing");

        GameRegistry.registerTileEntity(TileEntityPainter.class, "tile.painter");
        GameRegistry.registerTileEntity(TileEntityStamper.class, "tile.stamper");
        GameRegistry.registerTileEntity(TileEntityFiller.class, "tile.filler");
    }

    public void registerEventHandlers() {

        MinecraftForge.EVENT_BUS.register(this);

        BucketHandler.INSTANCE.buckets.put(ModBlocks.blockFluidDyeRed, ModItems.bucketDyeRed);
        BucketHandler.INSTANCE.buckets.put(ModBlocks.blockFluidDyeYellow, ModItems.bucketDyeYellow);
        BucketHandler.INSTANCE.buckets.put(ModBlocks.blockFluidDyeBlue, ModItems.bucketDyeBlue);
        BucketHandler.INSTANCE.buckets.put(ModBlocks.blockFluidDyeWhite, ModItems.bucketDyeWhite);
        BucketHandler.INSTANCE.buckets.put(ModBlocks.blockFluidDyePure, ModItems.bucketDyePure);
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
    }

    public void textureHook(TextureStitchEvent.Post event) {

    }
}
