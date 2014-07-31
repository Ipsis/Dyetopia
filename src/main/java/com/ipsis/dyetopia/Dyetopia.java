package com.ipsis.dyetopia;

import com.ipsis.dyetopia.block.DYTBlocks;
import com.ipsis.dyetopia.item.DYTItems;
import com.ipsis.dyetopia.proxy.IProxy;
import com.ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class Dyetopia {

    @Mod.Instance(Reference.MOD_ID)
    public static Dyetopia instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        DYTBlocks.preInit();
        DYTItems.preInit();
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        DYTBlocks.initialize();
        DYTItems.initialize();

        proxy.initTileEntities();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        DYTBlocks.postInit();
        DYTItems.postInit();
    }
}
