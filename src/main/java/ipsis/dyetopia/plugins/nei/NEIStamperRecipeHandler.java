package ipsis.dyetopia.plugins.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.gui.GuiStamper;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.manager.StamperManager;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.reference.Lang;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class NEIStamperRecipeHandler extends TemplateRecipeHandlerBase {

    private static final String OUTPUT_ID = "stamping";

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiStamper.class;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals(OUTPUT_ID) && getClass() == NEIStamperRecipeHandler.class) {

            /* Only one input */
            ItemStack ingredient = new ItemStack(ModItems.itemDyeBlank, 1);
            for (StamperManager.StamperRecipe r : StamperManager.getRecipes())
                arecipes.add(new StamperRecipe(ingredient, r.getOutput(), r.getPureAmount(), r.getEnergy()));

        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {

        for (StamperManager.StamperRecipe r : StamperManager.getRecipes())
        if (NEIServerUtils.areStacksSameTypeCrafting(result, r.getOutput()))
            arecipes.add(new StamperRecipe(new ItemStack(ModItems.itemDyeBlank, 1), result, r.getPureAmount(), r.getEnergy()));
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {

        /* Recipes that use this ingredient as an input */
        if (!ingredient.isItemEqual(new ItemStack(ModItems.itemDyeBlank)))
            return;

        for (StamperManager.StamperRecipe r : StamperManager.getRecipes())
            arecipes.add(new StamperRecipe(ingredient, r.getOutput(), r.getPureAmount(), r.getEnergy()));
    }

    @Override
    public void initialiseHandler() {

        /**
         * The fluid amounts compared to the tank sizes are quite small, so we scale back the tank display capacity
         * so you actually see the fluids!
         */
        int energyMax = 2000;
        int tankMax = 500;

        this.recipeName = StringHelper.localize(Lang.Gui.TITLE_STAMPER);
        addProgressTransferRect(GuiLayout.Stamper.PROGRESS_X, GuiLayout.Stamper.PROGRESS_Y, OUTPUT_ID);
        addWidget(new Slot(GuiLayout.Stamper.INPUT_SLOT_X, GuiLayout.Stamper.INPUT_SLOT_Y, "inputSlot"));
        addWidget(new Slot(GuiLayout.Stamper.OUTPUT_SLOT_X, GuiLayout.Stamper.OUTPUT_SLOT_Y, "outputSlot"));
        addWidget(new Energy(GuiLayout.Stamper.ENERGY_X, GuiLayout.Stamper.ENERGY_Y, energyMax, "energy"));
        addWidget(new Tank(GuiLayout.Stamper.PURE_TANK_X, GuiLayout.Stamper.PURE_TANK_Y, new FluidStack(ModFluids.fluidDyePure, 10), tankMax, "pureTank"));
        addWidget(new Progress(GuiLayout.Stamper.PROGRESS_X, GuiLayout.Stamper.PROGRESS_Y, "progress"));
    }

    @Override
    public void updateWidgets(int recipe) {

        StamperRecipe r = (StamperRecipe)arecipes.get(recipe);
        if (r == null)
            return;

        updateEnergyWidget("energy", r.energy);
        updateTankWidget("pureTank", r.pure);
    }

    public class StamperRecipe extends CachedRecipe {

        PositionedStack ingredient;
        PositionedStack result;
        int energy;
        int pure;

        public StamperRecipe(ItemStack ingred, ItemStack result, int pure, int energy) {

            this.ingredient = createPositionedStack(GuiLayout.Stamper.INPUT_SLOT_X, GuiLayout.Stamper.INPUT_SLOT_Y, ingred);
            this.result = createPositionedStack(GuiLayout.Stamper.OUTPUT_SLOT_X, GuiLayout.Stamper.OUTPUT_SLOT_Y, result);
            this.energy = energy;
            this.pure = pure;

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
}
