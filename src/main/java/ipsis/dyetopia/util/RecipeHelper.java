package ipsis.dyetopia.util;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeHelper {

    public static class CompressedRecipe {

        private static class OreDictInfo {
            private String name;
            private int count;

            private OreDictInfo() { }

            public OreDictInfo(String name) {
                this.name = name;
                this.count = 1;
            }

            public void incCount() {
                this.count++;
            }
        }

        private ArrayList<ItemStack> stackInput;
        private ArrayList<OreDictInfo> oreDictInput;
        private ItemStack output;

        private CompressedRecipe() { }
        public CompressedRecipe(ItemStack out) {
            output = out.copy();
            output.stackSize = 1;
            stackInput = new ArrayList<ItemStack>();
            oreDictInput = new ArrayList<OreDictInfo>();
        }

        public void addToRecipe(ItemStack is) {

            boolean added = false;
            for (int i = 0 ; i < stackInput.size() && !added; i++) {

                ItemStack currStack = stackInput.get(i);
                if (currStack.isItemEqual(is) && ItemStack.areItemStackTagsEqual(is, currStack)) {
                    currStack.stackSize++;
                    added = true;
                }
            }

            if (!added) {
                ItemStack s = is.copy();
                s.stackSize = 1;
                stackInput.add(s);
            }
        }

        public void addToRecipe(String oreDictName) {

            boolean added = false;
            for (int i = 0 ; i < oreDictInput.size() && !added; i++) {

                OreDictInfo curr = oreDictInput.get(i);
                if (oreDictName.equals(curr.name)) {
                    curr.incCount();
                    added = true;
                }
            }

            if (!added)
                oreDictInput.add(new OreDictInfo(oreDictName));
        }

        public int getNumDyes() {

            int count = 0;

            for (ItemStack s : stackInput)
                if (OreDictHelper.isDye(s))
                    count++;

            for (OreDictInfo s : oreDictInput)
                if (OreDictHelper.isADye(s.name))
                    count++;

            return count;
        }

        public void dumpRecipe() {

            StringBuilder sb = new StringBuilder();
            sb.append(stackToString(output));
            sb.append("<->");

            for (ItemStack i : stackInput) {
                sb.append(stackToString(i));
                sb.append(" ");
            }

            for (OreDictInfo i : oreDictInput)
                sb.append(i.count + "x[ORE:" + i.name + "] ");

            LogHelper.error(sb.toString());
        }

        private static String stackToString(ItemStack s) {
            if (s == null) return "";

            StringBuilder sb = new StringBuilder();
            sb.append(s.stackSize + "x[" + s.getUnlocalizedName() + "]");
            if (s.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                sb.append(":(*)");
            else
                sb.append(":(" + s.getItemDamage() + ")");

            if (OreDictHelper.isDye(s))
                sb.append("[DYE]");

            return sb.toString();
        }
    }

    public static CompressedRecipe createCompressedRecipe(IRecipe r) {

        CompressedRecipe cr = new CompressedRecipe(r.getRecipeOutput());
        if (r instanceof ShapelessRecipes) {
            for (Object object : ((ShapelessRecipes)r).recipeItems) {
                if (object instanceof ItemStack)
                    cr.addToRecipe((ItemStack)object);
            }
        } else if (r instanceof ShapedRecipes) {
            for (ItemStack s : ((ShapedRecipes) r).recipeItems) {
                if (s != null) // slot in recipe can be empty
                    cr.addToRecipe(s);
            }
        } else if (r instanceof ShapelessOreRecipe) {
            for (Object object : ((ShapelessOreRecipe) r).getInput()) {
                if (object instanceof ItemStack) {
                    cr.addToRecipe((ItemStack) object);
                } else if (object instanceof ArrayList) {
                    String name = OreDictHelper.getUniqueOreName((ArrayList) object);
                    if (!name.equals(""))
                        cr.addToRecipe(name);
                }
            }
        } else if (r instanceof ShapedOreRecipe) {
            for (Object object : ((ShapedOreRecipe) r).getInput()) {
                if (object instanceof ItemStack) {
                    cr.addToRecipe((ItemStack) object);
                } else if (object instanceof ArrayList) {
                    String name = OreDictHelper.getUniqueOreName((ArrayList) object);
                    if (!name.equals(""))
                        cr.addToRecipe(name);
                }
            }
        } else {
            cr = null;
        }

        return cr;
    }

    public static boolean recipeUsesUniqueDye(IRecipe r) {

        int count = 0;
        if (r instanceof ShapelessRecipes) {
            for (Object object : ((ShapelessRecipes)r).recipeItems) {
                if (object instanceof ItemStack) {
                    if (OreDictHelper.isDye((ItemStack)object))
                        count++;
                }
            }
        } else if (r instanceof ShapedRecipes) {
            for (ItemStack s : ((ShapedRecipes)r).recipeItems) {
                if (OreDictHelper.isDye(s))
                    count++;
            }
        } else if (r instanceof ShapelessOreRecipe) {

            for (Object object : ((ShapelessOreRecipe)r).getInput()) {

                if (object instanceof ItemStack) {
                    if (OreDictHelper.isDye((ItemStack)object))
                        count++;
                } else if (object instanceof ArrayList) {

                    String[] names = OreDictHelper.getOreNames((ArrayList)object);
                    String s = OreDictHelper.getDye(names);
                }
            }

        }

        return count != 0;
    }
}
