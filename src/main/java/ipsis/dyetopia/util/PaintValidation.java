package ipsis.dyetopia.util;

import com.google.common.base.Objects;
import ipsis.dyetopia.manager.DyeLiquidManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.*;

public class PaintValidation {

    /** NEVER change the recipe contents as it is pointing at the REAL recipes */
    private Object recipe;
    private RecipeType recipeType;

    private PaintValidation() {
        this.valid = false;

        this.singleDye = false;
        this.recipeDye = null;
        this.recipeDyeCount = 0;

        this.singleItem = false;
        this.recipeItem = null;
        this.recipeItemCount = 0;

        this.singleOreDictDye = false;
        this.recipeOreDictDyeName = "";
        this.recipeOreDictDyeCount = 0;

        this.singleOreDictItem = false;
        this.recipeOreDictItemCount = 0;

        this.recipeOreDictItemNames = new ArrayList<String>();
    }

    public ItemStack getRecipeDye() {

        if (!this.valid)
            return null;

        if (singleDye && recipeDye != null)
            return recipeDye.copy();

        if (singleOreDictDye && !recipeOreDictDyeName.equals("")) {
            ItemStack t = DyeHelper.DyeType.getStack(recipeOreDictDyeName);
            if (t != null)
                return t.copy();
        }

        return null;
    }

    public int getDyeAmount() {

        if (!this.valid)
            return 0;

        int numDye = 0;
        if (recipeDye != null)
            numDye = recipeDyeCount;
        else
            numDye = recipeOreDictDyeCount;

        if (numDye == 0)
            return 0;

        return DyeLiquidManager.DYE_BASE_AMOUNT / numDye;
    }

    public ItemStack getRecipeItem() {

        if (!this.valid)
            return null;

        if (singleItem && recipeItem != null)
            return recipeItem.copy();

        return null;
    }

    public ItemStack[] getRecipeItemList() {

        if (!this.valid)
            return new ItemStack[0];

        if (singleOreDictItem && !recipeOreDictItemNames.isEmpty()) {

            Set<ItemStack> itemSet = new HashSet<ItemStack>();
            for (String name : recipeOreDictItemNames)
                itemSet.addAll(OreDictionary.getOres(name));


            return itemSet.toArray(new ItemStack[0]);
        }

        return new ItemStack[0];
    }

    public PaintValidation(ShapelessRecipes recipe) {

        this();
        this.recipe = recipe;
        this.recipeType = RecipeType.SHAPELESS_VANILLA;
    }

    public PaintValidation(ShapedRecipes recipe) {

        this();
        this.recipe = recipe;
        this.recipeType = RecipeType.SHAPED_VANILLA;
    }

    public PaintValidation(ShapelessOreRecipe recipe) {

        this();
        this.recipe = recipe;
        this.recipeType = RecipeType.SHAPELESS_OREDICT;
    }

    public PaintValidation(ShapedOreRecipe recipe) {

        this();
        this.recipe = recipe;
        this.recipeType = RecipeType.SHAPED_OREDICT;
    }


    private boolean valid;
    public boolean getValid() { return this.valid; }

    public void processRecipe() {

        switch (this.recipeType) {
            case SHAPELESS_VANILLA:
                processShapelessVanilla();
                break;
            case SHAPED_VANILLA:
                processShapedVanilla();
                break;
            case SHAPELESS_OREDICT:
                processShapelessOreDict();
                break;
            case SHAPED_OREDICT:
                processShapedOreDict();
                break;
            default:
                break;
        }
    }

    /**
     * Check if this is a new dye or one we have seen before
     * @return true to continue parsing, false to stop
     */
    private boolean singleDye;
    private ItemStack recipeDye;
    private int recipeDyeCount;
    private boolean checkDye(ItemStack itemStack) {

        if (itemStack == null)
            return true;

        if (!OreDictHelper.isDye(itemStack))
            return true;

        if (recipeDye == null) {
            recipeDye = itemStack.copy();
            recipeDye.stackSize = 1;
            recipeDyeCount = 1;
            singleDye = true;
        } else {
            if (recipeDye.isItemEqual(itemStack)) {
                recipeDyeCount++;
            } else {
                /* Recipe has more than one dye */
                singleDye = false;
                return false;
            }
        }

        return true;
    }

    private boolean singleOreDictDye;
    private String recipeOreDictDyeName;
    private int recipeOreDictDyeCount;
    private boolean checkDye(ArrayList oreList) {

        if (oreList == null || oreList.size() < 1)
            return true;

        if (!OreDictHelper.isDye((ItemStack)oreList.get(0)))
            return true;

        String[] names = OreDictHelper.getOreNames(oreList);
        String s = OreDictHelper.getDye(names);
        if (!s.equals("")) {
            if (recipeOreDictDyeName.equals("")) {
                singleOreDictDye = true;
                recipeOreDictDyeName = s;
                recipeOreDictDyeCount = 1;
            } else {
                if (recipeOreDictDyeName.equals(s)) {
                    recipeOreDictDyeCount++;
                } else {
                   /* Recipe has more than one dye */
                    singleOreDictDye = false;
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Check if this is a new item or one we have seen before
     * @return true to continue parsing, false to stop
     */
    private boolean singleItem;
    private ItemStack recipeItem;
    private int recipeItemCount;
    private boolean checkItem(ItemStack itemStack) {

        if (itemStack == null)
            return true;

        if (OreDictHelper.isDye(itemStack))
            return true;

        if (recipeItem == null) {
            recipeItem = itemStack.copy();
            recipeItem.stackSize = 1;
            recipeItemCount = 1;
            singleItem = true;
        } else {
            if (recipeItem.isItemEqual(itemStack)) {
                recipeItemCount++;
            } else {
                /* Recipe has more than one item */
                singleItem = false;
                return false;
            }
        }

        return true;
    }

    private boolean singleOreDictItem;
    private int recipeOreDictItemCount;
    private ArrayList<String> recipeOreDictItemNames;

    private boolean checkItem(ArrayList oreList) {

        if (oreList == null || oreList.size() < 1)
            return true;

        if (OreDictHelper.isDye((ItemStack)oreList.get(0)))
            return true;

        String[] names = OreDictHelper.getOreNames(oreList);

        if (names.length == 0)
            return false;

        if (recipeOreDictItemNames.isEmpty()) {
            for (String name : names)
                recipeOreDictItemNames.add(name);

            singleOreDictItem = true;
            recipeOreDictItemCount = 1;
        } else {

            recipeOreDictItemNames.retainAll(Arrays.asList(names));
            if (recipeOreDictItemNames.size() == 0) {
                singleOreDictItem = false;
                return false;
            }

        }

        return true;
    }

    private void processShapelessVanilla() {

        ShapelessRecipes irecipe = (ShapelessRecipes)recipe;

        /* Is there a dye in this recipe */
        for (Object object : irecipe.recipeItems) {

            if (!(object instanceof ItemStack))
                continue;

            ItemStack itemStack = ((ItemStack)object).copy();
            itemStack.stackSize = 1;

            if (!checkDye(itemStack))
                return;
        }

        if (!singleDye)
            return;

        /* Is there a single item in this recipe */
        for (Object object : irecipe.recipeItems) {

            if (!(object instanceof ItemStack))
                continue;

            ItemStack itemStack = ((ItemStack)object).copy();
            itemStack.stackSize = 1;

            if (!checkItem(itemStack))
                return;
        }

        if (!singleItem)
            return;

        this.valid = true;
    }

    private void processShapedVanilla() {

        ShapedRecipes irecipe = (ShapedRecipes)recipe;

        /* Is there a dye in this recipe */
        for (int i = 0; i < irecipe.recipeItems.length; i++)
        {
            if (!(irecipe.recipeItems[i] instanceof ItemStack))
                continue;

            ItemStack itemStack = irecipe.recipeItems[i].copy();
            itemStack.stackSize = 1;

            if (!checkDye(itemStack))
                return;
        }

        if (!singleDye)
            return;

        /* Is there a single item in this recipe */
        for (int i = 0; i < irecipe.recipeItems.length; i++)
        {
            if (!(irecipe.recipeItems[i] instanceof ItemStack))
                continue;

            ItemStack itemStack = irecipe.recipeItems[i].copy();
            itemStack.stackSize = 1;

            if (!checkItem(itemStack))
                return;
        }

        if (!singleItem)
            return;

        this.valid = true;
    }

    private void processShapelessOreDict() {

        ShapelessOreRecipe irecipe = (ShapelessOreRecipe)recipe;

        /* Is there a single dye in this recipe */
        for (Object object : irecipe.getInput()) {

            if (object instanceof ItemStack) {

                ItemStack itemStack = ((ItemStack) object).copy();
                itemStack.stackSize = 1;

                if (!checkDye(itemStack))
                    return;

            } else if (object instanceof ArrayList) {

                if (!checkDye((ArrayList)object))
                    return;
            }
        }

        if (!singleDye && !singleOreDictDye)
            return;

        if (singleDye && singleOreDictDye) {
            if (!recipeOreDictDyeName.equals(DyeHelper.DyeType.getDye(recipeDye).getOreDict()))
                return;
        }

        /* Is there a single item in this recipe */
        for (Object object : irecipe.getInput()) {

            if (object instanceof ItemStack) {

                ItemStack itemStack = ((ItemStack) object).copy();
                itemStack.stackSize = 1;

                if (!checkItem(itemStack))
                    return;

            } else if (object instanceof ArrayList) {

                if (!checkItem((ArrayList)object))
                    return;
            }
        }

        if(!singleItem && !singleOreDictItem)
            return;

        if (singleItem && singleOreDictItem)
            return;

        this.valid = true;
    }

    private void processShapedOreDict() {

        ShapedOreRecipe irecipe = (ShapedOreRecipe)recipe;

        /* Is there a single dye in this recipe */
        for (int i = 0; i < irecipe.getInput().length; i++) {

            if (irecipe.getInput()[i] instanceof ItemStack) {

                ItemStack itemStack = ((ItemStack) irecipe.getInput()[i]).copy();
                itemStack.stackSize = 1;

                if (!checkDye(itemStack))
                    return;

            } else if (irecipe.getInput()[i] instanceof ArrayList) {

                if (!checkDye((ArrayList)irecipe.getInput()[i]))
                    return;
            }
        }

        if (!singleDye && !singleOreDictDye)
            return;

        if (singleDye && singleOreDictDye) {
            if (!recipeOreDictDyeName.equals(DyeHelper.DyeType.getDye(recipeDye).getOreDict()))
                return;
        }

        /* Is there a single item in this recipe */
        for (int i = 0; i < irecipe.getInput().length; i++) {

            if (irecipe.getInput()[i] instanceof ItemStack) {

                ItemStack itemStack = ((ItemStack) irecipe.getInput()[i]).copy();
                itemStack.stackSize = 1;

                if (!checkItem(itemStack))
                    return;

            } else if (irecipe.getInput()[i] instanceof ArrayList) {

                if (!checkItem((ArrayList)irecipe.getInput()[i]))
                    return;
            }
        }

        if(!singleItem && !singleOreDictItem)
            return;

        if (singleItem && singleOreDictItem)
            return;

        this.valid = true;
    }

    @Override
    public String toString() {

        return Objects.toStringHelper(this)
                .add("singleDye", singleDye)
                .add("recipeDye", recipeDye)
                .add("recipeDyeCount", recipeDyeCount)
                .add("singleOreDictDye", singleOreDictDye)
                .add("recipeOreDictDyeName", recipeOreDictDyeName)
                .add("recipeOreDictDyeCount", recipeOreDictDyeCount)
                .add("singleItem", singleItem)
                .add("recipeItem", recipeItem)
                .add("recipeItemCount", recipeItemCount)
                .add("singleOreDictItem", singleOreDictItem)
                .add("recipeOreDictItemName", recipeOreDictItemNames)
                .add("recipeOreDictItemCount", recipeOreDictItemCount)
                .toString();
    }

    private static enum RecipeType {

        SHAPELESS_VANILLA,
        SHAPED_VANILLA,
        SHAPELESS_OREDICT,
        SHAPED_OREDICT
    }
}
