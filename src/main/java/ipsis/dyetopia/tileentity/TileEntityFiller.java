package ipsis.dyetopia.tileentity;

import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.item.ItemDyeGun;
import ipsis.dyetopia.manager.FactoryManager;
import ipsis.dyetopia.manager.IFactory;
import ipsis.dyetopia.manager.IFactoryRecipe;
import ipsis.dyetopia.manager.StamperManager;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.TankType;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

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

        this.factoryMgr.run();
    }

    /**
     * IFactory
     */
    public FactoryManager getFactoryMgr() { return this.factoryMgr; }

    private int consumedEnergy;

    @Override
    public boolean isOutputValid(IFactoryRecipe recipe) {

        ItemStack gun = getStackInSlot(INPUT_SLOT);
        if (recipe == null || gun == null || gun.getItem() != ModItems.itemDyeGun)
            return false;

        /* Stop when full */
        return !ItemDyeGun.isFull(gun);
    }

    @Override
    public boolean isEnergyAvailable(int amount) {
        return amount ==  this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, true);
    }

    @Override
    public void consumeInputs(IFactoryRecipe recipe) {

        if (recipe != null)
            this.getTankMgr().drain(TankType.PURE.getName(), 1, true);
    }

    @Override
    public void createOutputs(IFactoryRecipe recipe) {

        ItemStack gun = getStackInSlot(INPUT_SLOT);
        if (recipe == null || gun == null || gun.getItem() != ModItems.itemDyeGun)
                return;

        ItemDyeGun.fillGun(gun, 1);
    }

    @Override
    public void consumeEnergy(int amount) {
        this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, false);
    }

    @Override
    public int getEnergyTick() {
        return Settings.Machines.painterRfTick;
    }

    @Override
    public IFactoryRecipe getRecipe() {

        ItemStack in = getStackInSlot(INPUT_SLOT);
        if (in != null && in.getItem() == ModItems.itemDyeGun && !ItemDyeGun.isFull(in)) {
            return recipe;
        }

        return null;
    }

    @Override
    public void updateRunning(boolean running) {

    }

    private static FillerRecipe recipe = new FillerRecipe();

    public static class FillerRecipe implements IFactoryRecipe {

        @Override
        public int getEnergy() {
            return Settings.Machines.fillerRfRecipe;
        }
    }
}
