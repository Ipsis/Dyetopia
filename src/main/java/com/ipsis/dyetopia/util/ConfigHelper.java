package com.ipsis.dyetopia.util;

import com.ipsis.dyetopia.reference.Reference;
import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigHelper {

    private static Configuration configuration;

    public static void init(File configFile) {

        configuration = new Configuration(configFile);

        try {

            configuration.load();
        }
        catch (Exception e) {

            LogHelper.error(Reference.MOD_NAME + " has had a problem loading its general configuration");
        }
        finally {

            configuration.save();
        }
    }
}
