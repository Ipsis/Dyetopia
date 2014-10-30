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

public class OreDictHelper {

    public static boolean isDye(ItemStack stack) {

        if (stack == null)
            return false;

        int[] ids = OreDictionary.getOreIDs(stack);
        if (ids.length != 0) {
            for (int id : ids) {
                /* dye is the generic dye ore name */
                String oreName = OreDictionary.getOreName(id);
                if (oreName.equals("dye") && oreName.startsWith("dye"))
                    return true;
            }
        }

        return false;
    }

    public static String[] getOreNames(ArrayList oreList) {

        if (oreList == null || oreList.size() <= 0)
            return new String[0];

        Set<Integer> oreMap = new HashSet<Integer>();

        boolean validOre = true;
        for (Object o : oreList) {

            if (!(o instanceof ItemStack))
                continue;

            int[] tempids = OreDictionary.getOreIDs((ItemStack)o);
            if (tempids.length == 0) {
                validOre = false;
                break;
            }

            if (oreMap.isEmpty())
                oreMap.addAll(Ints.asList(tempids));
            else
                oreMap.retainAll(Ints.asList(tempids));

            if (oreMap.isEmpty()) {
                validOre = false;
                break;
            }
        }

        if (!validOre)
            return new String[0];

        String[] names = new String[oreMap.size()];

        int x = 0;
        for (int i : oreMap)
            names[x++] = OreDictionary.getOreName(i);

        return names;
    }

    public static boolean isGenericDye(String[] oreNames) {

        if (oreNames == null || oreNames.length == 0)
            return false;

        for (String s : oreNames) {
            if (s.equals("dye"))
                return true;
        }

        return false;
    }

    public static boolean isSpecificDye(String[] oreNames) {

        if (oreNames == null || oreNames.length == 0)
            return false;


        for (String s : oreNames) {
            if (!s.equals("dye") && s.startsWith("dye"))
                return true;
        }

        return false;
    }

    public static int getDyeCount(String[] oreNames) {

        if (oreNames == null || oreNames.length == 0)
            return 0;

        int count = 0;
        for (String s : oreNames) {
            if (!s.equals("dye") && s.startsWith("dye"))
                count++;
        }

        return count;
    }

    public static String getDye(String[] oreNames) {

        if (oreNames == null || oreNames.length == 0)
            return "";

        if (getDyeCount(oreNames) > 1)
            return "";

        String name = "";
        for (String s : oreNames) {
            if (!s.equals("dye") && s.startsWith("dye")){
                name = s;
                break;
            }
        }

        return name;
    }

    public static boolean hasOreName(ItemStack itemStack, String name) {

        if (itemStack == null || name == null)
            return false;

        int[] ids = OreDictionary.getOreIDs(itemStack);
        if (ids.length != 0) {
            for (int id : ids) {
                if (OreDictionary.getOreName(id).equals(name))
                    return true;
            }
        }

        return false;
    }
}
