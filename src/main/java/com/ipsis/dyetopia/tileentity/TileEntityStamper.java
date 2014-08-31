package com.ipsis.dyetopia.tileentity;

import com.ipsis.dyetopia.item.DYTItems;
import com.ipsis.dyetopia.manager.FactoryManager;
import com.ipsis.dyetopia.manager.IFactory;
import com.ipsis.dyetopia.manager.IFactoryRecipe;
import com.ipsis.dyetopia.manager.SqueezerManager;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityStamper extends TileEntityMachinePureDye implements ISidedInventory, IFactory {

    private static final int TANK_CAPACITY = 40000;
    private static final int ENERGY_PER_TICK = 10;
    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private FactoryManager factoryMgr;

    public TileEntityStamper() {

        super(TANK_CAPACITY);
        inventory = new ItemStack[2];
        factoryMgr = new FactoryManager(this);
    }

    /**
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        this.factoryMgr.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("consumedEnergy", this.consumedEnergy);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.factoryMgr.readFromNBT(nbttagcompound);

        this.consumedEnergy = nbttagcompound.getInteger("consumedEnergy");
    }

    /**
     * ISidedInventory
     */
    private static final int[] accessSlots = new int[]{ INPUT_SLOT, OUTPUT_SLOT };
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {

        /* All slots accessible from all sides */
        return accessSlots;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {

        /* Ignore side */
        return isItemValidForSlot(slot, itemStack);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {

        if (slot == OUTPUT_SLOT)
            return true;

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {

        if (slot == INPUT_SLOT && stack != null && stack.getItem() == DYTItems.itemDyeBlank)
            return true;

        return false;
    }

    @Override
    public boolean isOutputValid(IFactoryRecipe recipe) {

        if (recipe == null)
            return false;

        /* Check the output slot */
        return true;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote)
            return;

        this.factoryMgr.run();
    }

    /**
     * IFactory
     */
    public FactoryManager getFactoryMgr() { return this.factoryMgr; }

    private int consumedEnergy;

    @Override
    public boolean isEnergyAvailable(int amount) {
        return amount ==  this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, true);
    }

    @Override
    public void consumeInputs(IFactoryRecipe recipe) {
        decrStackSize(INPUT_SLOT, 1);
    }

    @Override
    public void createOutputs(IFactoryRecipe recipe) {

        if (recipe == null)
            return;

        /* reduce the pure amount */
    }

    @Override
    public void consumeEnergy(int amount) {
        this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, false);
    }

    @Override
    public int getEnergyTick() {
        return ENERGY_PER_TICK;
    }

    @Override
    public IFactoryRecipe getRecipe() {
        return null;
    }

    @Override
    public void updateRunning(boolean running) {

    }
}
