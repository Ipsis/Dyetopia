package ipsis.dyetopia.manager.dyeableblocks;

import cpw.mods.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

public class DyeableModInfo {

    public String modid;
    public List<DyeableBlockDesc> mappings;

    public DyeableModInfo() {

        mappings = new ArrayList<DyeableBlockDesc>();
    }

    public boolean isLoaded() {

        if (modid.equals("vanilla"))
            return true;

        return Loader.isModLoaded(modid) || Loader.isModLoaded(modid.toLowerCase());
    }
}
