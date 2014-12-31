package ipsis.dyetopia.tileentity;

import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.item.ItemDyeGun;
import ipsis.dyetopia.manager.FactoryManager;
import ipsis.dyetopia.manager.IFactory;
import ipsis.dyetopia.manager.IFactoryRecipe;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.TankType;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityFiller extends TileEntityMachinePureDye implements ISidedInventory, IFactory {

    public static final int INPUT_SLOT = 0;

    private FactoryManager factoryMgr;

    public TileEntityFiller() {

        super(Settings.Machines.tankCapacity);
        inventory = new ItemStack[1];
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

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {

        if (slot == INPUT_SLOT && stack != null && stack.getItem() == ModItems.itemDyeGun)
            return true;

        return false;
    }


    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote)
            return;

        ItemStack itemStack = getStackInSlot(INPUT_SLOT);
        if (itemStack == null || itemStack.getItem() != ModItems.itemDyeGun)
            return;

        if (ItemDyeGun.isFull(itemStack))
            return;

        if (Settings.Machines.fillerRfTick != this.energyMgr.extractEnergy(ForgeDirection.DOWN, Settings.Machines.fillerRfTick, true))
            return;

        FluidStack fs = this.getTankMgr().drain(TankType.PURE.getName(), Settings.Machines.fillerDyeTick, false);
        if (fs == null || fs.amount != Settings.Machines.fillerDyeTick)
            return;

        int filled = ItemDyeGun.fillGun(itemStack, Settings.Machines.fillerDyeTick, true);
        this.getTankMgr().drain(TankType.PURE.getName(), filled, true);

        this.energyMgr.extractEnergy(ForgeDirection.DOWN, Settings.Machines.fillerRfTick, false);

        /* We never run the factory before of the above code */
    }

    /**
     * IFactory
     */
    public FactoryManager getFactoryMgr() { return this.factoryMgr; }

    private int consumedEnergy;

    @Override
    public boolean isOutputValid(IFactoryRecipe recipe) {
        return false;
    }

    @Override
    public boolean isEnergyAvailable(int amount) {
        return false;
    }

    @Override
    public void consumeInputs(IFactoryRecipe recipe) {
        /* NA */
    }

    @Override
    public void createOutputs(IFactoryRecipe recipe) {
        /* NA */
    }

    @Override
    public void consumeEnergy(int amount) {
        /* NA */
    }

    @Override
    public int getEnergyTick() {
        return 0;
    }

    @Override
    public IFactoryRecipe getRecipe() {
        return null;
    }

    @Override
    public void updateRunning(boolean running) {

    }

    public int getInfoEnergyPerTick() {
        return Settings.Machines.fillerRfTick;
    }
}
