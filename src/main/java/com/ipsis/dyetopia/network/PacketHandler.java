package com.ipsis.dyetopia.network;

import com.ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    public static void init() {

        //INSTANCE.registerMessage(MessageGui.class, MessageGui.class, 0, Side.SERVER);
    }
}
