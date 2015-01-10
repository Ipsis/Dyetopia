package ipsis.dyetopia.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 * Based off  Equivalent-Exchange-3/src/main/java/com/pahimar/ee3/util/NBTHelper.java
 * By Pahimar
 */
public class ItemStackNBTHelper {

    private static void initTagCompound(ItemStack stack) {

        if (stack.getTagCompound() == null)
            stack.setTagCompound(new NBTTagCompound());
    }

    public static boolean hashTag(ItemStack stack, String key) {
        return stack != null && stack.getTagCompound() != null && stack.getTagCompound().hasKey(key);
    }

    /**
     * Set API
     */
    public static void setLong(ItemStack stack, String key, long v) {
        initTagCompound(stack);
        stack.getTagCompound().setLong(key, v);
    }

    public static void setString(ItemStack stack, String key, String v) {
        initTagCompound(stack);
        stack.getTagCompound().setString(key, v);
    }

    public static void setBoolean(ItemStack stack, String key, boolean v) {
        initTagCompound(stack);
        stack.getTagCompound().setBoolean(key, v);
    }

    public static void setByte(ItemStack stack, String key, byte v) {
        initTagCompound(stack);
        stack.getTagCompound().setByte(key, v);
    }

    public static void setShort(ItemStack stack, String key, short v) {
        initTagCompound(stack);
        stack.getTagCompound().setShort(key, v);
    }

    public static void setInteger(ItemStack stack, String key, int v) {
        initTagCompound(stack);
        stack.getTagCompound().setInteger(key, v);
    }

    public static void setFloat(ItemStack stack, String key, float v) {
        initTagCompound(stack);
        stack.getTagCompound().setFloat(key, v);
    }

    public static void setDouble(ItemStack stack, String key, double v) {
        initTagCompound(stack);
        stack.getTagCompound().setDouble(key, v);
    }

    public static void setTagList(ItemStack stack, String key, NBTTagList v) {
        initTagCompound(stack);
        stack.getTagCompound().setTag(key, v);
    }

    public static void setTagCompound(ItemStack stack, String key, NBTTagCompound v) {
        initTagCompound(stack);
        stack.getTagCompound().setTag(key, v);
    }

    /**
     * Get API
     *
     * Defaults are
     * Long - 0
     * String - ""
     * Boolean - false
     * Byte - 0
     * Short - 0
     * Integer - 0
     * Float - 0
     * Double - 0
     */
    public static long getLong(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setLong(key, 0);

        return stack.getTagCompound().getLong(key);
    }

    public static String getString(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setString(key, "");

        return stack.getTagCompound().getString(key);
    }

    public static boolean getBoolean(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setBoolean(key, false);

        return stack.getTagCompound().getBoolean(key);
    }

    public static byte getByte(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setByte(key, (byte) 0);

        return stack.getTagCompound().getByte(key);
    }

    public static short getShort(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setShort(key, (short) 0);

        return stack.getTagCompound().getShort(key);
    }

    public static int getInteger(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setInteger(key, 0);

        return stack.getTagCompound().getInteger(key);
    }

    public static float getFloat(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setFloat(key, 0);

        return stack.getTagCompound().getFloat(key);
    }

    public static double getDouble(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setDouble(key, 0);

        return stack.getTagCompound().getDouble(key);
    }

    public static NBTTagList getTagList(ItemStack stack, String key, int type) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setTag(key, new NBTTagList());

        return stack.getTagCompound().getTagList(key, type);
    }

    public static NBTTagCompound getTagCompound(ItemStack stack, String key) {
        initTagCompound(stack);
        if (!stack.getTagCompound().hasKey(key))
            stack.getTagCompound().setTag(key, new NBTTagCompound());

        return stack.getTagCompound().getCompoundTag(key);
    }
}
