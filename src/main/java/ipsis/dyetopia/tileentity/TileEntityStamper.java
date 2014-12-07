package ipsis.dyetopia.tileentity;

import ipsis.dyetopia.gui.GuiStamper;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.manager.*;
import ipsis.dyetopia.network.PacketHandler;
import ipsis.dyetopia.network.message.MessageGuiWidget;
import ipsis.dyetopia.reference.GuiIds;
import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import ipsis.dyetopia.util.TankType;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

public class TileEntityStamper extends TileEntityMachinePureDye implements ISidedInventory, IFactory {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT = 1;

    private FactoryManager factoryMgr;

    public TileEntityStamper() {

        super(Settings.Machines.tankCapacity);
        inventory = new ItemStack[2];
        factoryMgr = new FactoryManager(this);
        this.currSelected = StamperManager.getFirst();
    }

    /**
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        this.factoryMgr.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("consumedEnergy", this.consumedEnergy);
        nbttagcompound.setInteger("currSelected", this.currSelected.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.factoryMgr.readFromNBT(nbttagcompound);

        this.consumedEnergy = nbttagcompound.getInteger("consumedEnergy");
        this.currSelected =  DyeHelper.DyeType.getDye(nbttagcompound.getInteger("currSelected"));

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

        if (slot == INPUT_SLOT && stack != null && stack.getItem() == ModItems.itemDyeBlank)
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

        if (recipe == null || !(recipe instanceof StamperManager.StamperRecipe))
            return false;

        StamperManager.StamperRecipe sr = (StamperManager.StamperRecipe)recipe;

        FluidStack fs = this.getTankMgr().drain(TankType.PURE.getName(), sr.getPureAmount(), false);
        if (fs == null || fs.amount != sr.getPureAmount())
            return false;

        ItemStack curr = getStackInSlot(OUTPUT_SLOT);
        if (curr == null)
            return true;

        if (curr.isItemEqual(sr.getOutput()) && curr.stackSize + 1 <= curr.getMaxStackSize())
            return true;

        return false;
    }

    @Override
    public boolean isEnergyAvailable(int amount) {
        return amount ==  this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, true);
    }

    @Override
    public void consumeInputs(IFactoryRecipe recipe) {

        if (recipe == null || !(recipe instanceof StamperManager.StamperRecipe))
            return;

        StamperManager.StamperRecipe sr = (StamperManager.StamperRecipe)recipe;
        decrStackSize(INPUT_SLOT, 1);

        this.getTankMgr().drain(TankType.PURE.getName(), sr.getPureAmount(), true);
    }

    @Override
    public void createOutputs(IFactoryRecipe recipe) {

        if (recipe == null || !(recipe instanceof StamperManager.StamperRecipe))
            return;

        StamperManager.StamperRecipe sr = (StamperManager.StamperRecipe)recipe;
        ItemStack out = getStackInSlot(OUTPUT_SLOT);
        if (out == null) {
            out = sr.getOutput();
            setInventorySlotContents(OUTPUT_SLOT, out);
        } else {
            out.stackSize++;
            if (out.stackSize > out.getMaxStackSize())
                out.stackSize = out.getMaxStackSize();
        }
    }

    @Override
    public void consumeEnergy(int amount) {
        this.energyMgr.extractEnergy(ForgeDirection.DOWN, amount, false);
    }

    @Override
    public int getEnergyTick() {
        return Settings.Machines.stamperRfTick;
    }

    @Override
    public IFactoryRecipe getRecipe() {

        ItemStack in = getStackInSlot(INPUT_SLOT);
        if (in == null || in.getItem() != ModItems.itemDyeBlank || in.stackSize <= 0)
            return null;

        return StamperManager.getRecipe(currSelected);
    }

    @Override
    public void updateRunning(boolean running) {

    }

    /**
     * Gui
     */
    private DyeHelper.DyeType currSelected;
    public void incSelected() {

        if (worldObj.isRemote) {

            this.currSelected = StamperManager.getNext(this.currSelected);

            PacketHandler.INSTANCE.sendToServer(
                    new MessageGuiWidget(GuiIds.GUI_STAMPER, GuiIds.GUI_CTRL_BUTTON,
                            GuiStamper.BUTTON_UP_ID, 0, 0));
        }
    }

    public void decSelected() {

        if (worldObj.isRemote) {

            this.currSelected = StamperManager.getPrev(this.currSelected);

            PacketHandler.INSTANCE.sendToServer(
                    new MessageGuiWidget(GuiIds.GUI_STAMPER, GuiIds.GUI_CTRL_BUTTON,
                            GuiStamper.BUTTON_DN_ID, 0, 0));
        }
    }

    public DyeHelper.DyeType getCurrSelected() {

        return this.currSelected;
    }

    public void setNextCurrSelected() {

        this.currSelected = StamperManager.getNext(this.currSelected);
    }

    public void setPrevCurrSelected() {

        this.currSelected = StamperManager.getPrev(this.currSelected);
    }

    /* GUI updating only! */
    public void setCurrSelected(DyeHelper.DyeType dye) {
        this.currSelected = dye;
    }
}
