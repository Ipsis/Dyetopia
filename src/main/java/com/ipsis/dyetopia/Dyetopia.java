package com.ipsis.dyetopia;

import com.ipsis.dyetopia.block.DYTBlocks;
import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.gui.GuiHandler;
import com.ipsis.dyetopia.item.DYTItems;
import com.ipsis.dyetopia.manager.DyeLiquidManager;
import com.ipsis.dyetopia.manager.DyeSourceManager;
import com.ipsis.dyetopia.manager.MultiBlockPatternManager;
import com.ipsis.dyetopia.network.PacketHandler;
import com.ipsis.dyetopia.proxy.IProxy;
import com.ipsis.dyetopia.reference.Reference;
import com.ipsis.dyetopia.util.ConfigHelper;
import com.ipsis.dyetopia.util.LogHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class Dyetopia {

    @Mod.Instance(Reference.MOD_ID)
    public static Dyetopia instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        PacketHandler.init();
        ConfigHelper.init(event.getSuggestedConfigurationFile());

        DYTFluids.preInit();
        DYTBlocks.preInit();
        DYTItems.preInit(); /* We have buckets, so the fluid block needs to be available first! */

        FMLInterModComms.sendMessage("Waila", "register", "com.ipsis.dyetopia.plugins.waila.DYTWailaProvider.callbackRegister");
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        DYTFluids.initialize();
        DYTItems.initialize();
        DYTBlocks.initialize();

        proxy.registerEventHandlers();

        proxy.initTileEntities();

        MultiBlockPatternManager.registerPatterns();

        DyeLiquidManager.getInstance().initialize();
        DyeSourceManager.getInstance().initialize();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        DYTFluids.postInit();
        DYTItems.postInit();
        DYTBlocks.postInit();
    }
}
