package com.ipsis.dyetopia.network;

import com.ipsis.dyetopia.network.message.MessageGuiFixedProgressBar;
import com.ipsis.dyetopia.network.message.MessageGuiWidget;
import com.ipsis.dyetopia.network.message.MessageTileEntityDYT;
import com.ipsis.dyetopia.network.message.MessageTileEntityMultiBlock;
import com.ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    public static void init() {

        INSTANCE.registerMessage(MessageGuiWidget.class, MessageGuiWidget.class, 0, Side.SERVER);
        INSTANCE.registerMessage(MessageGuiFixedProgressBar.class, MessageGuiFixedProgressBar.class, 1, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileEntityDYT.class, MessageTileEntityDYT.class, 2, Side.CLIENT);
        INSTANCE.registerMessage(MessageTileEntityMultiBlock.class, MessageTileEntityMultiBlock.class, 3, Side.CLIENT);
    }
}
