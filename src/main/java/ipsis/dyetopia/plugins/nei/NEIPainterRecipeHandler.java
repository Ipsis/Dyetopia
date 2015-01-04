package ipsis.dyetopia.plugins.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.gui.GuiPainter;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlocksManager;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.util.ComparableItemStackBlockSafe;
import ipsis.dyetopia.util.DyeHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NEIPainterRecipeHandler extends TemplateRecipeHandlerBase {

    private static final String OUTPUT_ID = "painting";

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiPainter.class;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals(OUTPUT_ID) && getClass() == NEIPainterRecipeHandler.class) {

            ArrayList<HashMap<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe>> recipes = DyeableBlocksManager.getRecipes();
            for (int i = 0; i < 16; i++) {
                for (Map.Entry<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe> entry : recipes.get(i).entrySet()) {
                    PainterRecipe r = new PainterRecipe(entry.getValue().getInput(), entry.getValue().getOutput(),
                            entry.getValue().getEnergy(), entry.getValue().getPureAmount(), entry.getValue().getDye());
                    arecipes.add(r);
                }
            }
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {

        /* Recipes that create this item */
        ArrayList<HashMap<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe>> recipes = DyeableBlocksManager.getRecipes();
        for (int i = 0; i < 16; i++) {
            for (Map.Entry<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe> entry : recipes.get(i).entrySet()) {
                if (NEIServerUtils.areStacksSameTypeCrafting(entry.getValue().getOutput(), result)) {
                    PainterRecipe r = new PainterRecipe(entry.getValue().getInput(), entry.getValue().getOutput(),
                            entry.getValue().getEnergy(), entry.getValue().getPureAmount(), entry.getValue().getDye());
                    arecipes.add(r);
                }
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {

        /* Recipes that use this item */
        ArrayList<HashMap<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe>> recipes = DyeableBlocksManager.getRecipes();
        for (int i = 0; i < 16; i++) {
            for (Map.Entry<ComparableItemStackBlockSafe, DyeableBlocksManager.DyedBlockRecipe> entry : recipes.get(i).entrySet()) {
                if (NEIServerUtils.areStacksSameTypeCrafting(entry.getValue().getInput(), ingredient)) {
                    PainterRecipe r = new PainterRecipe(entry.getValue().getInput(), entry.getValue().getOutput(),
                            entry.getValue().getEnergy(), entry.getValue().getPureAmount(), entry.getValue().getDye());
                    arecipes.add(r);
                }
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
        int tankMax = 1000;

        this.recipeName = StringHelper.localize(Lang.Gui.TITLE_PAINTER);
        addProgressTransferRect(GuiLayout.Painter.PROGRESS_X, GuiLayout.Painter.PROGRESS_Y, OUTPUT_ID);
        addWidget(new Slot(GuiLayout.Painter.INPUT_SLOT_X, GuiLayout.Painter.INPUT_SLOT_Y, "inputSlot"));
        addWidget(new Slot(GuiLayout.Painter.OUTPUT_SLOT_X, GuiLayout.Painter.OUTPUT_SLOT_Y, "outputSlot"));
        addWidget(new Energy(GuiLayout.Painter.ENERGY_X, GuiLayout.Painter.ENERGY_Y, energyMax, "energy"));
        addWidget(new Tank(GuiLayout.Painter.PURE_TANK_X, GuiLayout.Painter.PURE_TANK_Y, new FluidStack(ModFluids.fluidDyePure, 10), tankMax, "pureTank"));
        addWidget(new Progress(GuiLayout.Painter.PROGRESS_X, GuiLayout.Painter.PROGRESS_Y, "progress"));
    }

    @Override
    public void updateWidgets(int recipe) {

        PainterRecipe r = (PainterRecipe)arecipes.get(recipe);
        if (r == null)
            return;

        updateEnergyWidget("energy", r.energy);
        updateTankWidget("pureTank", r.pure);
    }

    public class PainterRecipe extends CachedRecipe {

        int energy;
        int pure;
        PositionedStack ingredient;
        PositionedStack result;
        DyeHelper.DyeType dye;

        public PainterRecipe(ItemStack ingred, ItemStack result, int energy, int pure, DyeHelper.DyeType dye) {

            /* itemstack sits inside the slot, which has a 1 pixel border */
            this.ingredient = createPositionedStack(GuiLayout.Painter.INPUT_SLOT_X, GuiLayout.Painter.INPUT_SLOT_Y, ingred);
            this.result = createPositionedStack(GuiLayout.Painter.OUTPUT_SLOT_X, GuiLayout.Painter.OUTPUT_SLOT_Y, result);
            this.energy = energy;
            this.pure = pure;
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
}
