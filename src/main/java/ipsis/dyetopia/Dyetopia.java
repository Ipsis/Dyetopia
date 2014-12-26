package ipsis.dyetopia;

import cpw.mods.fml.common.FMLCommonHandler;
import ipsis.dyetopia.handler.DyeFileHandler;
import ipsis.dyetopia.init.*;
import ipsis.dyetopia.gui.GuiHandler;
import ipsis.dyetopia.manager.*;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlocksManager;
import ipsis.dyetopia.network.PacketHandler;
import ipsis.dyetopia.proxy.IProxy;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.handler.ConfigHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import ipsis.dyetopia.world.gen.feature.DYTWorldGen;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTORY_CLASS)
public class Dyetopia {

    @Mod.Instance(Reference.MOD_ID)
    public static Dyetopia instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        PacketHandler.init();
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());

        ModFluids.preInit();
        ModBlocks.preInit();
        ModItems.preInit(); /* We have buckets, so the fluid block needs to be available first! */
        DYTWorldGen.preInit();

        ModOreDict.preInit();

        DyeFileHandler.getInstance().load(event.getModConfigurationDirectory());

        FMLInterModComms.sendMessage("Waila", "register", "ipsis.dyetopia.plugins.waila.DYTWailaProvider.callbackRegister");
    }

    @Mod.EventHandler
    public void initialize(FMLInitializationEvent event) {

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        ModFluids.initialize();
        ModItems.initialize();
        ModBlocks.initialize();
        DYTWorldGen.initialize();

        proxy.registerEventHandlers();

        proxy.initTileEntities();

        MultiBlockPatternManager.registerPatterns();

        DyeLiquidManager.getInstance().initialize();
        SqueezerManager.initialise();
        MixerManager.initialise();
        StamperManager.initialise();
        DyeableBlocksManager.initialise();

        DyeSourceManager.getInstance().initialize();

        Recipes.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        ModFluids.postInit();
        ModItems.postInit();
        ModBlocks.postInit();
        DYTWorldGen.postInit();
        DyeableBlocksManager.postInit();
    }
}
