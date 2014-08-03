package com.ipsis.dyetopia.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;


public class TileEntityMultiBlockBase extends TileEntityDYT {


    protected int masterX;
    protected int masterY;
    protected int masterZ;
    protected boolean hasMaster;
    private boolean isMaster;

    public boolean isMaster() { return this.isMaster; }
    public boolean hasMaster() { return this.hasMaster; }
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
                ((TileEntityMultiBlockMaster)te).breakStructure();
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

        NBTTagCompound nbttagcompound = new NBTTagCompound();
        writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {

        readFromNBT(pkt.func_148857_g());
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }
}
