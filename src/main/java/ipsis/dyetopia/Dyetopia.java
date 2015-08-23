package ipsis.dyetopia;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import ipsis.dyetopia.handler.DyeFileHandler;
import ipsis.dyetopia.init.*;
import ipsis.dyetopia.gui.GuiHandler;
import ipsis.dyetopia.manager.*;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlocksManager;
import ipsis.dyetopia.manager.dyeableblocks.config.DyeableBlocksConfigManager;
import ipsis.dyetopia.network.PacketHandler;
import ipsis.dyetopia.proxy.IProxy;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.handler.ConfigHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
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
        DyeableBlocksConfigManager.getInstance().applyBlacklists();

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

        Recipes.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        ModFluids.postInit();
        ModItems.postInit();
        ModBlocks.postInit();
        DYTWorldGen.postInit();
        DyeableBlocksManager.postInit();
        ForgeColorManager.getInstance().addBlacklistedModBlocks();
    }

    @Mod.EventHandler
    public void handleModIdMapping(FMLModIdMappingEvent event) {

        /**
         * All managers that use ComparableItemStack must refresh their entries
         * This is because the ids that are assigned at initialisation can be remapped if a the mods that are loaded are
         * different. The lookups are then performed with remapped ids.
         * eg. mod item stone id 127 on boot, mod item stone remapped to 278 o world load
         */
        SqueezerManager.refreshMap();
        DyeableBlocksManager.refreshMap();

    }
}
