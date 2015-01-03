package ipsis.dyetopia.manager.dyeableblocks;

import cofh.lib.inventory.ComparableItemStackSafe;
import cpw.mods.fml.common.registry.GameData;
import ipsis.dyetopia.handler.DyeFileHandler;
import ipsis.dyetopia.manager.IFactoryRecipe;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.ComparableItemStackBlockSafe;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DyeableBlocksManager {

    private static class OriginInfo {
        public ItemStack block; /* hashmap key */
        public ItemStack origin;

        public OriginInfo(ItemStack origin, ItemStack block) {
            this.origin = origin;
            this.block = block;
        }

        private OriginInfo() { }
    }

    /**
     * The maps are as follows:
     *
     * The origin map has:
     * key: colored itemstack
     * value: uncolored origin item
     *
     * The recipe map is an array of 16 hashmaps, one for each dye color.
     *
     * key: itemstack to color (may be an uncolored origin or a colored itemstack
     * value: recipe
     */
    private static HashMap<ComparableItemStackBlockSafe, OriginInfo> originMap = new HashMap<ComparableItemStackBlockSafe, OriginInfo>();
    private static ArrayList<HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe>> recipeMap = new ArrayList<HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe>>(16);
    static {
        for (int i = 0; i < 16; i++) {
            HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe> entry = new HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe>();
            recipeMap.add(i, entry);
        }
    }

    public static void initialise() {
        /* Nothing anymore */
    }

    public static void refreshMap() {

        /* Update the hashes */
        for (int i = 0; i < 16; i++) {
            HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe> map = new HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe>(recipeMap.get(i).size());
            for (Map.Entry entry : recipeMap.get(i).entrySet()) {
                DyedBlockRecipe r = (DyedBlockRecipe) entry.getValue();
                map.put(new ComparableItemStackBlockSafe(r.getInput()), r);
            }

            recipeMap.get(i).clear();
            recipeMap.set(i, map);
        }

        /* Update the origins */
        HashMap<ComparableItemStackBlockSafe, OriginInfo> map = new HashMap<ComparableItemStackBlockSafe, OriginInfo>();
        for (Map.Entry entry : originMap.entrySet()) {
            OriginInfo info = (OriginInfo)entry.getValue();
            map.put(new ComparableItemStackBlockSafe(info.block), info);
        }

        originMap.clear();
        originMap = map;
    }

    public static ArrayList<HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe>> getRecipes() {
        return recipeMap;
    }

    /**
     * Run only after all mods are loaded, or you cannot find their blocks/items
     */
    public static void postInit() {

        for (DyeableModInfo modInfo : DyeFileHandler.getInstance().cfgArray) {

            if (!modInfo.isLoaded()) {
                LogHelper.info("DyeableBlocksManager: skipping " + modInfo.modid + " not loaded");
                continue;
            }

            if (DyeFileHandler.getInstance().isModBlacklisted(modInfo.modid)) {
                LogHelper.info("DyeableBlocksManager: skipping " + modInfo.modid + " blacklisted");
                continue;
            }

            for (DyeableBlockDesc desc : modInfo.mappings) {

                if (!desc.isValid()) {
                    LogHelper.info("DyeableBlocksManager: invalid descriptor " + desc.refname);
                    continue;
                }

                if (desc.isBlockBlacklisted()) {
                    LogHelper.info("DyeableBlocksManager: skipping " + desc.refname + " target block blacklisted " + desc.blockName);
                    continue;
                }

                boolean originBlacklisted = (desc.hasOrigin() && desc.isOriginBlacklisted());

                switch (desc.type) {
                    case SIMPLE:
                        handleSimpleDesc(desc, originBlacklisted);
                        break;
                    case VANILLA:
                        handleVanillaDesc(desc, originBlacklisted);
                        break;
                    case FULL_META:
                        break;
                    case FULL_BLOCK:
                        handleFullBlockDesc(desc, originBlacklisted);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * Convert the name to an itemstack
     * Default meta of 0
     */
    private static ItemStack getItemStackFromRegistry(String name) {

        Block block = GameData.getBlockRegistry().getObject(name);
        if (block != null && block != Blocks.air)
            return new ItemStack(block);

        /* Not a block, get the item */
        Item item = GameData.getItemRegistry().getObject(name);
        if (item != null)
            return new ItemStack(item);

        return null;
    }

    /**
     * Simple colored blocks are mapped against the dye meta data
     * meta 0 = BLACK
     * meta 15 = WHITE
     */
    private static void handleSimpleDesc(DyeableBlockDesc desc, boolean originBlacklisted) {

        ItemStack outStack = getItemStackFromRegistry(desc.blockName);
        if (outStack == null)
            return;

        if (desc.hasOrigin()) {

            if (originBlacklisted) {
                LogHelper.info("DyeableBlocksManager: skipping " + desc.refname + " origin, blacklisted " + desc.blockName);
            } else {
                ItemStack originStack = getItemStackFromRegistry(desc.originName);
                if (originStack != null) {
                    originStack.setItemDamage(desc.originMeta);

                    for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
                        int meta = d.getDmg();
                        addEntry(originStack, d, new ItemStack(outStack.getItem(), 1, meta));
                        addOrigin(originStack, new ItemStack(outStack.getItem(), 1, meta));
                    }
                }
            }
        }

        for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
            for (DyeHelper.DyeType d2 : DyeHelper.DyeType.VALID_DYES) {
                if (d == d2)
                    continue;

                addEntry(new ItemStack(outStack.getItem(), 1, d.getDmg()), d2, new ItemStack(outStack.getItem(), 1, d2.getDmg()));
            }
        }
    }

    /**
     * Vanilla colored blocks are mapped
     * meta 0 = WHITE
     * meta 15 = BLACK
     */
    private static void handleVanillaDesc(DyeableBlockDesc desc, boolean originBlacklisted) {

        ItemStack outStack = getItemStackFromRegistry(desc.blockName);
        if (outStack == null)
            return;

        if (desc.hasOrigin()) {

            if (originBlacklisted) {
                LogHelper.info("DyeableBlocksManager: skipping " + desc.refname + " origin, blacklisted " + desc.blockName);
            } else {
                ItemStack originStack = getItemStackFromRegistry(desc.originName);
                if (originStack != null) {
                    originStack.setItemDamage(desc.originMeta);

                    for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
                        int meta = 15 - d.getDmg(); /* Vanilla white dye = 15 */
                        addEntry(originStack, d, new ItemStack(outStack.getItem(), 1, meta));
                        addOrigin(originStack, new ItemStack(outStack.getItem(), 1,  meta));
                    }
                }
            }
        }

        if (desc.associative) {

            for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
                for (DyeHelper.DyeType d2 : DyeHelper.DyeType.VALID_DYES) {
                    if (d == d2)
                        continue;

                    addEntry(new ItemStack(outStack.getItem(), 1, 15 - d.getDmg()), d2, new ItemStack(outStack.getItem(), 1, 15 - d2.getDmg()));
                }
            }
        }
    }

    private static void handleFullBlockDesc(DyeableBlockDesc desc, boolean originBlacklisted) {

        /* TODO handle blacklist */
        /* The block name is not needed here, as each block is fully described */
        if (desc.hasOrigin()) {
            if (originBlacklisted) {
                LogHelper.info("DyeableBlocksManager: skipping " + desc.refname + " origin, blacklisted " + desc.blockName);
            } else {
                ItemStack originStack = getItemStackFromRegistry(desc.originName);
                if (originStack != null) {
                    originStack.setItemDamage(desc.originMeta);

                    for (DyeHelper.DyeType dye : DyeHelper.DyeType.VALID_DYES) {

                        DyeableBlockDesc.BlockMapDesc blockDesc = desc.blockMap[dye.ordinal()];
                        if (blockDesc == null)
                            continue;

                        ItemStack outStack = getItemStackFromRegistry(blockDesc.name);
                        if (outStack != null) {
                            addEntry(originStack, dye, new ItemStack(outStack.getItem(), 1, blockDesc.meta));
                            addOrigin(originStack, new ItemStack(outStack.getItem(), 1, blockDesc.meta));
                        }
                    }
                }
            }
        }

        if (desc.associative) {

            for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {

                DyeableBlockDesc.BlockMapDesc inDesc = desc.getBlockMapDesc(d);
                if (inDesc == null)
                    continue;

                ItemStack inStack = getItemStackFromRegistry(inDesc.name);
                if (inStack == null)
                    continue;

                for (DyeHelper.DyeType d2 : DyeHelper.DyeType.VALID_DYES) {
                    DyeableBlockDesc.BlockMapDesc outDesc = desc.getBlockMapDesc(d2);
                    if (outDesc == null || d == d2)
                        continue;

                    ItemStack outStack = getItemStackFromRegistry(outDesc.name);
                    if (outStack != null) {
                        addEntry(new ItemStack(inStack.getItem(), 1, inDesc.meta), d2, new ItemStack(outStack.getItem(), 1, outDesc.meta));
                    }
                }
            }
        }
    }

    private static void addEntry(ItemStack source, DyeHelper.DyeType dye, ItemStack output) {

        ComparableItemStackBlockSafe key = new ComparableItemStackBlockSafe(source.copy());
        HashMap<ComparableItemStackBlockSafe, DyedBlockRecipe> rmap = recipeMap.get(dye.ordinal());

        if (!rmap.containsKey(key)) {
            rmap.put(key, new DyedBlockRecipe(source, dye, output));
        } else {
            LogHelper.warn("addEntry: DUPLICATE MAPPING");
        }
    }

    private static void addOrigin(ItemStack origin, ItemStack block) {
        
        ComparableItemStackBlockSafe key = new ComparableItemStackBlockSafe(block.copy());
        originMap.put(key, new OriginInfo(origin, block));
    }

    public static boolean hasOrigin(ItemStack itemStack) {

        return originMap.containsKey(new ComparableItemStackBlockSafe(itemStack));
    }

    public static ItemStack getOrigin(ItemStack itemStack) {

        if (!hasOrigin(itemStack))
            return null;

        return originMap.get(new ComparableItemStackBlockSafe(itemStack)).origin.copy();
    }

    public static class DyedBlockRecipe implements IFactoryRecipe {

        private ItemStack input; /* the itemstack to color */
        private ItemStack output; /* the colored itemstack */
        private DyeHelper.DyeType dye; /* the dye to color with */
        private int pureAmount; /* the amount of pure dye required */

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
