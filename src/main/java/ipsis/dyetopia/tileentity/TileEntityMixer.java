package ipsis.dyetopia.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.api.tileentity.IEnergyInfo;
import cofh.lib.util.position.BlockPosition;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.manager.*;
import ipsis.dyetopia.reference.Nbt;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.TankType;
import ipsis.dyetopia.util.multiblock.MultiBlockPattern;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileEntityMixer extends TileEntityMultiBlockMaster implements  ITankHandler, ISidedInventory, IEnergyHandler, IEnergyInfo, IFactory  {

    private TankManager tankMgr;
    private EnergyManager energyMgr;
    private FactoryManager factoryMgr;

    private MultiBlockPattern pattern = MultiBlockPatternManager.getPattern(MultiBlockPatternManager.Type.MIXER);

    public TileEntityMixer() {
        super();
        this.setMaster(this);
        energyMgr = new EnergyManager(Settings.Machines.energyCapacity);
        energyMgr.getEnergyStorage().setMaxTransfer(Settings.Machines.energyRxTick);
        factoryMgr = new FactoryManager(this);

        setupTanks();
    }

    @Override
    public MultiBlockPattern getPattern() {
        return pattern;
    }

    private void setValveColor(BlockPosition p, TileEntityValve.Color color) {

        TileEntity te = this.worldObj.getTileEntity(p.x, p.y, p.z);
        if (te instanceof TileEntityValve) {
            ((TileEntityValve) te).setColor(color);
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
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.RED : TileEntityValve.Color.NONE);

        /* Yellow tank */
        p = o.copy();
        p.moveUp(1);
        p.moveLeft(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.YELLOW : TileEntityValve.Color.NONE);

        /* Blue tank */
        p = o.copy();
        p.moveDown(1);
        p.moveRight(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.BLUE : TileEntityValve.Color.NONE);

        /* White tank */
        p = o.copy();
        p.moveDown(1);
        p.moveLeft(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.WHITE : TileEntityValve.Color.NONE);

        /* Pure tank */
        p = o.copy();
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.PURE : TileEntityValve.Color.NONE);
    }

    private void setupTanks() {

        this.tankMgr = new TankManager();
        this.tankMgr.registerTank(TankType.RED.getName(), Settings.Machines.tankCapacity);
        this.tankMgr.registerTank(TankType.YELLOW.getName(), Settings.Machines.tankCapacity);
        this.tankMgr.registerTank(TankType.BLUE.getName(), Settings.Machines.tankCapacity);
        this.tankMgr.registerTank(TankType.WHITE.getName(), Settings.Machines.tankCapacity);
        this.tankMgr.registerTank(TankType.PURE.getName(), Settings.Machines.tankCapacity);

        this.tankMgr.addToWhitelist(TankType.RED.getName(), ModFluids.fluidDyeRed);
        this.tankMgr.addToWhitelist(TankType.YELLOW.getName(), ModFluids.fluidDyeYellow);
        this.tankMgr.addToWhitelist(TankType.BLUE.getName(), ModFluids.fluidDyeBlue);
        this.tankMgr.addToWhitelist(TankType.WHITE.getName(), ModFluids.fluidDyeWhite);
        this.tankMgr.addToWhitelist(TankType.PURE.getName(), ModFluids.fluidDyePure);

        this.tankMgr.blockTankFillAll(TankType.PURE.getName());
        this.tankMgr.blockTankDrainAll(TankType.RED.getName());
        this.tankMgr.blockTankDrainAll(TankType.YELLOW.getName());
        this.tankMgr.blockTankDrainAll(TankType.BLUE.getName());
        this.tankMgr.blockTankDrainAll(TankType.WHITE.getName());
    }

    /**
     * ************
     * ITankHandler
     */

    public TankManager getTankMgr() {
        return this.tankMgr;
    }

    public int fill(TankType tank, ForgeDirection from, FluidStack resource, boolean doFill) {

        if (!this.isStructureValid())
            return 0;

        return this.tankMgr.fill(tank.getName(), from, resource, doFill);
    }

    public FluidStack drain(TankType tank, ForgeDirection from, FluidStack resource, boolean doDrain) {

        if (!this.isStructureValid())
            return null;

        return this.tankMgr.drain(tank.getName(), from, resource, doDrain);
    }

    public FluidStack drain(TankType tank, ForgeDirection from, int maxDrain, boolean doDrain) {

        if (!this.isStructureValid())
            return null;

        return this.tankMgr.drain(tank.getName(), from, maxDrain, doDrain);
    }

    public boolean canFill(TankType tank, ForgeDirection from, Fluid fluid) {

        if (!this.isStructureValid())
            return false;

        return this.tankMgr.canFill(tank.getName(), from, fluid);
    }

    public boolean canDrain(TankType tank, ForgeDirection from, Fluid fluid) {

        if (!this.isStructureValid())
            return false;

        return this.tankMgr.canDrain(tank.getName(), from, fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        if (!this.isStructureValid())
            return null;

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

        nbttagcompound.setInteger(Nbt.Blocks.CONSUMED_ENERGY, this.consumedEnergy);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.tankMgr.readFromNBT(nbttagcompound);
        this.energyMgr.readFromNBT(nbttagcompound);
        this.factoryMgr.readFromNBT(nbttagcompound);

        this.consumedEnergy = nbttagcompound.getInteger(Nbt.Blocks.CONSUMED_ENERGY);
    }

    /**
     * IEnergyHandler
     */
    public EnergyManager getEnergyMgr() {
        return this.energyMgr;
    }

    @Override
    public int receiveEnergy(ForgeDirection forgeDirection, int i, boolean b) {

        if (!this.isStructureValid())
            return 0;

        return energyMgr.receiveEnergy(forgeDirection, i, b);
    }

    @Override
    public int extractEnergy(ForgeDirection forgeDirection, int i, boolean b) {

        if (!this.isStructureValid())
            return 0;

        return energyMgr.extractEnergy(forgeDirection, i, b);
    }

    @Override
    public int getEnergyStored(ForgeDirection forgeDirection) {

        if (!this.isStructureValid())
            return 0;

        return energyMgr.getEnergyStored(forgeDirection);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection forgeDirection) {

        if (!this.isStructureValid())
            return 0;

        return energyMgr.getMaxEnergyStored(forgeDirection);
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection forgeDirection) {

        if (!this.isStructureValid())
            return false;

        return energyMgr.canConnectEnergy(forgeDirection);
    }

    /**
     * IEnergyInfo
     */
    public int getInfoEnergyPerTick() {
        return getEnergyTick();
    }

    public int getInfoMaxEnergyPerTick() {
        return energyMgr.getEnergyStorage().getMaxReceive();
    }

    public int getInfoEnergyStored() {

        return energyMgr.getEnergyStored(ForgeDirection.DOWN);
    }

    public int getInfoMaxEnergyStored() {

        return this.getMaxEnergyStored(ForgeDirection.DOWN);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote)
            return;

        if (!this.isStructureValid())
            return;

        this.factoryMgr.run();
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

        MixerManager.MixerRecipe r = (MixerManager.MixerRecipe)recipe;

        return this.tankMgr.fill(TankType.PURE.getName(), r.getPureFluidStack(), false) == r.getPureAmount();
    }

    @Override
    public boolean isEnergyAvailable(int amount) {
        return amount ==  this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, true);
    }

    @Override
    public void consumeInputs(IFactoryRecipe recipe) {

        MixerManager.MixerRecipe r = (MixerManager.MixerRecipe)recipe;

        this.tankMgr.drain(TankType.RED.getName(), r.getRedFluidStack(), true);
        this.tankMgr.drain(TankType.YELLOW.getName(), r.getYellowFluidStack(), true);
        this.tankMgr.drain(TankType.BLUE.getName(), r.getBlueFluidStack(), true);
        this.tankMgr.drain(TankType.WHITE.getName(), r.getWhiteFluidStack(), true);
    }

    @Override
    public void createOutputs(IFactoryRecipe recipe) {

        MixerManager.MixerRecipe r = (MixerManager.MixerRecipe)recipe;

        this.tankMgr.fill(TankType.PURE.getName(), r.getPureFluidStack(), true);
    }

    @Override
    public void consumeEnergy(int amount) {
        this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, false);
    }

    @Override
    public int getEnergyTick() {
        return Settings.Machines.mixerRfTick;
    }

    @Override
    public IFactoryRecipe getRecipe() {

        MixerManager.MixerRecipe r = MixerManager.getRecipe();
        if (r == null)
            return null;

        FluidStack f;

        f = this.tankMgr.drain(TankType.RED.getName(), r.getRedFluidStack(), false);
        if (f == null || f.amount != r.getRedAmount())
            return null;

        f = this.tankMgr.drain(TankType.YELLOW.getName(), r.getYellowFluidStack(), false);
        if (f == null || f.amount != r.getYellowAmount())
            return null;

        f = this.tankMgr.drain(TankType.BLUE.getName(), r.getBlueFluidStack(), false);
        if (f == null || f.amount != r.getBlueAmount())
            return null;

        f = this.tankMgr.drain(TankType.WHITE.getName(), r.getWhiteFluidStack(), false);
        if (f == null || f.amount != r.getWhiteAmount())
            return null;

        return r;
    }

    @Override
    public void updateRunning(boolean running) {
        setStatus(running);
    }

    /**
     * ISidedInventory
     */
    private static final int[] fakeAccessSlots = new int[0];
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {

        /* No  slots accessible from any side */
        return fakeAccessSlots;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {

        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {

        /* Nothing to extract from this machine */
        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {

        return false;
    }
}
