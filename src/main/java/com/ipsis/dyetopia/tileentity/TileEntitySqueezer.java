package com.ipsis.dyetopia.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyStorage;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.position.BlockPosition;
import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.manager.*;
import com.ipsis.dyetopia.util.LogHelper;
import com.ipsis.dyetopia.util.TankType;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileEntitySqueezer extends TileEntityMultiBlockMaster implements ITankHandler, ISidedInventory, IEnergyHandler, IFactory {

    private TankManager tankMgr;
    private EnergyManager energyMgr;
    private FactoryManager factoryMgr;

    private static final int TANK_CAPACITY = 40000;
    private static final int ENERGY_CAPACITY = 50000;
    private static final int ENERGY_PER_TICK = 10;
    public static final int INPUT_SLOT = 0;

    public TileEntitySqueezer() {
        super();
        this.setMaster(this);
        inventory = new ItemStack[1];
        energyMgr = new EnergyManager(ENERGY_CAPACITY);
        factoryMgr = new FactoryManager(this);

        setupTanks();
    }

    private void setValveColor(BlockPosition p, TileEntityValve.Color color) {

        TileEntity te = this.worldObj.getTileEntity(p.x, p.y, p.z);
        if (te instanceof TileEntityValve) {
            ((TileEntityValve)te).setColor(color);
            this.worldObj.markBlockForUpdate(p.x, p.y, p.z);
        }
    }

    @Override
    public void onStructureValidChanged(boolean isNowValid) {

        /* Assign the valve blocks */
        BlockPosition o = new BlockPosition(
                this.xCoord, this.yCoord, this.zCoord,
                this.getPatternOrientation());
        BlockPosition p;

        /* Red tank */
        p = o.copy();
        p.moveUp(1);
        p.moveRight(1);
        setValveColor(p, isNowValid ? TileEntityValve.Color.RED : TileEntityValve.Color.NONE);

        /* Yellow tank */
        p = o.copy();
        p.moveDown(1);
        p.moveRight(1);
        setValveColor(p, isNowValid ? TileEntityValve.Color.YELLOW : TileEntityValve.Color.NONE);

        /* Blue tank */
        p = o.copy();
        p.moveUp(1);
        p.moveRight(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.BLUE : TileEntityValve.Color.NONE);

        /* White tank */
        p = o.copy();
        p.moveDown(1);
        p.moveRight(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.WHITE : TileEntityValve.Color.NONE);
    }

    private void setupTanks() {

        this.tankMgr = new TankManager();
        this.tankMgr.registerTank(TankType.RED.getName(), TANK_CAPACITY);
        this.tankMgr.registerTank(TankType.YELLOW.getName(), TANK_CAPACITY);
        this.tankMgr.registerTank(TankType.BLUE.getName(), TANK_CAPACITY);
        this.tankMgr.registerTank(TankType.WHITE.getName(), TANK_CAPACITY);

        this.tankMgr.addToWhitelist(TankType.RED.getName(), DYTFluids.fluidDyeRed);
        this.tankMgr.addToWhitelist(TankType.YELLOW.getName(), DYTFluids.fluidDyeYellow);
        this.tankMgr.addToWhitelist(TankType.BLUE.getName(), DYTFluids.fluidDyeBlue);
        this.tankMgr.addToWhitelist(TankType.WHITE.getName(), DYTFluids.fluidDyeWhite);
    }

    /***************
     * ITankHandler
     */

    public TankManager getTankMgr() {
        return this.tankMgr;
    }

    public int fill(TankType tank, ForgeDirection from, FluidStack resource, boolean doFill) {

        return 0;
    }

    public FluidStack drain(TankType tank, ForgeDirection from, FluidStack resource, boolean doDrain) {

        return this.tankMgr.drain(tank.getName(), from, resource, doDrain);
    }

    public FluidStack drain(TankType tank, ForgeDirection from, int maxDrain, boolean doDrain) {

        return this.tankMgr.drain(tank.getName(), from, maxDrain, doDrain);
    }

    public boolean canFill(TankType tank, ForgeDirection from, Fluid fluid) {
        return false;
    }

    public boolean canDrain(TankType tank, ForgeDirection from, Fluid fluid) {

        return this.tankMgr.canDrain(tank.getName(), from, fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        return this.tankMgr.getTankInfo(from);
    }

    /**
     * NBT
     */
    /**
     * NBT and description packet
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        this.tankMgr.writeToNBT(nbttagcompound);
        this.energyMgr.writeToNBT(nbttagcompound);
        this.factoryMgr.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("consumedEnergy", this.consumedEnergy);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.tankMgr.readFromNBT(nbttagcompound);
        this.energyMgr.readFromNBT(nbttagcompound);
        this.factoryMgr.readFromNBT(nbttagcompound);

        this.consumedEnergy = nbttagcompound.getInteger("consumedEnergy");
    }

    /**
     * ISidedInventory
     */
    private static final int[] accessSlots = new int[]{ INPUT_SLOT };
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

        /* Nothing to extract from this machine */
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {

        if (slot == INPUT_SLOT && stack != null && SqueezerManager.getRecipe(stack) != null)
            return true;

        return false;
    }

    /**
     * IEnergyHandler
     */
    public EnergyManager getEnergyMgr() { return this.energyMgr; }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return energyMgr.receiveEnergy(forgeDirection, i, b);
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b) {
        return energyMgr.extractEnergy(forgeDirection, i, b);
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {
        return energyMgr.getEnergyStored(forgeDirection);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {
        return energyMgr.getMaxEnergyStored(forgeDirection);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {
        return canConnectEnergy(forgeDirection);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote)
            return;

        this.factoryMgr.run();

        /*
        this.tankMgr.fill(TankType.RED.getName(), ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeRed, 1), true);
        this.tankMgr.fill(TankType.YELLOW.getName(), ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeYellow, 1), true);
        this.tankMgr.fill(TankType.BLUE.getName(), ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeBlue, 1), true);
        this.tankMgr.fill(TankType.WHITE.getName(), ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeWhite, 1), true); */

        this.energyMgr.receiveEnergy(ForgeDirection.DOWN, 10, false);
    }

    /**
     * IFactory
     */
    public FactoryManager getFactoryMgr() { return this.factoryMgr; }

    private int consumedEnergy;

    @Override
    public boolean isOutputValid(IFactoryRecipe recipe) {

        if (recipe == null)
            return false;

        SqueezerManager.SqueezerRecipe sr = (SqueezerManager.SqueezerRecipe)recipe;

        if (this.tankMgr.fill(TankType.RED.getName(), ForgeDirection.NORTH, sr.getRedFluidStack(), true) == sr.getRedAmount() &&
            this.tankMgr.fill(TankType.YELLOW.getName(), ForgeDirection.NORTH, sr.getYellowFluidStack(), true) == sr.getYellowAmount() &&
            this.tankMgr.fill(TankType.BLUE.getName(), ForgeDirection.NORTH, sr.getBlueFluidStack(), true) == sr.getBlueAmount() &&
            this.tankMgr.fill(TankType.WHITE.getName(), ForgeDirection.NORTH, sr.getWhiteFluidStack(), true) == sr.getWhiteAmount()) {
            return true;
        }

        return false;
    }

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

        SqueezerManager.SqueezerRecipe sr = (SqueezerManager.SqueezerRecipe)recipe;

        this.tankMgr.fill(TankType.RED.getName(), ForgeDirection.NORTH, sr.getRedFluidStack(), false);
        this.tankMgr.fill(TankType.YELLOW.getName(), ForgeDirection.NORTH, sr.getYellowFluidStack(), false);
        this.tankMgr.fill(TankType.BLUE.getName(), ForgeDirection.NORTH, sr.getBlueFluidStack(), false);
        this.tankMgr.fill(TankType.WHITE.getName(), ForgeDirection.NORTH, sr.getWhiteFluidStack(), false);

        LogHelper.info("createOutput: " + sr.toString());
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
        return SqueezerManager.getRecipe(getStackInSlot(INPUT_SLOT));
    }

    @Override
    public void updateRunning(boolean running) {

    }
}
