package ipsis.dyetopia.init;

import ipsis.dyetopia.reference.Names;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ModOreDict {

    public static void preInit() {

        for (int i = 0; i < Names.Items.ITEM_DYE_CHUNK_TYPES.length; i++) {
            OreDictionary.registerOre("dye" + Names.Items.ITEM_DYE_CHUNK_TYPES[i], new ItemStack(ModItems.itemDyeChunk, 1, i));
            OreDictionary.registerOre("dye", new ItemStack(ModItems.itemDyeChunk, 1, i));
        }

        for (int i = 0; i < 4; i++) {
            OreDictionary.registerOre("treeSapling", new ItemStack(ModBlocks.blockSaplingDye, 1, i));
            OreDictionary.registerOre("treeLeaves", new ItemStack(ModBlocks.blockLeavesDye, 1, i));
            OreDictionary.registerOre("logWood", new ItemStack(ModBlocks.blockLogDye, 1, i));
        }
    }

    public static void initialise() {

    }

    public static void postInit() {

    }
}
