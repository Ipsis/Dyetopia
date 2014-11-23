package ipsis.dyetopia.util;

import com.google.common.primitives.Ints;
import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.reference.Names;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Generic dye  : the oredict entry that maps to any dye
 * Specific dye : the oredict entry that maps to a specific dye eg. dyeWhite
 */

public class OreDictHelper {

    /* The generic dye is called dye, other dyes are named dyeXXX */
    private static final String GENERIC_DYE_ORE_NAME = "dye";
    private static final String SPECIFIC_DYE_ORE_PREFIX = "dye";

    /**
     * Does the oreid represent an ore dict entry matching the generic dye
     */
    public static boolean isGenericDyeName(int oreid) {
        return isGenericDyeName(OreDictionary.getOreName(oreid));
    }

    /**
     * Is the string the generic dye name
     */
    public static boolean isGenericDyeName(String s) {
        if (s != null && s.equals(GENERIC_DYE_ORE_NAME))
            return true;
        return false;
    }

    /**
     * Does the oreid represent an ore dict entry matching a specific dye
     */
    public static boolean isSpecificDyeName(int oreid) {
        return isSpecificDyeName(OreDictionary.getOreName(oreid));
    }

    /**
     * Is the string a specific dye name ie. has the correct prefix
     */
    public static boolean isSpecificDyeName(String s) {
        if (s != null && !isGenericDyeName(s) && s.startsWith(SPECIFIC_DYE_ORE_PREFIX))
            return true;
        return false;
    }

    /**
     * Is the itemstack a dye
     * @param s - the itemstack to check
     * @param isGeneric - true is generic dye, false for a specific dye
     * @return true if a dye (generic or specific)
     */
    public static boolean isDye(ItemStack s, boolean isGeneric) {

        if (s != null) {

            int[] ids = OreDictionary.getOreIDs(s);
            for (int i : ids) {
                String name = OreDictionary.getOreName(i);
                if (isGeneric && isGenericDyeName(name))
                    return true;
                else if (!isGeneric && isSpecificDyeName(name))
                    return true;
            }
        }

        return false;
    }

    /**
     * Get the specific dye name for this item stack
     * Only returns the ore namme if ONE specific ore name is
     * tied to this item stack
     * @return "" or the ore name
     */
    public static String getSpecificDyeName(ItemStack s) {

        int m = 0;
        String match = "";
        if (s != null) {
            int[] ids = OreDictionary.getOreIDs(s);
            for (int i : ids) {
                if (isSpecificDyeName(i)) {
                    match = OreDictionary.getOreName(i);
                    m++;
                }
            }
        }

        if (m != 1)
            match = "";

        return match;
    }


    /**
     * Calculate which ore ids are valid for ALL the itemstacks.
     * So the common ore ids across all the itemstacks.
     * @param itemStacks the itemstacks to search
     * @return an array of the common ore ids
     */
    public static int[] getOreIdSet(ItemStack[] itemStacks) {

        if (itemStacks == null || itemStacks.length < 1)
            return new int[0];

        Set<Integer> set = new HashSet<Integer>();

        boolean valid = true;
        for (int i = 0 ; i < itemStacks.length && valid; i++) {

            int[] ids = OreDictionary.getOreIDs(itemStacks[i]);
            if (ids.length == 0) {
                valid = false;
            } else {
                if (set.isEmpty())
                    set.addAll(Ints.asList(ids));
                else
                    set.retainAll(Ints.asList(ids));
            }
        }

        if (!valid)
            set.clear();

        Integer[] tmp = set.toArray(new Integer[set.size()]);
        int[] ret = new int[tmp.length];
        for (int x = 0; x < tmp.length; x++)
            ret[x] = tmp[x];
        return ret;
    }

    /**
     * Convert the oreid array from getOreIdSet to a single name
     * @param ids the value return from getOreIdSet
     */
    public static String getCompressedOreName(int ids[]) {

        if (ids == null || ids.length < 1)
            return "";

        if (ids.length != 1)
            return "";

        return OreDictionary.getOreName(ids[0]);

    }
}
