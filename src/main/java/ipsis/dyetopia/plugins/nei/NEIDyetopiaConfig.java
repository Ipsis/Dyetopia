package ipsis.dyetopia.plugins.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.util.LogHelper;

public class NEIDyetopiaConfig implements IConfigureNEI {

    @Override
    public void loadConfig() {

        LogHelper.info("NEI loadConfig");

        API.registerUsageHandler(new RecipeHandlerSqueezer());
    }

    @Override
    public String getName() {
        return Reference.MOD_NAME;
    }

    @Override
    public String getVersion() {
        return Reference.MOD_VERSION;
    }
}
