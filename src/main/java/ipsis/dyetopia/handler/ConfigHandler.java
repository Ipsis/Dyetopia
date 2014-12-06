package ipsis.dyetopia.handler;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.dyetopia.reference.Config;
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
                Config.Trees.ENABLED, true, "Enable the dye trees").getBoolean(true);

        /* Machines */
        Settings.Machines.tankCapacity = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.TANK_CAPACITY, Settings.Machines.DEF_TANK_CAPACITY,
                Config.Machines.TANK_CAPACITY_TXT).getInt(Settings.Machines.DEF_TANK_CAPACITY);
        Settings.Machines.energyCapacity = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.ENERGY_CAPACITY, Settings.Machines.DEF_ENERGY_CAPACITY,
                Config.Machines.ENERGY_CAPACITY_TXT).getInt(Settings.Machines.DEF_ENERGY_CAPACITY);

        Settings.Machines.squeezerRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.SQUEEZER_RF_TICK, Settings.Machines.DEF_SQUEEZER_RF_TICK,
                Config.Machines.SQUEEZER_RF_TICK_TXT).getInt(Settings.Machines.DEF_SQUEEZER_RF_TICK);
        Settings.Machines.squeezerRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.SQUEEZER_RF_RECIPE, Settings.Machines.DEF_SQUEEZER_RF_RECIPE,
                Config.Machines.SQUEEZER_RF_RECIPE_TXT).getInt(Settings.Machines.DEF_SQUEEZER_RF_RECIPE);

        Settings.Machines.mixerRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.MIXER_RF_TICK, Settings.Machines.DEF_MIXER_RF_TICK,
                Config.Machines.MIXER_RF_TICK_TXT).getInt(Settings.Machines.DEF_MIXER_RF_TICK);
        Settings.Machines.mixerRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.MIXER_RF_RECIPE, Settings.Machines.DEF_MIXER_RF_RECIPE,
                Config.Machines.MIXER_RF_RECIPE_TXT).getInt(Settings.Machines.DEF_MIXER_RF_RECIPE);

        Settings.Machines.stamperRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.STAMPER_RF_TICK, Settings.Machines.DEF_STAMPER_RF_TICK,
                Config.Machines.STAMPER_RF_TICK_TXT).getInt(Settings.Machines.DEF_STAMPER_RF_TICK);
        Settings.Machines.stamperRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.STAMPER_RF_RECIPE, Settings.Machines.DEF_STAMPER_RF_RECIPE,
                Config.Machines.STAMPER_RF_RECIPE_TXT).getInt(Settings.Machines.DEF_STAMPER_RF_RECIPE);
        /*
        No user mod or you will be able to squeeze->stamp->squeeze
        Settings.Machines.stamperPureCost = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.STAMPER_PURE_COST, Settings.Machines.DEF_STAMPER_PURE_COST,
                Config.Machines.STAMPER_PURE_COST_TXT).getInt(Settings.Machines.DEF_STAMPER_PURE_COST); */

        Settings.Machines.fillerRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.FILLER_RF_TICK, Settings.Machines.DEF_FILLER_RF_TICK,
                Config.Machines.FILLER_RF_TICK_TXT).getInt(Settings.Machines.DEF_FILLER_RF_TICK);
        Settings.Machines.fillerRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.FILLER_RF_RECIPE, Settings.Machines.DEF_FILLER_RF_RECIPE,
                Config.Machines.FILLER_RF_RECIPE_TXT).getInt(Settings.Machines.DEF_FILLER_RF_RECIPE);

        Settings.Machines.painterRfTick = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.PAINTER_RF_TICK, Settings.Machines.DEF_PAINTER_RF_TICK,
                Config.Machines.PAINTER_RF_TICK_TXT).getInt(Settings.Machines.DEF_PAINTER_RF_TICK);
        Settings.Machines.painterRfRecipe = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Machines.PAINTER_RF_RECIPE, Settings.Machines.DEF_PAINTER_RF_RECIPE,
                Config.Machines.PAINTER_RF_RECIPE_TXT).getInt(Settings.Machines.DEF_PAINTER_RF_RECIPE);

        /* Items */
        Settings.Items.dyeGunTankCapacity = configuration.get(Configuration.CATEGORY_GENERAL,
                Config.Items.DYEGUN_TANK_CAPACITY, Settings.Items.DEF_DYEGUN_TANK_CAPACITY,
                Config.Items.DYEGUN_TANK_CAPACITY_TXT).getInt(Settings.Items.DEF_DYEGUN_TANK_CAPACITY);

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
