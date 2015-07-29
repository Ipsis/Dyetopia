package ipsis.dyetopia.manager.dyeableblocks.config;

import ipsis.dyetopia.util.DyeHelper;

import java.util.HashMap;

public class BlockDescFull extends BlockDescBase {

    /* Map of color to colored block */
    HashMap<DyeHelper.DyeType, ModObjectDesc.ModObjectDyedDesc> fullMap;

    public BlockDescFull() {
        super();
        fullMap = new HashMap<DyeHelper.DyeType, ModObjectDesc.ModObjectDyedDesc>();
    }

    @Override
    public boolean isValid() {
        return !fullMap.isEmpty();
    }

    public void addEntry(String name, int attr, DyeHelper.DyeType dye) {

        if (dye == DyeHelper.DyeType.INVALID || name == null || name.equals("") || attr < 0 || attr > 15)
            return;

        fullMap.put(dye, new ModObjectDesc.ModObjectDyedDesc(name, attr, dye));
    }

    public ModObjectDesc.ModObjectDyedDesc getEntry(DyeHelper.DyeType dye) {

        ModObjectDesc.ModObjectDyedDesc desc = fullMap.get(dye);
        if (desc != null && !DyeableBlocksConfigManager.getInstance().isBlockBlacklisted(desc.getName()))
            return desc;

        return null;
    }

}
