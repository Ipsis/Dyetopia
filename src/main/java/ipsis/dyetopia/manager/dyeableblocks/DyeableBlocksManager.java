package ipsis.dyetopia.manager.dyeableblocks;


import cofh.lib.inventory.ComparableItemStackSafe;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DyeableBlocksManager {

    private static HashMap<ComparableItemStackSafe, DyedBlock[]> dyedBlockMap = new HashMap<ComparableItemStackSafe, DyedBlock[]>();

    public static void initialise() {

        /* hardcoded mapping for now */

        /* Clay */
        for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES)
            addEntry(new ItemStack(Blocks.hardened_clay), d, new ItemStack(Blocks.stained_hardened_clay, 1, d.getDmg()));


        for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
            for (DyeHelper.DyeType d2 : DyeHelper.DyeType.VALID_DYES) {
                if (d == d2)
                    continue;

                addEntry(new ItemStack(Blocks.stained_hardened_clay, 1, d.getDmg()), d2, new ItemStack(Blocks.stained_hardened_clay, 1, d2.getDmg()));
                addEntry(new ItemStack(Blocks.wool, 1, d.getDmg()), d2, new ItemStack(Blocks.wool, 1, d2.getDmg()));
                addEntry(new ItemStack(Blocks.stained_glass, 1, d.getDmg()), d2, new ItemStack(Blocks.stained_glass, 1, d2.getDmg()));
                addEntry(new ItemStack(Blocks.stained_glass_pane, 1, d.getDmg()), d2, new ItemStack(Blocks.stained_glass_pane, 1, d2.getDmg()));
                addEntry(new ItemStack(Blocks.carpet, 1, d.getDmg()), d2, new ItemStack(Blocks.carpet, 1, d2.getDmg()));
            }
        }
    }

    private static void addEntry(ItemStack source, DyeHelper.DyeType dye, ItemStack output) {

        ComparableItemStackSafe cmp = new ComparableItemStackSafe(source.copy());

        DyedBlock[] map;
        if (dyedBlockMap.containsKey(cmp)) {
            map = dyedBlockMap.get(cmp);
        } else {
            map = new DyedBlock[16];
            dyedBlockMap.put(cmp, map);
        }

        map[dye.getDmg()] = new DyedBlock(output);
    }

    private static class DyedBlock {

        private ItemStack output;

        public DyedBlock(ItemStack output) {
            this.output = output.copy();
        }

        public ItemStack getOutput() {
            return this.output;
        }
    }


    public static ItemStack getDyed(ItemStack source, DyeHelper.DyeType dye) {

        if (source == null || dye == DyeHelper.DyeType.INVALID)
            return null;

        DyedBlock[] map = dyedBlockMap.get(new ComparableItemStackSafe(source));
        if (map == null)
            return null;

        if (map[dye.getDmg()] != null)
            return map[dye.getDmg()].getOutput();

        return null;
    }

    public static boolean canDye(ItemStack source, DyeHelper.DyeType dye) {

        return getDyed(source, dye) != null;
    }
}
