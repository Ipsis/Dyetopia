package ipsis.dyetopia.plugins.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ipsis.dyetopia.gui.GuiPainter;
import ipsis.dyetopia.gui.container.ContainerPainter;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlocksManager;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.util.ComparableItemStackBlockSafe;
import ipsis.dyetopia.util.DyeHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NEIPainterRecipeManager extends TemplateRecipeHandler {

    public class PainterSetup extends TemplateRecipeHandler.CachedRecipe {

        PositionedStack result; /* The output colored block */
        PositionedStack ingredient;
        DyeHelper.DyeType dye;

        public PainterSetup(ItemStack ingredient, ItemStack result, DyeHelper.DyeType dye) {

            this.ingredient = new PositionedStack(ingredient, ContainerPainter.INPUT_SLOT_X - 5, ContainerPainter.INPUT_SLOT_Y - 11);
            this.result = new PositionedStack(result, ContainerPainter.OUTPUT_SLOT_X - 5, ContainerPainter.OUTPUT_SLOT_Y - 11);
            this.dye = dye;
        }

        @Override
        public PositionedStack getIngredient() {
            return ingredient;
        }

        @Override
        public PositionedStack getResult() {
            return result;
        }
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(78 - 5, 34 - 11, 24, 16), "painting"));
    }

    @Override
    public String getGuiTexture() {
        return Textures.Gui.GUI_PAINTER;
    }

    @Override
    public String getRecipeName() {
        return "Painter Recipe";
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals("painting") && getClass() == NEIPainterRecipeManager.class) {

            ArrayList<HashMap<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe>> recipes = DyeableBlocksManager.getRecipes();
            for (int i = 0; i < 16; i++) {
                for (Map.Entry<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe> entry : recipes.get(i).entrySet()) {
                    PainterSetup r = new PainterSetup(entry.getValue().getInput(), entry.getValue().getOutput(), entry.getValue().getDye());
                    arecipes.add(r);
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiPainter.class;
    }

    @Override
    public boolean hasOverlay(GuiContainer gui, net.minecraft.inventory.Container container, int recipe) {
        return false;
    }


    /*
    private static class ResultRecipe {

        ArrayList<ItemStack> input;
        ItemStack output;
        DyeHelper.DyeType dye;

        public ResultRecipe(ItemStack output, ItemStack input, DyeHelper.DyeType dye) {

            this.output = output;
            this.input = new ArrayList<ItemStack>();
            this.input.add(input);
            this.dye = dye;
        }
    }

    private HashMap<ComparableItemStackBlockSafe, ResultRecipe> resultMap;
    private void buildPainterMaps() {
        resultMap = new HashMap<ComparableItemStackBlockSafe, ResultRecipe>();

        ArrayList<HashMap<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe>> recipes = DyeableBlocksManager.getRecipes();
        for (int i = 0; i < 16; i++) {
            for (Map.Entry<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe> entry : recipes.get(i).entrySet()) {

                ComparableItemStackBlockSafe key = new ComparableItemStackBlockSafe(entry.getValue().getOutput());
                ResultRecipe r = resultMap.get(key);
                if (r != null) {
                    r.input.add(entry.getValue().getInput());
                } else {
                    r = new ResultRecipe(entry.getValue().getOutput(), entry.getValue().getInput(), entry.getValue().getDye());
                    resultMap.put(key, r);
                }
            }
        }
    } */
}
