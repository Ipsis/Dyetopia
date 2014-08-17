package com.ipsis.dyetopia.tileentity;

import com.ipsis.dyetopia.network.PacketHandler;
import com.ipsis.dyetopia.network.message.MessageTileEntityMultiBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;


public abstract class TileEntityMultiBlockBase extends TileEntityDYT implements ISidedInventory {

    protected int masterX;
    protected int masterY;
    protected int masterZ;
    protected boolean hasMaster;
    private boolean isMaster;

    public boolean isMaster() {
        return this.isMaster;
    }

    public boolean hasMaster() {
        return this.hasMaster;
    }

    public void setMaster(TileEntityMultiBlockMaster te) {

        if (te != null) {
            this.hasMaster = true;
            this.masterX = te.xCoord;
            this.masterY = te.yCoord;
            this.masterZ = te.zCoord;
        } else {
            this.hasMaster = false;
            this.masterX = 0;
            this.masterY = 0;
            this.masterZ = 0;
        }
    }

    public TileEntityMultiBlockBase() {
        this(false);
    }

    public TileEntityMultiBlockBase(boolean master) {
        super();
        this.isMaster = master;
        this.hasMaster = master;
    }

    public int getMasterX() {
        return this.masterX;
    }

    public int getMasterY() {
        return this.masterY;
    }

    public int getMasterZ() {
        return this.masterZ;
    }

    protected TileEntityMultiBlockMaster getMasterTE() {

        if (this.hasMaster) {
            TileEntity te = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
            if (te instanceof TileEntityMultiBlockMaster)
                return (TileEntityMultiBlockMaster) te;
        }

        return null;
    }

    /**
     * Processing
     */
    @Override
    public void updateEntity() {

           /* Slaves do nothing */
    }

    public void breakStructure() {
        if (!this.isMaster() && this.hasMaster) {
            TileEntity te = worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
            if (te instanceof TileEntityMultiBlockMaster)
                ((TileEntityMultiBlockMaster) te).breakStructure();
        }
    }

    /**
     * NBT and description packet
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("MasterX", this.masterX);
        nbttagcompound.setInteger("MasterY", this.masterY);
        nbttagcompound.setInteger("MasterZ", this.masterZ);
        nbttagcompound.setBoolean("HasMaster", this.hasMaster);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.masterX = nbttagcompound.getInteger("MasterX");
        this.masterY = nbttagcompound.getInteger("MasterY");
        this.masterZ = nbttagcompound.getInteger("MasterZ");
        this.hasMaster = nbttagcompound.getBoolean("HasMaster");
    }


    @Override
    public Packet getDescriptionPacket() {

        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityMultiBlock(this));
    }

    public void handleDescriptionPacket(MessageTileEntityMultiBlock msg) {

        this.masterX = msg.masterX;
        this.masterY = msg.masterY;
        this.masterZ = msg.masterZ;
        this.hasMaster = msg.hasMaster;
        this.setDirectionFacing(ForgeDirection.getOrientation(msg.facing));
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public String toString() {

        if (this.hasMaster)
            return "Master @ (" + this.masterX + ", " + this.masterY + ", " + this.masterZ + ")";
        else
            return "No Master";
    }

    /* ISidedInventory */
    private IInventory getInv() {

        if (!this.hasMaster())
            return null;

        TileEntityMultiBlockMaster m = this.getMasterTE();
        if (m != null && m instanceof IInventory)
            return (IInventory) m;

        return null;
    }

    private ISidedInventory getSidedInv() {

        if (!this.hasMaster())
            return null;

        TileEntityMultiBlockMaster m = this.getMasterTE();
        if (m != null && m instanceof ISidedInventory)
            return (ISidedInventory) m;

        return null;
    }

    @Override
    public void closeInventory() {

        IInventory te = getInv();
        if (te != null)
            te.closeInventory();
    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {

        IInventory te = getInv();
        if (te != null)
            return te.decrStackSize(slot, count);

        return null;
    }

    @Override
    public String getInventoryName() {

        IInventory te = getInv();
        if (te != null)
            return te.getInventoryName();

        return null;
    }

    @Override
    public int getInventoryStackLimit() {

        IInventory te = getInv();
        if (te != null)
            return te.getInventoryStackLimit();

        return 0;
    }

    @Override
    public int getSizeInventory() {

        IInventory te = getInv();
        if (te != null)
            return te.getSizeInventory();

        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {

        IInventory te = getInv();
        if (te != null)
            return te.getStackInSlot(slot);

        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {

        IInventory te = getInv();
        if (te != null)
            return te.getStackInSlotOnClosing(slot);

        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {

        IInventory te = getInv();
        if (te != null)
            return te.hasCustomInventoryName();

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {

        IInventory te = getInv();
        if (te != null)
            return te.isItemValidForSlot(slot, stack);

        return false;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {

        IInventory te = getInv();
        if (te != null)
            return te.isUseableByPlayer(player);

        return false;
    }

    @Override
    public void openInventory() {

        IInventory te = getInv();
        if (te != null)
            te.openInventory();
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {

        IInventory te = getInv();
        if (te != null)
            te.setInventorySlotContents(slot, stack);
    }


    private static final int[] fakeAccessSlots = new int[0];
    @Override
    public int[] getAccessibleSlotsFromSide(int side) {

        ISidedInventory te = getSidedInv();
        if (te != null)
            return te.getAccessibleSlotsFromSide(side);

        return fakeAccessSlots;
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack itemStack, int side) {

        ISidedInventory te = getSidedInv();
        if (te != null)
            return te.canInsertItem(slot, itemStack, side);

        return false;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack itemStack, int side) {

        ISidedInventory te = getSidedInv();
        if (te != null)
            return te.canExtractItem(slot, itemStack, side);

        return false;
    }
}
