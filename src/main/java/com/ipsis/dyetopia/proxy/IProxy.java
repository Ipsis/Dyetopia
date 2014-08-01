package com.ipsis.dyetopia.proxy;

import net.minecraftforge.client.event.TextureStitchEvent;

public interface IProxy {

    public abstract void registerEventHandlers();
    public abstract void initTileEntities();
    public abstract void textureHook(TextureStitchEvent.Post event);
}
