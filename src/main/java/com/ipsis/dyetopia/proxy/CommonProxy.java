package com.ipsis.dyetopia.proxy;

import com.ipsis.dyetopia.block.DYTBlocks;
import com.ipsis.dyetopia.handler.BucketHandler;
import com.ipsis.dyetopia.item.DYTItems;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy implements IProxy {

    public void initTileEntities() {

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
