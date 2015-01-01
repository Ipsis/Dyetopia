package ipsis.dyetopia.plugins.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.NEIClientUtils;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cofh.lib.inventory.ComparableItemStackSafe;
import cofh.lib.util.helpers.MathHelper;
import ipsis.dyetopia.gui.GuiSqueezer;
import ipsis.dyetopia.gui.container.ContainerSqueezer;
import ipsis.dyetopia.manager.SqueezerManager;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.awt.List;
import java.util.*;

public class NEISqueezerRecipeManager extends TemplateRecipeHandlerDYT {

    private static final int X_OFFSET = 5;
    private static final int Y_OFFSET = 11;

    public class SqueezerSetup extends CachedRecipeDYT {

        public SqueezerSetup(ItemStack ingred, int red, int yellow, int blue, int white, int energy) {

            ingred.stackSize = 1;
            this.ingredient = new PositionedStack(ingred, ContainerSqueezer.INPUT_SLOT_X - X_OFFSET + 1, ContainerSqueezer.INPUT_SLOT_Y - Y_OFFSET + 1);
            this.energyCurr = energy;
            this.energyMax = Settings.Machines.energyCapacity;
            this.redCurr = red;
            this.redMax = Settings.Machines.tankCapacity;
            this.yellowCurr = yellow;
            this.yellowMax = Settings.Machines.tankCapacity;
            this.blueCurr = blue;
            this.blueMax = Settings.Machines.tankCapacity;
            this.whiteCurr = white;
            this.whiteMax = Settings.Machines.tankCapacity;
        }

        @Override
        public PositionedStack getIngredient() {
            return ingredient;
        }

        @Override
        public PositionedStack getResult() {
            /* Result is the fluid amounts */
            return null;
        }
    }

    @Override
    public void initialiseHandler() {

        this.recipeName = "Squeezer Recipe";
        setTransferRect(62 - 5, 35 - 11, 24, 16, "squeezing");
    }

    @Override
    public void drawBackgroundWidgets(int recipe) {

        drawSlot(ContainerSqueezer.INPUT_SLOT_X - X_OFFSET, ContainerSqueezer.INPUT_SLOT_Y - Y_OFFSET);
        drawEnergy(7 - X_OFFSET, 22 - Y_OFFSET);
        drawTank(96 - X_OFFSET, 12 - Y_OFFSET);
        drawTank(114 - X_OFFSET, 12 - Y_OFFSET);
        drawTank(132 - X_OFFSET, 12 - Y_OFFSET);
        drawTank(150 - X_OFFSET, 12 - Y_OFFSET);
        drawProgress(61 - X_OFFSET, 34 - Y_OFFSET);
    }

    @Override
    public void drawExtras(int recipe) {

        super.drawExtras(recipe);
        drawProgressBar(61 - X_OFFSET, 34 - Y_OFFSET);
        drawEnergyLevel(7 - X_OFFSET, 22 - Y_OFFSET, recipe);

        drawTankLevel(96 - X_OFFSET, 12 - Y_OFFSET, recipe, 0);
        drawTankLevel(114 - X_OFFSET, 12 - Y_OFFSET, recipe, 1);
        drawTankLevel(132 - X_OFFSET, 12 - Y_OFFSET, recipe, 2);
        drawTankLevel(150 - X_OFFSET, 12 - Y_OFFSET, recipe, 3);
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals("squeezing") && getClass() == NEISqueezerRecipeManager.class) {
            HashMap<ComparableItemStackSafe, SqueezerManager.SqueezerRecipe> recipes = SqueezerManager.getRecipes();
            for (Map.Entry<ComparableItemStackSafe,SqueezerManager.SqueezerRecipe> recipe : recipes.entrySet())
                arecipes.add(new SqueezerSetup(recipe.getValue().getInput(), recipe.getValue().getRedAmount(),
                        recipe.getValue().getYellowAmount(), recipe.getValue().getBlueAmount(), recipe.getValue().getWhiteAmount(),
                        recipe.getValue().getEnergy()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {

        HashMap<ComparableItemStackSafe, SqueezerManager.SqueezerRecipe> recipes = SqueezerManager.getRecipes();
        for (Map.Entry<ComparableItemStackSafe,SqueezerManager.SqueezerRecipe> recipe : recipes.entrySet()) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue().getInput(), ingredient)) {
                arecipes.add(new SqueezerSetup(recipe.getValue().getInput(), recipe.getValue().getRedAmount(),
                        recipe.getValue().getYellowAmount(), recipe.getValue().getBlueAmount(), recipe.getValue().getWhiteAmount(),
                        recipe.getValue().getEnergy()));
            }
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
