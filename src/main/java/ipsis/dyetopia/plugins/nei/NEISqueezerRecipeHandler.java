package ipsis.dyetopia.plugins.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import cofh.lib.inventory.ComparableItemStackSafe;
import ipsis.dyetopia.gui.GuiSqueezer;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.manager.SqueezerManager;
import ipsis.dyetopia.reference.GuiLayout;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class NEISqueezerRecipeHandler extends TemplateRecipeHandlerBase {

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiSqueezer.class;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals("squeezing") && getClass() == NEISqueezerRecipeHandler.class) {
            HashMap<ComparableItemStackSafe, SqueezerManager.SqueezerRecipe> recipes = SqueezerManager.getRecipes();
            for (Map.Entry<ComparableItemStackSafe,SqueezerManager.SqueezerRecipe> recipe : recipes.entrySet())
                arecipes.add(new SqueezerRecipe(recipe.getValue().getInput(), recipe.getValue().getRedAmount(),
                        recipe.getValue().getYellowAmount(), recipe.getValue().getBlueAmount(), recipe.getValue().getWhiteAmount(),
                        recipe.getValue().getEnergy()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {

        /* Recipes that use this ingredient as an input */
        HashMap<ComparableItemStackSafe, SqueezerManager.SqueezerRecipe> recipes = SqueezerManager.getRecipes();
        for (Map.Entry<ComparableItemStackSafe,SqueezerManager.SqueezerRecipe> recipe : recipes.entrySet()) {
            if (NEIServerUtils.areStacksSameTypeCrafting(recipe.getValue().getInput(), ingredient)) {
                arecipes.add(new SqueezerRecipe(recipe.getValue().getInput(), recipe.getValue().getRedAmount(),
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
        int energyMax = 2000;
        int tankMax = 500;

        this.recipeName = "Squeezer Recipe";
        addProgressTransferRect(GuiLayout.Squeezer.PROGRESS_X, GuiLayout.Squeezer.PROGRESS_Y, "squeezing");
        addWidget(new Slot(GuiLayout.Squeezer.INPUT_SLOT_X, GuiLayout.Squeezer.INPUT_SLOT_Y, "inputSlot"));
        addWidget(new Energy(GuiLayout.Squeezer.ENERGY_X, GuiLayout.Squeezer.ENERGY_Y, energyMax, "energy"));
        addWidget(new Tank(GuiLayout.Squeezer.RED_TANK_X, GuiLayout.Squeezer.TANK_Y, new FluidStack(ModFluids.fluidDyeRed, 10), tankMax, "redTank"));
        addWidget(new Tank(GuiLayout.Squeezer.YELLOW_TANK_X, GuiLayout.Squeezer.TANK_Y, new FluidStack(ModFluids.fluidDyeYellow, 10), tankMax, "yellowTank"));
        addWidget(new Tank(GuiLayout.Squeezer.BLUE_TANK_X, GuiLayout.Squeezer.TANK_Y, new FluidStack(ModFluids.fluidDyeBlue, 10), tankMax, "blueTank"));
        addWidget(new Tank(GuiLayout.Squeezer.WHITE_TANK_X, GuiLayout.Squeezer.TANK_Y, new FluidStack(ModFluids.fluidDyeWhite, 10), tankMax, "whiteTank"));
        addWidget(new Progress(GuiLayout.Squeezer.PROGRESS_X, GuiLayout.Squeezer.PROGRESS_Y, "progress"));
    }

    @Override
    public void updateWidgets(int recipe) {

        SqueezerRecipe r = (SqueezerRecipe)arecipes.get(recipe);
        if (r == null)
            return;

        updateEnergyWidget("energy", r.energy);
        updateTankWidget("redTank", r.red);
        updateTankWidget("yellowTank", r.yellow);
        updateTankWidget("blueTank", r.blue);
        updateTankWidget("whiteTank", r.white);
    }

    public class SqueezerRecipe extends CachedRecipe {

        PositionedStack ingredient;
        int energy;
        int red;
        int yellow;
        int blue;
        int white;

        public SqueezerRecipe(ItemStack ingred, int red, int yellow, int blue, int white, int energy) {

            /* itemstack sits inside the slot, which has a 1 pixel border */
            this.ingredient = createPositionedStack(GuiLayout.Squeezer.INPUT_SLOT_X, GuiLayout.Squeezer.INPUT_SLOT_Y, ingred);
            this.energy = energy;
            this.red = red;
            this.yellow = yellow;
            this.blue = blue;
            this.white = white;
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
