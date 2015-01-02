package ipsis.dyetopia.plugins.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import cofh.lib.inventory.ComparableItemStackSafe;
import ipsis.dyetopia.gui.GuiSqueezer;
import ipsis.dyetopia.gui.container.ContainerSqueezer;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.manager.SqueezerManager;
import ipsis.dyetopia.reference.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NEISqueezerRecipeManager2 extends TemplateRecipeHandlerBase {

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiSqueezer.class;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals("squeezing") && getClass() == NEISqueezerRecipeManager2.class) {
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
    public void initialiseHandler() {

        /**
         * The fluid amounts compared to the tank sizes are quite small, so we scale back the tank display capacity
         * so you actually see the fluids!
         */
        int energyMax = 5000;
        int tankMax = 10000;

        this.recipeName = "Squeezer Recipe";
        transferRects.add(new RecipeTransferRect(new Rectangle(62 - 5, 35 - 11, 24, 16), "squeezing"));
        addWidget(new Slot(ContainerSqueezer.INPUT_SLOT_X, ContainerSqueezer.INPUT_SLOT_Y, "inputSlot"));
        addWidget(new Energy(7, 22, energyMax, "energy"));
        addWidget(new Tank(96, 12, new FluidStack(ModFluids.fluidDyeRed, 10), tankMax, "redTank"));
        addWidget(new Tank(114, 12, new FluidStack(ModFluids.fluidDyeYellow, 10), tankMax, "yellowTank"));
        addWidget(new Tank(132, 12, new FluidStack(ModFluids.fluidDyeBlue, 10), tankMax, "blueTank"));
        addWidget(new Tank(150, 12, new FluidStack(ModFluids.fluidDyeWhite, 10), tankMax, "whiteTank"));
        addWidget(new Progress(61, 34, "progress"));
    }

    @Override
    public void updateWidgets(int recipe) {

        SqueezerSetup r = (SqueezerSetup)arecipes.get(recipe);
        if (r == null)
            return;

        updateEnergyWidget("energy", r.energy);
        updateTankWidget("redTank", r.red);
        updateTankWidget("yellowTank", r.yellow);
        updateTankWidget("blueTank", r.blue);
        updateTankWidget("whiteTank", r.white);
    }

    public class SqueezerSetup extends CachedRecipe {

        PositionedStack ingredient;
        int energy;
        int red;
        int yellow;
        int blue;
        int white;


        public SqueezerSetup(ItemStack ingred, int red, int yellow, int blue, int white, int energy) {

            /* itemstack sits inside the slot, which has a 1 pixel border */
            this.ingredient = new PositionedStack(ingred, ContainerSqueezer.INPUT_SLOT_X - X_OFFSET + 1, ContainerSqueezer.INPUT_SLOT_Y - Y_OFFSET + 1);
            this.energy = energy;
            this.red = red;
            this.yellow = yellow;
            this.blue = blue;
            this.white = white;

            this.energy = 1000;
            this.red = 1000;
            this.yellow = 1000;
            this.blue = 1000;
            this.white = 1000;
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
}
