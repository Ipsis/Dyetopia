package ipsis.dyetopia.tileentity;

import cofh.api.energy.IEnergyHandler;
import cofh.lib.util.position.BlockPosition;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.manager.*;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.LogHelper;
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

public class TileEntitySqueezer extends TileEntityMultiBlockMaster implements ITankHandler, ISidedInventory, IEnergyHandler, IFactory {

    private TankManager tankMgr;
    private EnergyManager energyMgr;
    private FactoryManager factoryMgr;

    private MultiBlockPattern pattern = MultiBlockPatternManager.getPattern(MultiBlockPatternManager.Type.SQUEEZER);

    public static final int INPUT_SLOT = 0;

    public TileEntitySqueezer() {
        super();
        this.setMaster(this);
        inventory = new ItemStack[1];
        energyMgr = new EnergyManager(Settings.Machines.energyCapacity);
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
        p.moveLeft(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.RED : TileEntityValve.Color.NONE);

        /* Yellow tank */
        p = o.copy();
        p.moveUp(1);
        p.moveRight(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.YELLOW : TileEntityValve.Color.NONE);

        /* Blue tank */
        p = o.copy();
        p.moveDown(1);
        p.moveLeft(1);
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
        this.tankMgr.registerTank(TankType.RED.getName(), Settings.Machines.tankCapacity);
        this.tankMgr.registerTank(TankType.YELLOW.getName(), Settings.Machines.tankCapacity);
        this.tankMgr.registerTank(TankType.BLUE.getName(), Settings.Machines.tankCapacity);
        this.tankMgr.registerTank(TankType.WHITE.getName(), Settings.Machines.tankCapacity);

        this.tankMgr.addToWhitelist(TankType.RED.getName(), ModFluids.fluidDyeRed);
        this.tankMgr.addToWhitelist(TankType.YELLOW.getName(), ModFluids.fluidDyeYellow);
        this.tankMgr.addToWhitelist(TankType.BLUE.getName(), ModFluids.fluidDyeBlue);
        this.tankMgr.addToWhitelist(TankType.WHITE.getName(), ModFluids.fluidDyeWhite);

        /* Dont allow filling  */
        this.tankMgr.blockTankFillAll(TankType.RED.getName());
        this.tankMgr.blockTankFillAll(TankType.YELLOW.getName());
        this.tankMgr.blockTankFillAll(TankType.BLUE.getName());
        this.tankMgr.blockTankFillAll(TankType.WHITE.getName());

        LogHelper.info("Squeezer: " + this.tankMgr);
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
        return false;
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
    private static final int[] fakeAccessSlots = new int[0];
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {

        if (!this.isStructureValid())
            return fakeAccessSlots;

        /* All slots accessible from all sides */
        return accessSlots;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {

        if (!this.isStructureValid())
            return false;

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

        if (!this.isStructureValid())
            return false;

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

        SqueezerManager.SqueezerRecipe sr = (SqueezerManager.SqueezerRecipe)recipe;

        if (this.tankMgr.fill(TankType.RED.getName(), sr.getRedFluidStack(), false) == sr.getRedAmount() &&
            this.tankMgr.fill(TankType.YELLOW.getName(),  sr.getYellowFluidStack(), false) == sr.getYellowAmount() &&
            this.tankMgr.fill(TankType.BLUE.getName(), sr.getBlueFluidStack(), false) == sr.getBlueAmount() &&
            this.tankMgr.fill(TankType.WHITE.getName(), sr.getWhiteFluidStack(), false) == sr.getWhiteAmount()) {
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

        this.tankMgr.fill(TankType.RED.getName(), sr.getRedFluidStack(), true);
        this.tankMgr.fill(TankType.YELLOW.getName(), sr.getYellowFluidStack(), true);
        this.tankMgr.fill(TankType.BLUE.getName(), sr.getBlueFluidStack(), true);
        this.tankMgr.fill(TankType.WHITE.getName(), sr.getWhiteFluidStack(), true);
    }

    @Override
    public void consumeEnergy(int amount) {
        this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, false);
    }

    @Override
    public int getEnergyTick() {
        return Settings.Machines.squeezerRfTick;
    }

    @Override
    public IFactoryRecipe getRecipe() {
        return SqueezerManager.getRecipe(getStackInSlot(INPUT_SLOT));
    }

    @Override
    public void updateRunning(boolean running) {

    }
}
