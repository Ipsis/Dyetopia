package ipsis.dyetopia.manager;

import ipsis.dyetopia.util.DyeHelper;

public class PainterManager {

    public static DyeHelper.DyeType getFirst() { return DyeHelper.DyeType.VALID_DYES[0]; }
    public static DyeHelper.DyeType getNext(DyeHelper.DyeType curr) {
        return curr.getNext();
    }
    public static DyeHelper.DyeType getPrev(DyeHelper.DyeType curr) {
        return curr.getPrev();
    }
}
