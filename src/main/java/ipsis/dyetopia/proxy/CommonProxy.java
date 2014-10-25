package ipsis.dyetopia.proxy;

import ipsis.dyetopia.block.DYTBlocks;
import ipsis.dyetopia.handler.BucketHandler;
import ipsis.dyetopia.item.DYTItems;
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

        BucketHandler.INSTANCE.buckets.put(DYTBlocks.blockFluidDyeRed, DYTItems.bucketDyeRed);
        BucketHandler.INSTANCE.buckets.put(DYTBlocks.blockFluidDyeYellow, DYTItems.bucketDyeYellow);
        BucketHandler.INSTANCE.buckets.put(DYTBlocks.blockFluidDyeBlue, DYTItems.bucketDyeBlue);
        BucketHandler.INSTANCE.buckets.put(DYTBlocks.blockFluidDyeWhite, DYTItems.bucketDyeWhite);
        BucketHandler.INSTANCE.buckets.put(DYTBlocks.blockFluidDyePure, DYTItems.bucketDyePure);
        MinecraftForge.EVENT_BUS.register(BucketHandler.INSTANCE);
    }

    public void textureHook(TextureStitchEvent.Post event) {

    }
}
