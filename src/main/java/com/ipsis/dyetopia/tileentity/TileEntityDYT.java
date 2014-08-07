package com.ipsis.dyetopia.tileentity;


import com.ipsis.dyetopia.network.PacketHandler;
import com.ipsis.dyetopia.network.message.MessageTileEntityDYT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDYT extends TileEntity {

    private ForgeDirection facing;

    public TileEntityDYT() {
        facing =  ForgeDirection.SOUTH;
    }

    public void setDirectionFacing(ForgeDirection facing) {
        this.facing = facing;
    }

    public ForgeDirection getDirectionFacing() { return this.facing; }

    /**
     * NBT and description packet
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger("Facing", this.facing.ordinal());
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        facing = ForgeDirection.getOrientation(nbttagcompound.getInteger("Facing"));
    }

    @Override
    public Packet getDescriptionPacket() {

        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityDYT(this));
    }

    public void handleDescriptionPacket(MessageTileEntityDYT msg) {

        this.facing = ForgeDirection.getOrientation(msg.facing);
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

}
