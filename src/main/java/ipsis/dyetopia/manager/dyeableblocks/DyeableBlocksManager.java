package ipsis.dyetopia.manager.dyeableblocks;

import ipsis.dyetopia.manager.IFactoryRecipe;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.ComparableItemStackBlockSafe;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class DyeableBlocksManager {

    private static ArrayList<HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe>> recipeMap = new ArrayList<HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe>>(16);
    static {
        for (int i = 0; i < 16; i++) {
            HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe> entry = new HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe>();
            recipeMap.add(i, entry);
        }
    }

    public static void initialise() {

        /* hardcoded mapping for now */

        /**
         * Vanilla dyes are ordered dmg 0 = black, dmg 15 = white
         * Colored carpet, wool etc is ordered dmg 0 = white, dmg 15 = black
         */

        /* Clay, glass, panes  */
        for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
            addEntry(new ItemStack(Blocks.hardened_clay), d, new ItemStack(Blocks.stained_hardened_clay, 1, 15 - d.getDmg()));
            addEntry(new ItemStack(Blocks.glass), d, new ItemStack(Blocks.stained_glass, 1, 15 - d.getDmg()));
            addEntry(new ItemStack(Blocks.glass_pane), d, new ItemStack(Blocks.stained_glass_pane, 1, 15 - d.getDmg()));
        }

        for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
            for (DyeHelper.DyeType d2 : DyeHelper.DyeType.VALID_DYES) {
                if (d == d2)
                    continue;

                addEntry(new ItemStack(Blocks.stained_hardened_clay, 1, 15 - d.getDmg()), d2, new ItemStack(Blocks.stained_hardened_clay, 1, 15 - d2.getDmg()));
                addEntry(new ItemStack(Blocks.wool, 1, 15 - d.getDmg()), d2, new ItemStack(Blocks.wool, 1, 15 - d2.getDmg()));
                addEntry(new ItemStack(Blocks.stained_glass, 1, 15 - d.getDmg()), d2, new ItemStack(Blocks.stained_glass, 1, 15 - d2.getDmg()));
                addEntry(new ItemStack(Blocks.stained_glass_pane, 1, 15 - d.getDmg()), d2, new ItemStack(Blocks.stained_glass_pane, 1, 15 - d2.getDmg()));
                addEntry(new ItemStack(Blocks.carpet, 1, 15 - d.getDmg()), d2, new ItemStack(Blocks.carpet, 1,15 - d2.getDmg()));
            }
        }
    }

    private static void addEntry(ItemStack source, DyeHelper.DyeType dye, ItemStack output) {

        ComparableItemStackBlockSafe key = new ComparableItemStackBlockSafe(source.copy());
        HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe> rmap = recipeMap.get(dye.ordinal());
        if (!rmap.containsKey(key)) {
            rmap.put(key, new DyedBlockRecipe(source, dye, output));
        } else {
            LogHelper.warn("addEntry: duplicate mapping " + source + " " + dye + " " + output);
        }
    }

    public static class DyedBlockRecipe implements IFactoryRecipe {

        private ItemStack input;
        private ItemStack output;
        private DyeHelper.DyeType dye;
        private int pureAmount;

        public DyedBlockRecipe(ItemStack input, DyeHelper.DyeType dye, ItemStack output, int pureAmount) {
            this.input = input.copy();
            this.dye = dye;
            this.output = output.copy();
            this.pureAmount = pureAmount;
        }

        public DyedBlockRecipe(ItemStack input, DyeHelper.DyeType dye, ItemStack output) {
            this(input, dye, output, DyeHelper.getLCM());
        }

        @Override
        public String toString() {
            return input + " " + dye + " -> " + output;
        }

        public ItemStack getInput() { return input.copy(); }
        public ItemStack getOutput() { return output.copy(); }
        public int getPureAmount() { return pureAmount; }
        public DyeHelper.DyeType getDye() { return dye; }

        /**
         * IFactoryRecipe
         */
        @Override
        public int getEnergy() {
            return Settings.Machines.painterRfRecipe;
        }
    }

    public static DyedBlockRecipe getDyedBlock(ItemStack source, DyeHelper.DyeType dye) {

        if (source == null)
            return null;

        ComparableItemStackBlockSafe key = new ComparableItemStackBlockSafe(source);
        HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe> rmap = recipeMap.get(dye.ordinal());

        return rmap.get(key);
    }

    public static boolean canDyeBlock(ItemStack source, DyeHelper.DyeType dye) {

        return getDyedBlock(source, dye) != null;
    }

    public static boolean canDyeBlock(ItemStack source) {

        for (int i = 0; i < 16; i++) {
            if (recipeMap.get(i).containsKey(new ComparableItemStackBlockSafe(source)))
                return true;
        }

        return false;
    }
}
