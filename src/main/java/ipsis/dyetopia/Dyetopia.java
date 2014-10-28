package ipsis.dyetopia;

import ipsis.dyetopia.block.DYTBlocks;
import ipsis.dyetopia.fluid.DYTFluids;
import ipsis.dyetopia.gui.GuiHandler;
import ipsis.dyetopia.item.DYTItems;
import ipsis.dyetopia.manager.*;
import ipsis.dyetopia.network.PacketHandler;
import ipsis.dyetopia.proxy.IProxy;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.util.ConfigHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import ipsis.dyetopia.util.OreDictHelper;
import ipsis.dyetopia.world.gen.feature.DYTWorldGen;

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
        DYTWorldGen.preInit();

        OreDictHelper.registerOres();

        FMLInterModComms.sendMessage("Waila", "register", "ipsis.dyetopia.plugins.waila.DYTWailaProvider.callbackRegister");
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        DYTFluids.initialize();
        DYTItems.initialize();
        DYTBlocks.initialize();
        DYTWorldGen.initialize();

        proxy.registerEventHandlers();

        proxy.initTileEntities();

        MultiBlockPatternManager.registerPatterns();

        DyeLiquidManager.getInstance().initialize();
        SqueezerManager.initialise();
        MixerManager.initialise();
        PainterManager.initialise();
        StamperManager.initialise();

        DyeSourceManager.getInstance().initialize();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        DYTFluids.postInit();
        DYTItems.postInit();
        DYTBlocks.postInit();
        DYTWorldGen.postInit();
    }
}
