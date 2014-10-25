package ipsis.dyetopia.util;

import cofh.lib.inventory.ComparableItemStackSafe;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Converts a coloured item into its uncoloured origin item
 */
public class OriginHelper {

    private static HashMap<ComparableItemStackSafe, ItemStack> recipeMap = new HashMap();

    public static void addRecipe(ItemStack in, ItemStack out) {

        if (in != null && out != null) {
            in.stackSize = 1;
            out.stackSize = 1;
            recipeMap.put(new ComparableItemStackSafe(in), out);
        }
    }

    public static void debugDumpMap() {

        Iterator iter = recipeMap.entrySet().iterator();
        while (iter.hasNext()) {

            Map.Entry pairs = (Map.Entry)iter.next();
            ComparableItemStackSafe in = (ComparableItemStackSafe)pairs.getKey();
            ItemStack out = (ItemStack)pairs.getValue();
            LogHelper.info("[OriginHelper]: " + in.toItemStack() + "->" + out);

        }
    }

    public static ItemStack getOrigin(ItemStack in) {

        if (in == null)
            return null;

        return recipeMap.get(new ComparableItemStackSafe(in));
    }

    public static boolean hasOrigin(ItemStack in) {

        if (in == null)
            return false;

        return recipeMap.containsKey(new ComparableItemStackSafe(in));
    }

    public static boolean isOrigin(ItemStack origin) {

        if (origin == null)
            return false;

        return recipeMap.values().contains(origin);
    }

    public static ItemStack[] getOriginList() {

        return recipeMap.values().toArray(new ItemStack[0]);
    }
}
