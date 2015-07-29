package ipsis.dyetopia.proxy;

import net.minecraftforge.client.event.TextureStitchEvent;

public interface IProxy {

    void registerEventHandlers();
    void initTileEntities();
    void textureHook(TextureStitchEvent.Post event);
}
