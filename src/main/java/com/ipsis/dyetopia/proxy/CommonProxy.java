package com.ipsis.dyetopia.proxy;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class CommonProxy implements IProxy {

    public void initTileEntities() {

    }

    public void registerEventHandlers() {

        MinecraftForge.EVENT_BUS.register(this);
    }

    public void textureHook(TextureStitchEvent.Post event) {

    }
}
