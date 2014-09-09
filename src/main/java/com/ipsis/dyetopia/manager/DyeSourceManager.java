package com.ipsis.dyetopia.manager;

import cofh.lib.inventory.ComparableItemStack;
import com.ipsis.dyetopia.util.OreDictHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.HashMap;
import java.util.List;

public class DyeSourceManager {

    private static final DyeSourceManager instance = new DyeSourceManager();

    private HashMap<ComparableItemStack, ItemStack> map;

    private DyeSourceManager() {
        map = new HashMap<ComparableItemStack, ItemStack>();
    }

    public static final DyeSourceManager getInstance() { return instance; }

    public void initialize() {

		/* Shapeless recipes */
        List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
        for (IRecipe irecipe : allrecipes) {
            if (irecipe instanceof ShapelessRecipes) {

				/*
				 * Only add shapeless recipes where single item creates a dye
				 * (can be 1 or more of the same dye)
				 */
                ShapelessRecipes r = (ShapelessRecipes) irecipe;
                if (r.getRecipeSize() == 1) {
                    ItemStack out = irecipe.getRecipeOutput();
                    ItemStack in = (ItemStack) (r.recipeItems.get(0));
                    addRecipe(in, out);
                }
            }
        }

//        /* dont like this but dont know of a better way! */
//        Map allsmelting = FurnaceRecipes.smelting().getSmeltingList();
//        Iterator i = allsmelting.entrySet().iterator();
//        while (i.hasNext()) {
//            Map.Entry pairs = (Map.Entry) i.next();
//
//            /**
//             * If the source item is actually a block eg. cactus
//             * then the damage value may not be correct.
//             * We therefore create a new stack using the item for the block.
//             * We may be using the damage value as part of the hashmap, so this
//             * makes sure a lookup will work.
//             */
//
//            ItemStack in = ((ItemStack)pairs.getKey());
//            ItemStack cleanItem = new ItemStack(in.getItem());
//            ItemStack out = ((ItemStack)pairs.getValue()).copy();
//            addRecipe(cleanItem, out);
//        }

        /* TODO handle an item->item->dye such as Botania flowers ?? */
    }

    private void addRecipe(ItemStack in, ItemStack out) {

        if (OreDictHelper.isDye(out)) {
            map.put(new ComparableItemStack(in), out);
        }
    }

    public boolean isSource(ItemStack itemStack) {

        if (itemStack == null)
            return false;

        return map.containsKey(new ComparableItemStack(itemStack));
    }

    public ItemStack getDye(ItemStack itemStack) {

        if (itemStack == null)
            return null;

        return map.get(new ComparableItemStack(itemStack));
    }
}
