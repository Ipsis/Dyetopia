package ipsis.dyetopia.util;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RegistryHelper {
    /**
     * Convert the name to an itemstack
     * Default meta of 0
     */
    public static ItemStack getItemStackFromRegistry(String name) {

        Block block = GameData.getBlockRegistry().getObject(name);
        if (block != null && block != Blocks.air)
            return new ItemStack(block);

        /* Not a block, get the item */
        Item item = GameData.getItemRegistry().getObject(name);
        if (item != null)
            return new ItemStack(item);

        return null;
    }

    public static ItemStack getItemStackFromRegistryAttr(String name, int attr) {

        ItemStack itemStack = getItemStackFromRegistry(name);
        if (itemStack != null)
            itemStack.setItemDamage(attr);

        return itemStack;
    }
}
