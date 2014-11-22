package ipsis.dyetopia.util;

import cofh.lib.inventory.ComparableItemStackSafe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OriginManager {

    private static HashMap<ComparableItemStackSafe, ItemStack> recipeMap = new HashMap();

    public static void initialise() {

        List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
        for (IRecipe irecipe : allrecipes) {

            if (irecipe.getRecipeOutput() == null)
                continue;

            /* We want a dye + something */
            if (irecipe.getRecipeSize() < 2)
                continue;

            /* No recipes that make dyes or food */
            if (OreDictHelper.isDye(irecipe.getRecipeOutput()) || irecipe.getRecipeOutput().getItem() instanceof ItemFood)
                continue;

            //if (!RecipeHelper.recipeUsesUniqueDye(irecipe))
            //    continue;

            RecipeHelper.CompressedRecipe cr = RecipeHelper.createCompressedRecipe(irecipe);

            if (cr != null)
                cr.dumpRecipe();

        }

    }

    private static void addOrigin(ItemStack colorIS, ItemStack uncoloredIS) {

        if (colorIS == null || uncoloredIS == null)
            return;

        ItemStack i1 = colorIS.copy();
        ItemStack i2 = uncoloredIS.copy();
        recipeMap.put(new ComparableItemStackSafe(i1), i2);
    }

    public static ItemStack getOrigin(ItemStack in) {

        return recipeMap.get(new ComparableItemStackSafe(in));
    }

    public static boolean hasOrigin(ItemStack in) {

        return recipeMap.containsKey(new ComparableItemStackSafe(in));
    }

    public static void debugDumpMap() {

        Iterator iter = recipeMap.entrySet().iterator();
        while (iter.hasNext()) {

            Map.Entry pairs = (Map.Entry)iter.next();
            ComparableItemStackSafe in = (ComparableItemStackSafe)pairs.getKey();
            ItemStack out = (ItemStack)pairs.getValue();
            LogHelper.info("[BlockOriginHelper]: " +
                    in.toItemStack() + "->" + out);
        }
    }


}
