package ipsis.dyetopia.manager.dyeableblocks.config;

import cpw.mods.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

public class ModInfo {

    /* Are modid always lowercase?? */
    String modid;
    List<BlockDescBase> mappings;

    public ModInfo() {
        mappings = new ArrayList<BlockDescBase>();
    }

    public void setModid(String modid) {
        this.modid = modid;
    }

    public String getModid() {
        return this.modid;
    }

    public boolean hasModid() {
        return modid != null && !modid.equals("");
    }

    public boolean isLoaded() {

        if (modid.equals("vanilla"))
            return true;

        return Loader.isModLoaded(modid) || Loader.isModLoaded(modid.toLowerCase());
    }

    public void addMapping(BlockDescBase mapping) {
        mappings.add(mapping);
    }

    public List<BlockDescBase> getMappings() { return mappings; }
}
