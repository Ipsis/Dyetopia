package ipsis.dyetopia.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.util.LogHelper;

public class NEIPluginInitConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {

        LogHelper.info("Initialising " + getName());

        API.registerRecipeHandler(new NEISqueezerRecipeManager());
        API.registerRecipeHandler(new NEIPainterRecipeManager());
    }

    @Override
    public String getName() {
        return Reference.MOD_NAME + " NEI plugin";
    }

    @Override
    public String getVersion() {
        return Reference.MOD_VERSION;
    }
}