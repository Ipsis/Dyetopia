package ipsis.dyetopia.manager;

import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import ipsis.dyetopia.util.OreDictHelper;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

public class DyeLiquidManager {

    public static final int DYE_BASE_AMOUNT = DyeHelper.getLCM();

    private static final DyeLiquidManager instance = new DyeLiquidManager();

    /* This allows lookup of the dye recipe for any oredict dye */
    private HashMap<Integer, DyeRecipe> map;

    private DyeLiquidManager() {
        map = new HashMap<Integer, DyeRecipe>();
    }

    public static final DyeLiquidManager getInstance() { return instance; }

    public void initialize() {

        for (int i = 0; i < 16; i++) {
            DyeHelper.DyeType d = DyeHelper.DyeType.getDye(i);
            map.put(OreDictionary.getOreID(d.getOreDictName()), new DyeRecipe(d.getStack(), d.getRed(), d.getYellow(), d.getBlue(), d.getWhite()));
        }
    }

    public DyeRecipe getRecipe(ItemStack s) {

        if (!OreDictHelper.isDye(s, false))
            return null;

        /* This needs to find the correct oredict dye name to lookup */
        int[] ids = OreDictionary.getOreIDs(s);
        for (int id : ids) {
            if (map.containsKey(id))
                return map.get(id);
        }

        return null;
    }

}
