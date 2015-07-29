package ipsis.dyetopia.manager;

import ipsis.dyetopia.gui.container.ProgressBar;
import ipsis.dyetopia.reference.Nbt;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;

import java.util.List;

public class FactoryManager {

    private boolean running;
    protected IFactoryRecipe runningRecipe;
    private int recipeEnergy;
    private int consumedEnergy;
    private IFactory factory;

    public FactoryManager(IFactory factory) {

        this.factory = factory;
        resetSM();
    }

    private void resetSM() {

        this.runningRecipe = null;
        this.recipeEnergy = 0;
        this.consumedEnergy = 0;
        setRunning(false);
    }

    private void setRunning(boolean running) {

        if (this.running != running) {
            this.running = running;
            this.factory.updateRunning(this.running);
        }
    }

    public boolean getRunning() {

        return this.running;
    }

    public boolean isFinished() {

        if (runningRecipe == null)
            return false;

        if (this.consumedEnergy >= runningRecipe.getEnergy())
            return true;

        return false;
    }

    public void run() {

        IFactoryRecipe currRecipe = factory.getRecipe();

        if (currRecipe == null) {
            resetSM();
            return;
        }

        if (runningRecipe != null && currRecipe != runningRecipe) {
            resetSM();
            return;
        }

        runningRecipe = currRecipe;
        recipeEnergy = runningRecipe.getEnergy();

        if (!factory.isOutputValid(runningRecipe)) {
            resetSM();
            return;
        }

        if (!factory.isEnergyAvailable(factory.getEnergyTick())) {
            setRunning(false);
            return;
        }

        setRunning(true);
        factory.consumeEnergy(factory.getEnergyTick());
        this.consumedEnergy += factory.getEnergyTick();

        if (!isFinished())
            return;

		/* Processing is finished, create the output */
        factory.consumeInputs(runningRecipe);
        factory.createOutputs(runningRecipe);

        /**
         *  Reset progress but dont update the running state
         *  This should stop the flickering on/off effect
         */
        currRecipe = null;
        runningRecipe = null;
        recipeEnergy = 0;
        consumedEnergy = 0;

        runningRecipe = factory.getRecipe();
        if (runningRecipe == null)
            resetSM();
    }

    public int getScaledProgress(int scale) {

		/*
		if (isRsDisabled() || getRecipeEnergy() <= 0)
			return 0; */

        if (recipeEnergy <= 0)
            return 0;

        return (int)(scale * ((float)consumedEnergy / recipeEnergy));
    }

    /*****
     * NBT
     *****/
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        nbttagcompound.setBoolean(Nbt.Blocks.RUNNING, running);
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {

        if (nbttagcompound.hasKey(Nbt.Blocks.RUNNING))
            running = nbttagcompound.getBoolean(Nbt.Blocks.RUNNING);
        else
            running = false;
    }

    /**
     * GUI updating
     */
    private int lastConsumedEnergy;
    private int lastRecipeEnergy;
    public void initGuiTracking(ICrafting icrafting, Container container) {

        this.lastConsumedEnergy = this.consumedEnergy;
        this.lastRecipeEnergy = this.recipeEnergy;
    }

    public void updateGuiTracking(List crafters, Container container) {

        for (Object crafter : crafters) {

            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastConsumedEnergy != this.consumedEnergy) {
                int progId = ProgressBar.createIDEnergy(ProgressBar.ID_ENERGY_CONSUMED);
                icrafting.sendProgressBarUpdate(container, progId, this.consumedEnergy);
            }
            if (this.lastRecipeEnergy != this.recipeEnergy) {
                int progId = ProgressBar.createIDEnergy(ProgressBar.ID_ENERGY_RECIPE);
                icrafting.sendProgressBarUpdate(container, progId, this.recipeEnergy);
            }
        }

        this.lastConsumedEnergy = this.consumedEnergy;
        this.lastRecipeEnergy = this.recipeEnergy;
    }

    public void processGuiTracking(int id, int data) {

        if (ProgressBar.getIDType(id) == ProgressBar.ID_TYPE.ID_ENERGY) {

            if (ProgressBar.getIDValue(id) == ProgressBar.ID_ENERGY_CONSUMED)
                this.consumedEnergy = data;
            else if (ProgressBar.getIDValue(id) == ProgressBar.ID_ENERGY_RECIPE)
                this.recipeEnergy = data;
        }

    }

}
