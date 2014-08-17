package com.ipsis.dyetopia.tileentity;

import cofh.lib.util.helpers.BlockHelper;
import cofh.lib.util.position.BlockPosition;
import com.ipsis.dyetopia.block.BlockDYTMultiBlock;
import com.ipsis.dyetopia.manager.MultiBlockPatternManager;
import com.ipsis.dyetopia.network.PacketHandler;
import com.ipsis.dyetopia.network.message.MessageTileEntityMultiBlock;
import com.ipsis.dyetopia.network.message.MessageTileEntityMultiBlockMaster;
import com.ipsis.dyetopia.util.LogHelper;
import com.ipsis.dyetopia.util.multiblock.MultiBlockPattern;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class TileEntityMultiBlockMaster extends TileEntityMultiBlockBase implements IInventory {

    private int tickCount;
    private static final int CHECK_VALID_TICKS = 40;

    private boolean structureValid;

    public TileEntityMultiBlockMaster() {
        super(true);
        this.structureValid = false;
    }

    public MultiBlockPattern getPattern() {
        return null;
    }

    protected ForgeDirection getPatternOrientation() {

        /**
         * Pattern is defined as looking at the multiblock.
         * TE facing is looking out from the multiblock.
         */
        return this.getDirectionFacing().getOpposite();
    }

    public boolean isStructureValid() { return this.structureValid; }

    /* Server->GUI sync only */
    public void setStructureValid(boolean b) { this.structureValid = b; }

    /**
     * Processing
     */
    private boolean validateStructure() {

        MultiBlockPattern pattern = getPattern();
        if (pattern == null)
            return false;

        for (int slice = 0; slice < pattern.getSlices(); slice++) {
            for (int row = 0; row < pattern.getRows(); row++) {
                for (int col = 0; col < pattern.getCols(); col++) {

                    BlockPosition p = new BlockPosition(
                            this.xCoord, this.yCoord, this.zCoord,
                            getPatternOrientation());
                    p.y += (slice - 1);
                    p.moveForwards(row);

                    if (col == 0)
                        p.moveLeft(1);
                    else if (col == 2)
                        p.moveRight(1);

                    ItemStack itemStack = pattern.getItemStackAt(slice, row, col);
                    Block b = this.worldObj.getBlock(p.x, p.y, p.z);

                    if (itemStack != null && !BlockHelper.isEqual(b, Block.getBlockFromItem(itemStack.getItem()))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private void updateStructure(boolean doForm) {

        MultiBlockPattern pattern = getPattern();
        if (pattern == null)
            return;

        for (int slice = 0; slice < pattern.getSlices(); slice++) {
            for (int row = 0 ; row < pattern.getRows(); row++) {
                for (int col = 0; col < pattern.getCols(); col++) {

                    BlockPosition p = new BlockPosition(
                            this.xCoord, this.yCoord, this.zCoord,
                            getPatternOrientation());
                    p.y += (slice - 1);
                    p.moveForwards(row);

                    if (col == 0)
                        p.moveLeft(1);
                    else if (col == 2)
                        p.moveRight(1);

                    Block b = this.worldObj.getBlock(p.x, p.y, p.z);
                    if (b instanceof BlockDYTMultiBlock) {
                        TileEntity te = this.worldObj.getTileEntity(p.x, p.y, p.z);
                        if (te instanceof TileEntityMultiBlockBase) {
                            if (doForm)
                                ((TileEntityMultiBlockBase)te).setMaster(this);
                            else
                                ((TileEntityMultiBlockBase)te).setMaster(null);
                            this.worldObj.markBlockForUpdate(p.x, p.y, p.z);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity() {

        if (worldObj.isRemote)
            return;

        this.tickCount++;

        if ((this.tickCount % CHECK_VALID_TICKS) == 0) {
            boolean isNowValid = validateStructure();
            if (this.structureValid != isNowValid) {
                updateStructure(isNowValid);
                this.structureValid = isNowValid;
                this.onStructureValidChanged(isNowValid);
            }
        }
    }

    public void onStructureValidChanged(boolean isNowValid) { }

    @Override
    public void breakStructure() {
        if (this.structureValid) {
            updateStructure(false);
            this.structureValid = false;
        }
    }

    /*************
     * IInventory
     */

    /* exists, but has no items in it */
    public ItemStack[] inventory = new ItemStack[0];

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        super.readFromNBT(nbttagcompound);

        NBTTagList nbttaglist = nbttagcompound.getTagList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int slot = nbttagcompound1.getByte("Slot") & 0xff;
            if (slot >= 0 && slot < inventory.length) {
                setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(nbttagcompound1));
            }
        }


    }

    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {

        super.writeToNBT(nbttagcompound);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack stack = getStackInSlot(i);

            if (stack != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                stack.writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    @Override
    public void closeInventory() {

    }

    @Override
    public ItemStack decrStackSize(int slot, int count) {

        if (inventory[slot] == null)
            return null;

        if (inventory[slot].stackSize <= count) {
            ItemStack s = inventory[slot];
            inventory[slot] = null;
            markDirty();
            return s;
        }

        ItemStack s = inventory[slot].splitStack(count);
        if (inventory[slot].stackSize <= 0)
            inventory[slot] = null;

        markDirty();
        return s;
    }

    @Override
    public String getInventoryName() {

        return null;
    }

    @Override
    public int getInventoryStackLimit() {

        return 64;
    }

    @Override
    public int getSizeInventory() {

        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {

        return inventory[slot];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {

        ItemStack s = inventory[slot];
        if (s != null)
            inventory[slot] = null;

        return s;
    }

    @Override
    public boolean hasCustomInventoryName() {

        return false;
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {

        return true;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {

        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {

        inventory[slot] = stack;
        if (stack != null && stack.stackSize > getInventoryStackLimit())
            stack.stackSize = getInventoryStackLimit();
        markDirty();
    }


    @Override
    public Packet getDescriptionPacket() {

        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityMultiBlockMaster(this));
    }

    public void handleDescriptionPacket(MessageTileEntityMultiBlockMaster msg) {

        this.masterX = msg.masterX;
        this.masterY = msg.masterY;
        this.masterZ = msg.masterZ;
        this.setStructureValid(msg.isStructureValid);
        this.setDirectionFacing(ForgeDirection.getOrientation(msg.facing));
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);

        LogHelper.info("Master: " + msg);
    }
}
