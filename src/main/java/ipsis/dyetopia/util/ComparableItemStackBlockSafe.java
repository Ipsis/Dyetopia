package ipsis.dyetopia.util;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * The CoFH ComparableItemStackSafe uses the ore dictionary for
 * entries that start with "block" (along with others).
 * Currently I need to treat those "block" ones as not comparable
 */
public class ComparableItemStackBlockSafe extends ComparableItemStack {

    static final String ORE = "ore";
    static final String DUST = "dust";
    static final String INGOT = "ingot";
    static final String NUGGET = "nugget";

    public static boolean safeOreType(String oreName) {

        return oreName.startsWith(ORE) || oreName.startsWith(DUST) || oreName.startsWith(INGOT) || oreName.startsWith(NUGGET);
    }

    public static int getOreID(ItemStack stack) {

        int id = ItemHelper.oreProxy.getOreID(stack);
        return safeOreType(ItemHelper.oreProxy.getOreName(id)) ? id : -1;
    }

    public static int getOreID(String oreName) {

        return safeOreType(oreName) ? ItemHelper.oreProxy.getOreID(oreName) : -1;
    }

    public ComparableItemStackBlockSafe(ItemStack stack) {

        super(stack);
        oreID = getOreID(stack);
    }

    public ComparableItemStackBlockSafe(Item item, int damage, int stackSize) {

        super(item, damage, stackSize);
        this.oreID = getOreID(this.toItemStack());
    }

    @Override
    public ComparableItemStackBlockSafe set(ItemStack stack) {

        super.set(stack);
        oreID = getOreID(stack);

        return this;
    }
}
