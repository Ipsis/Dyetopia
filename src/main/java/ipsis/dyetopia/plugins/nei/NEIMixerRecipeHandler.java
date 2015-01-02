package ipsis.dyetopia.plugins.nei;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import cofh.lib.inventory.ComparableItemStackSafe;
import ipsis.dyetopia.gui.GuiMixer;
import ipsis.dyetopia.gui.GuiSqueezer;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.manager.MixerManager;
import ipsis.dyetopia.manager.SqueezerManager;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.reference.Settings;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class NEIMixerRecipeHandler extends TemplateRecipeHandlerBase {

    @Override
    public Class<? extends GuiContainer> getGuiClass() {
        return GuiMixer.class;
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        /* There is only one recipe */
        if (outputId.equals("mixing") && getClass() == NEIMixerRecipeHandler.class) {

            arecipes.add(new MixerRecipe(
                    MixerManager.getRecipe().getRedAmount(),
                    MixerManager.getRecipe().getYellowAmount(),
                    MixerManager.getRecipe().getBlueAmount(),
                    MixerManager.getRecipe().getWhiteAmount(),
                    MixerManager.getRecipe().getPureAmount(),
                    MixerManager.getRecipe().getEnergy()));
        } else {
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void initialiseHandler() {
        
        int energyMax = 2000;
        int tankMax = Settings.Machines.tankCapacity;

        this.recipeName = "Mixer Recipe";
        addProgressTransferRect(GuiLayout.Mixer.PROGRESS_X, GuiLayout.Mixer.PROGRESS_Y, "mixing");
        addWidget(new Energy(GuiLayout.Mixer.ENERGY_X, GuiLayout.Mixer.ENERGY_Y, energyMax, "energy"));
        addWidget(new Tank(GuiLayout.Mixer.RED_TANK_X, GuiLayout.Mixer.TANK_Y, new FluidStack(ModFluids.fluidDyeRed, 10), tankMax, "redTank"));
        addWidget(new Tank(GuiLayout.Mixer.YELLOW_TANK_X, GuiLayout.Mixer.TANK_Y, new FluidStack(ModFluids.fluidDyeYellow, 10), tankMax, "yellowTank"));
        addWidget(new Tank(GuiLayout.Mixer.BLUE_TANK_X, GuiLayout.Mixer.TANK_Y, new FluidStack(ModFluids.fluidDyeBlue, 10), tankMax, "blueTank"));
        addWidget(new Tank(GuiLayout.Mixer.WHITE_TANK_X, GuiLayout.Mixer.TANK_Y, new FluidStack(ModFluids.fluidDyeWhite, 10), tankMax, "whiteTank"));
        addWidget(new Tank(GuiLayout.Mixer.PURE_TANK_X, GuiLayout.Mixer.TANK_Y, new FluidStack(ModFluids.fluidDyeWhite, 10), tankMax, "pureTank"));
        addWidget(new Progress(GuiLayout.Mixer.PROGRESS_X, GuiLayout.Mixer.PROGRESS_Y, "progress"));
    }

    @Override
    public void updateWidgets(int recipe) {

        MixerRecipe r = (MixerRecipe)arecipes.get(recipe);
        if (r == null)
            return;

        updateEnergyWidget("energy", r.energy);
        updateTankWidget("redTank", r.red);
        updateTankWidget("yellowTank", r.yellow);
        updateTankWidget("blueTank", r.blue);
        updateTankWidget("whiteTank", r.white);
        updateTankWidget("pureTank", r.pure);
    }

    public class MixerRecipe extends CachedRecipe {

        int energy;
        int red;
        int yellow;
        int blue;
        int white;
        int pure;

        public MixerRecipe(int red, int yellow, int blue, int white, int pure, int energy) {

            this.energy = energy;
            this.red = red;
            this.yellow = yellow;
            this.blue = blue;
            this.white = white;
            this.pure = pure;
        }

        @Override
        public PositionedStack getIngredient() {
            return null;
        }

        @Override
        public PositionedStack getResult() { return null; }
    }
}
