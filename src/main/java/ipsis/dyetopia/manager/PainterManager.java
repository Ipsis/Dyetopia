
package ipsis.dyetopia.manager;


import cofh.lib.inventory.ComparableItemStackSafe;
import ipsis.dyetopia.util.*;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.*;

public class PainterManager {

    private static HashMap<ComparableItemStackSafe, PainterRecipe[]>  recipeMap = new HashMap();

    public static void initialise() {

        List<IRecipe> allrecipes = CraftingManager.getInstance().getRecipeList();
        for (IRecipe irecipe : allrecipes) {

            if (irecipe.getRecipeOutput() == null)
                continue;

            /* We want a dye + something */
            if (irecipe.getRecipeSize() <= 1)
                continue;

            /* No recipes that make dyes or food */
            if (OreDictHelper.isDye(irecipe.getRecipeOutput()) || irecipe.getRecipeOutput().getItem() instanceof ItemFood)
                continue;

            if (irecipe instanceof ShapelessRecipes || irecipe instanceof ShapedRecipes) {
                /* Easy */

                PaintValidation v;
                ItemStack output;
                if (irecipe instanceof ShapelessRecipes) {
                    v = new PaintValidation((ShapelessRecipes) irecipe);
                    output = ((ShapelessRecipes) irecipe).getRecipeOutput().copy();
                } else {
                    v = new PaintValidation((ShapedRecipes) irecipe);
                    output = ((ShapedRecipes) irecipe).getRecipeOutput().copy();
                }

                v.processRecipe();
                if (!v.getValid())
                    continue;

                LogHelper.info("Valid: " + v);

                ItemStack inputDye = v.getRecipeDye();
                ItemStack inputItem = v.getRecipeItem();

                if (inputDye != null && inputItem != null) {
                    addRecipe(inputItem,
                            DyeHelper.DyeType.getDye(inputDye),
                            output, v.getDyeAmount());

                    OriginHelper.addRecipe(output.copy(), inputItem);
                }

            } else if (irecipe instanceof ShapelessOreRecipe || irecipe instanceof ShapedOreRecipe) {
                /* Hard */

                PaintValidation v;
                ItemStack output;
                if (irecipe instanceof ShapelessOreRecipe) {
                    v = new PaintValidation((ShapelessOreRecipe) irecipe);
                    output = ((ShapelessOreRecipe) irecipe).getRecipeOutput().copy();
                } else {
                    v = new PaintValidation((ShapedOreRecipe) irecipe);
                    output = ((ShapedOreRecipe) irecipe).getRecipeOutput().copy();
                }

                v.processRecipe();
                if (!v.getValid())
                    continue;

                LogHelper.info("Valid: " + v);

                ItemStack inputDye = v.getRecipeDye();
                ItemStack inputItem = v.getRecipeItem();
                ItemStack inputItemList[] = v.getRecipeItemList();

                if (inputDye != null && inputItem != null && inputItemList.length > 0)
                    continue;

                if (inputDye != null) {

                    if (inputItem != null) {

                        addRecipe(inputItem,
                                DyeHelper.DyeType.getDye(inputDye),
                                output, v.getDyeAmount());

                        OriginHelper.addRecipe(output.copy(), inputItem);
                    } else {

                        for (ItemStack itemStack : inputItemList) {

                            addRecipe(itemStack,
                                    DyeHelper.DyeType.getDye(inputDye),
                                    output, v.getDyeAmount());
                        }

                        if (inputItemList.length == 1)
                            OriginHelper.addRecipe(output.copy(), inputItemList[0]);
                    }
                }
            }
        }

        debugDumpMap();
        OriginHelper.debugDumpMap();
    }

    public static void debugDumpMap() {

        Iterator iter = recipeMap.entrySet().iterator();
        while (iter.hasNext()) {

            Map.Entry pairs = (Map.Entry)iter.next();
            ComparableItemStackSafe output = (ComparableItemStackSafe)pairs.getKey();
            PainterRecipe[] rList = (PainterRecipe[])pairs.getValue();
            LogHelper.info("[PainterManager]: " + output.item.getUnlocalizedName());
            if (rList != null) {
                int x = 0;
                for (PainterRecipe r : rList) {
                    if (r != null)
                        LogHelper.info("[PainterManager]: " + x + "->" + r);
                    x++;
                }
            }
        }
    }

    public static void addRecipe(ItemStack in, DyeHelper.DyeType dye, ItemStack out, int pureAmount) {

        if (in == null || out == null || pureAmount < 0)
            return;

        PainterRecipe[] rList;
        if (!recipeMap.containsKey(new ComparableItemStackSafe(in))) {
            rList = new PainterRecipe[DyeHelper.DyeType.VALID_DYES.length];
            recipeMap.put(new ComparableItemStackSafe(in), rList);
        } else {
            rList = recipeMap.get(new ComparableItemStackSafe(in));
        }

        rList[dye.ordinal()] = new PainterRecipe(in, dye, out, pureAmount);
    }

    public static boolean hasRecipe(ItemStack in, DyeHelper.DyeType dye) {

        if (in == null)
            return false;

        if (!recipeMap.containsKey(new ComparableItemStackSafe(in)))
            return false;

        PainterRecipe[] rList = recipeMap.get(new ComparableItemStackSafe(in));
        if (rList == null || rList[dye.ordinal()] == null)
            return false;

        return true;
    }

    public static PainterRecipe getRecipe(ItemStack in, DyeHelper.DyeType dye) {

        if (in == null)
            return null;

        if (!recipeMap.containsKey(new ComparableItemStackSafe(in)))
            return null;

        PainterRecipe[] rList = recipeMap.get(new ComparableItemStackSafe(in));
        if (rList == null || rList[dye.ordinal()] == null)
            return null;

        return rList[dye.ordinal()];
    }

    public static class PainterRecipe {

        private ItemStack in;
        private DyeHelper.DyeType dye;
        private ItemStack out;
        private int pureAmount;

        private PainterRecipe() { }

        public PainterRecipe(ItemStack in, DyeHelper.DyeType dye, ItemStack out, int pureAmount) {

            this.in = in;
            this.dye = dye;
            this.out = out;
            this.pureAmount = pureAmount;
        }

        public int getPureAmount() { return this.pureAmount; }
        public ItemStack getInput() { return this.in.copy(); }
        public DyeHelper.DyeType getDye() { return this.dye; }
        public ItemStack getOutput() { return this.out.copy(); }

        @Override
        public String toString() {

            return in + " + " + dye + " + " + pureAmount + "->" + out;
        }
    }

 }
