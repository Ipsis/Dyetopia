package ipsis.dyetopia.manager.dyeableblocks;

import ipsis.dyetopia.handler.DyeFileHandler;
import ipsis.dyetopia.manager.IFactoryRecipe;
import ipsis.dyetopia.manager.dyeableblocks.config.*;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.ComparableItemStackBlockSafe;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import ipsis.dyetopia.util.RegistryHelper;
import net.minecraft.block.Block;
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

    public static void postInit() {
        loadDyeableBlocksConfig();
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

        ItemStack testStack;
        Block b = Block.getBlockFromItem(source.getItem());
        if (b != null) {
            testStack = new ItemStack(b, 1, source.getItem().getMetadata(source.getItemDamage()));
        } else {
            testStack = source.copy();
        }

        for (int i = 0; i < 16; i++) {
            if (recipeMap.get(i).containsKey(new ComparableItemStackBlockSafe(testStack)))
                return true;
        }

        return false;
    }

    /*****************************************
     * Config blocks
     */

    /**
     * Run only after all mods are loaded, or you cannot find their blocks/items
     */
    public static void loadDyeableBlocksConfig() {

        /* Blacklisted mods are already removed */
        for (ModInfo modInfo : DyeableBlocksConfigManager.getInstance().cfgMap.values()) {

            if (!modInfo.isLoaded()) {
                LogHelper.info("DyeableBlocksManager: skipping " + modInfo.getModid() + " not loaded");
                continue;
            }

            for (BlockDescBase desc : modInfo.getMappings()) {

                if (!desc.isValid()) {
                    LogHelper.info("DyeableblocksManager: skipping invalid " + desc.refname);
                    continue;
                }

                if (desc instanceof BlockDescFull)
                    processFull((BlockDescFull)desc);
                else if (desc instanceof BlockDescAttrMap)
                    processAttrMap((BlockDescAttrMap)desc);
                else if (desc instanceof BlockDescSimple)
                    processSimple((BlockDescSimple)desc);
            }
        }
    }

    private static void processFull(BlockDescFull desc) {

        if (desc.hasOrigin()) {
            ItemStack source = RegistryHelper.getItemStackFromRegistryAttr(desc.getOrigin().getName(), desc.getOrigin().getAttr());
            if (source != null) {
                for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {

                    ModObjectDesc.ModObjectDyedDesc resultDesc = desc.getEntry(d);
                    if (resultDesc == null)
                        continue;

                    ItemStack result = RegistryHelper.getItemStackFromRegistryAttr(resultDesc.getName(), resultDesc.getAttr());
                    if (result == null)
                        continue;

                    addEntry(source, d, result);
                    addOrigin(source, result);
                }
            }
        }

        if (desc.isAssociative()) {

            for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {

                ModObjectDesc.ModObjectDyedDesc sourceDesc = desc.getEntry(d);
                if (sourceDesc == null)
                    continue;

                ItemStack source = RegistryHelper.getItemStackFromRegistryAttr(sourceDesc.getName(), sourceDesc.getAttr());
                if (source == null)
                    continue;

                for (DyeHelper.DyeType d2 : DyeHelper.DyeType.VALID_DYES) {
                    if (d == d2)
                        continue;

                    ModObjectDesc.ModObjectDyedDesc resultDesc = desc.getEntry(d2);
                    if (resultDesc == null)
                        continue;

                    ItemStack result = RegistryHelper.getItemStackFromRegistryAttr(resultDesc.getName(), resultDesc.getAttr());
                    if (result == null)
                        continue;

                    addEntry(new ItemStack(source.getItem(), 1, sourceDesc.getAttr()), d2, result);

                }
            }
        }
    }

    private static void processAttrMap(BlockDescAttrMap desc) {

        ItemStack result = RegistryHelper.getItemStackFromRegistry(desc.getBaseName());
        if (result == null)
            return;

        if (desc.hasOrigin()) {

            ItemStack source = RegistryHelper.getItemStackFromRegistryAttr(desc.getOrigin().getName(), desc.getOrigin().getAttr());
            if (source != null) {
                for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {

                    int meta = desc.getAttrMapEntry(d);
                    addEntry(source, d, new ItemStack(result.getItem(), 1, meta));
                    addOrigin(source, new ItemStack(result.getItem(), 1, meta));
                }
            }
        }

        if (desc.isAssociative()) {

            for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
                for (DyeHelper.DyeType d2 : DyeHelper.DyeType.VALID_DYES) {
                    if (d == d2)
                        continue;

                    int sourcemeta = desc.getAttrMapEntry(d);
                    int resultmeta = desc.getAttrMapEntry(d2);

                    addEntry(new ItemStack(result.getItem(), 1, sourcemeta), d2, new ItemStack(result.getItem(), 1, resultmeta));
                }
            }
        }
    }

    private static void processSimple(BlockDescSimple desc) {

        ItemStack result = RegistryHelper.getItemStackFromRegistry(desc.getBaseName());
        if (result == null)
            return;

        if (desc.hasOrigin()) {

            ItemStack source = RegistryHelper.getItemStackFromRegistryAttr(desc.getOrigin().getName(), desc.getOrigin().getAttr());
            if (source != null) {
                for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {

                    int meta = desc.getColorAttr(d);
                    addEntry(source, d, new ItemStack(result.getItem(), 1, meta));
                    addOrigin(source, new ItemStack(result.getItem(), 1, meta));
                }
            }
        }

        if (desc.isAssociative()) {

            for (DyeHelper.DyeType d : DyeHelper.DyeType.VALID_DYES) {
                for (DyeHelper.DyeType d2 : DyeHelper.DyeType.VALID_DYES) {
                    if (d == d2)
                        continue;

                    int sourcemeta = desc.getColorAttr(d);
                    int resultmeta = desc.getColorAttr(d2);

                    addEntry(new ItemStack(result.getItem(), 1, sourcemeta), d2, new ItemStack(result.getItem(), 1, resultmeta));
                }
            }
        }
    }
}
