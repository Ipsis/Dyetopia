package ipsis.dyetopia.manager.dyeableblocks.config;


import ipsis.dyetopia.util.DyeHelper;

public class BlockDescAttrMap extends BlockDescSimple {

    /**
     * Mapping of dye to attribute
     * The index into the array is the DyeType
     */
    int[] attrMap;

    public BlockDescAttrMap() {
        super();
        attrMap = new int[DyeHelper.DyeType.VALID_DYES.length];
        this.vanillaOrder = false;
    }

    public void setAttrMapEntry(DyeHelper.DyeType dye, int attr) {
        if (attr >= 0 && attr <= 65535 && dye != DyeHelper.DyeType.INVALID)
            attrMap[dye.ordinal()] = attr;
    }

    public int getAttrMapEntry(DyeHelper.DyeType dye) {
        return dye == DyeHelper.DyeType.INVALID ? -1 : attrMap[dye.ordinal()];
    }
}
