package ipsis.dyetopia.handler;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.dyetopia.reference.Config;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Settings;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHandler {

    public static Configuration configuration;

    public static void init(File configFile)
    {
        if (configuration == null)
        {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration()
    {
        /* Trees */
        Settings.Trees.enabled = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Trees.ENABLED, true,
                StringHelper.localize(Lang.Config.TREES_ENABLED)).getBoolean(true);

        /* Machines */
        Settings.Machines.tankCapacity = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.TANK_CAPACITY, Settings.Machines.DEF_TANK_CAPACITY,
                StringHelper.localize(Lang.Config.MACHINES_TANK_CAPACITY)).getInt(Settings.Machines.DEF_TANK_CAPACITY);
        Settings.Machines.energyCapacity = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.ENERGY_CAPACITY, Settings.Machines.DEF_ENERGY_CAPACITY,
                StringHelper.localize(Lang.Config.MACHINES_ENERGY_CAPACITY)).getInt(Settings.Machines.DEF_ENERGY_CAPACITY);
        Settings.Machines.energyRxTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.ENERGY_RX_TICK, Settings.Machines.DEF_RX_RF_TICK,
                StringHelper.localize(Lang.Config.MACHINES_ENERGY_RX_TICK)).getInt(Settings.Machines.DEF_RX_RF_TICK);

        Settings.Machines.squeezerRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.SQUEEZER_RF_TICK, Settings.Machines.DEF_SQUEEZER_RF_TICK,
                StringHelper.localize(Lang.Config.MACHINES_ENERGY_SQUEEZER_RF_TICK)).getInt(Settings.Machines.DEF_SQUEEZER_RF_TICK);
        Settings.Machines.squeezerRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.SQUEEZER_RF_RECIPE, Settings.Machines.DEF_SQUEEZER_RF_RECIPE,
                StringHelper.localize(Lang.Config.MACHINES_RECIPE_SQUEEZER_ENERGY)).getInt(Settings.Machines.DEF_SQUEEZER_RF_RECIPE);

        Settings.Machines.mixerRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.MIXER_RF_TICK, Settings.Machines.DEF_MIXER_RF_TICK,
                StringHelper.localize(Lang.Config.MACHINES_ENERGY_MIXER_RF_TICK)).getInt(Settings.Machines.DEF_MIXER_RF_TICK);
        Settings.Machines.mixerRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.MIXER_RF_RECIPE, Settings.Machines.DEF_MIXER_RF_RECIPE,
                StringHelper.localize(Lang.Config.MACHINES_RECIPE_MIXER_ENERGY)).getInt(Settings.Machines.DEF_MIXER_RF_RECIPE);

        Settings.Machines.mixerAmountRed = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.MIXER_RECIPE_RED, Settings.Machines.DEF_MIXER_MB_RED,
                StringHelper.localize(Lang.Config.MACHINES_RECIPE_MIXER_RED_AMOUNT)).getInt(Settings.Machines.DEF_MIXER_MB_RED);
        Settings.Machines.mixerAmountYellow = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.MIXER_RECIPE_YELLOW, Settings.Machines.DEF_MIXER_MB_YELLOW,
                StringHelper.localize(Lang.Config.MACHINES_RECIPE_MIXER_YELLOW_AMOUNT)).getInt(Settings.Machines.DEF_MIXER_MB_YELLOW);
        Settings.Machines.mixerAmountBlue = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.MIXER_RECIPE_BLUE, Settings.Machines.DEF_MIXER_MB_BLUE,
                StringHelper.localize(Lang.Config.MACHINES_RECIPE_MIXER_BLUE_AMOUNT)).getInt(Settings.Machines.DEF_MIXER_MB_BLUE);
        Settings.Machines.mixerAmountWhite = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.MIXER_RECIPE_WHITE, Settings.Machines.DEF_MIXER_MB_WHITE,
                StringHelper.localize(Lang.Config.MACHINES_RECIPE_MIXER_WHITE_AMOUNT)).getInt(Settings.Machines.DEF_MIXER_MB_WHITE);

        Settings.Machines.stamperRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.STAMPER_RF_TICK, Settings.Machines.DEF_STAMPER_RF_TICK,
                StringHelper.localize(Lang.Config.MACHINES_ENERGY_STAMPER_RF_TICK)).getInt(Settings.Machines.DEF_STAMPER_RF_TICK);
        Settings.Machines.stamperRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.STAMPER_RF_RECIPE, Settings.Machines.DEF_STAMPER_RF_RECIPE,
                StringHelper.localize(Lang.Config.MACHINES_RECIPE_STAMPER_ENERGY)).getInt(Settings.Machines.DEF_STAMPER_RF_RECIPE);

        Settings.Machines.fillerRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.FILLER_RF_TICK, Settings.Machines.DEF_FILLER_RF_TICK,
                StringHelper.localize(Lang.Config.MACHINES_ENERGY_FILLER_RF_TICK)).getInt(Settings.Machines.DEF_FILLER_RF_TICK);
        Settings.Machines.fillerDyeTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.FILLER_DYE_TICK, Settings.Machines.DEF_FILLER_DYE_TICK,
                StringHelper.localize(Lang.Config.MACHINES_DYE_FILLER_MB_TICK)).getInt(Settings.Machines.DEF_FILLER_DYE_TICK);

        Settings.Machines.painterRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.PAINTER_RF_TICK, Settings.Machines.DEF_PAINTER_RF_TICK,
                StringHelper.localize(Lang.Config.MACHINES_ENERGY_PAINTER_RF_TICK)).getInt(Settings.Machines.DEF_PAINTER_RF_TICK);
        Settings.Machines.painterRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.PAINTER_RF_RECIPE, Settings.Machines.DEF_PAINTER_RF_RECIPE,
                StringHelper.localize(Lang.Config.MACHINES_RECIPE_PAINTER_ENERGY)).getInt(Settings.Machines.DEF_PAINTER_RF_RECIPE);

        /* Items */
        Settings.Items.dyeGunTankCapacity = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Items.DYEGUN_TANK_CAPACITY, Settings.Items.DEF_DYEGUN_TANK_CAPACITY,
                StringHelper.localize(Lang.Config.ITEM_DYEGUN_CAPACITY)).getInt(Settings.Items.DEF_DYEGUN_TANK_CAPACITY);

        if (configuration.hasChanged())
        {
            configuration.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.modID.equalsIgnoreCase(Reference.MOD_ID))
        {
            loadConfiguration();
        }
    }
}
