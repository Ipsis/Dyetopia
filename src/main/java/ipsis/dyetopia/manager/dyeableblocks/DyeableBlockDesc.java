package ipsis.dyetopia.manager.dyeableblocks;

import ipsis.dyetopia.handler.DyeFileHandler;
import ipsis.dyetopia.util.DyeHelper;

public class DyeableBlockDesc {

    public enum MapType {
        SIMPLE,
        VANILLA,    /* with or without offset */
        FULL_META,
        FULL_BLOCK,
        INVALID
    }

    @Override
    public String toString() {
        return refname + ":" + blockName + ":" + type;
    }

    public static class BlockMapDesc {
        public String name;
        public int meta;
        public DyeHelper.DyeType dye;

        public BlockMapDesc() {
            meta = -1;
            dye = DyeHelper.DyeType.INVALID;
        }
    }

    public String refname;
    public boolean associative;

    public String originName;
    public int originMeta;
    public String blockName;

    /* Mapping */
    public MapType type;
    public int offset;
    public int[] metaMap;
    public BlockMapDesc[] blockMap;


    public DyeableBlockDesc() {
        associative = true;
        refname = Integer.toString(hashCode());
        type = MapType.INVALID;
        offset = 0;
        originMeta = -1;
    }

    public boolean isValid() {

        if (type == MapType.INVALID)
            return false;

        /* No point */
        if ((!hasOrigin() && !associative) || !hasBlockName())
            return false;

        if (blockName == null && blockName.equals(""))
            return false;

        if (type == MapType.FULL_META) {
            if (metaMap == null)
                return false;

            /** all entries must be set */
            for (int i = 0; i < 16; i++)
                if (metaMap[i] == -1)
                    return false;

        } else if (type == MapType.FULL_BLOCK) {

            for (BlockMapDesc desc : blockMap) {
                if (desc.name == null || desc.name.equals(""))
                    return false;
            }
        }

        return true;
    }

    public void initMetaMap() {
        metaMap = new int[16];
        for (int i = 0; i < 16; i++)
            metaMap[i] = -1;
    }

    public void initBlockMap() {
        blockMap = new BlockMapDesc[16];
    }

    public void setMetaMap(int idx, int v) {
        if (metaMap != null && idx >= 0 && idx < 16)
            metaMap[idx] = v;
    }

    public boolean hasOrigin() {
        return originMeta != -1 && originName != null;
    }

    public boolean isOriginBlacklisted() {
        return (hasOrigin() && DyeFileHandler.getInstance().isBlockBlacklisted(originName));
    }

    public boolean hasBlockName() {
        return blockName != null && !blockName.equals("");
    }

    public boolean isBlockBlacklisted() {
        return (hasBlockName() && DyeFileHandler.getInstance().isBlockBlacklisted(blockName));
    }
}
