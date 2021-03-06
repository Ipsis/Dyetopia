package ipsis.dyetopia.manager;

import cofh.api.energy.EnergyStorage;
import ipsis.dyetopia.gui.container.ProgressBar;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

/**
 * This is a wrapper around the EnergyStorge
 */

public class EnergyManager {

    private EnergyStorage energyStorage;

    private EnergyManager() { }

    public EnergyManager(int capacity) {

        energyStorage = new EnergyStorage(capacity);
    }

    public EnergyStorage getEnergyStorage() { return this.energyStorage; }

    /****************
     * Wrapped IEnergyHandler calls
     ****************/
    public boolean canConnectEnergy(ForgeDirection from) {

        return true;
    }

    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {

        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {

        return energyStorage.extractEnergy(maxExtract, simulate);
    }

    public int getEnergyStored(ForgeDirection from) {

        return energyStorage.getEnergyStored();
    }

    public int getMaxEnergyStored(ForgeDirection from) {

        return energyStorage.getMaxEnergyStored();
    }

    /**
     * NBT
     */
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        energyStorage.readFromNBT(nbtTagCompound);
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        energyStorage.writeToNBT(nbtTagCompound);
    }

    /**
     * GUI updating
     */
    private int lastEnergy;
    public void initGuiTracking(ICrafting icrafting, Container container) {

        lastEnergy = energyStorage.getEnergyStored();

        int progId = ProgressBar.createIDEnergy(ProgressBar.ID_ENERGY_STORED);
        icrafting.sendProgressBarUpdate(container, progId, energyStorage.getEnergyStored());
    }

    public void updateGuiTracking(List crafters, Container container) {

        if (lastEnergy == energyStorage.getEnergyStored())
            return;

        int progId = ProgressBar.createIDEnergy(ProgressBar.ID_ENERGY_STORED);
        for (Object crafter : crafters) {

            ICrafting icrafting = (ICrafting) crafter;
            icrafting.sendProgressBarUpdate(container, progId, energyStorage.getEnergyStored());
        }

        lastEnergy = energyStorage.getEnergyStored();
    }

    public void processGuiTracking(int id, int data) {

        if (ProgressBar.getIDType(id) == ProgressBar.ID_TYPE.ID_ENERGY)
            if (ProgressBar.getIDValue(id) == ProgressBar.ID_ENERGY_STORED)
                energyStorage.setEnergyStored(data);
    }


}
