package ipsis.dyetopia.plugins.nei;

import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cofh.lib.inventory.ComparableItemStackSafe;
import ipsis.dyetopia.gui.GuiSqueezer;
import ipsis.dyetopia.gui.container.ContainerSqueezer;
import ipsis.dyetopia.manager.SqueezerManager;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class NEISqueezerRecipeManager extends TemplateRecipeHandler {

    public class SqueezerSetup extends CachedRecipe {

        PositionedStack ingred;

        public SqueezerSetup(ItemStack ingred) {

            ingred.stackSize = 1;
            this.ingred = new PositionedStack(ingred, ContainerSqueezer.INPUT_SLOT_X - 5, ContainerSqueezer.INPUT_SLOT_Y - 11);
        }

        @Override
        public PositionedStack getIngredient() {
            return ingred;
        }

        @Override
        public PositionedStack getResult() {
            return null;
        }
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(62 - 5, 35 - 11, 24, 16), "squeezing"));
    }

    @Override
    public String getGuiTexture() {
        return Textures.Gui.GUI_SQUEEZER;
    }

    @Override
    public String getRecipeName() {
        return "Squeezer Recipe";
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals("squeezing") && getClass() == NEISqueezerRecipeManager.class) {
            HashMap<ComparableItemStackSafe, SqueezerManager.SqueezerRecipe> recipes = SqueezerManager.getRecipes();
            for (Map.Entry<ComparableItemStackSafe,SqueezerManager.SqueezerRecipe> recipe : recipes.entrySet())
                arecipes.add(new SqueezerSetup(recipe.getValue().getInput()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiSqueezer.class;
    }

    @Override
    public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
        return false;
    }
}
