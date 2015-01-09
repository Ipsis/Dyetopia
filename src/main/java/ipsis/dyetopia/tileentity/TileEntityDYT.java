package ipsis.dyetopia.tileentity;


import ipsis.dyetopia.network.PacketHandler;
import ipsis.dyetopia.network.message.MessageTileEntityDYT;
import ipsis.dyetopia.reference.Nbt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityDYT extends TileEntity {

    private ForgeDirection facing;
    private boolean status;

    public TileEntityDYT() {
        facing =  ForgeDirection.SOUTH;
        status = false;
    }

    public void setDirectionFacing(ForgeDirection facing) {
        this.facing = facing;
    }

    public ForgeDirection getDirectionFacing() { return this.facing; }

    public void setStatus(boolean status) {

        if (this.status != status) {
            this.status = status;
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }
    public boolean getStatus() { return this.status; }

    /**
     * NBT and description packet
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        nbttagcompound.setInteger(Nbt.Blocks.FACING, this.facing.ordinal());
        nbttagcompound.setBoolean(Nbt.Blocks.STATUS, this.status);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        facing = ForgeDirection.getOrientation(nbttagcompound.getInteger(Nbt.Blocks.FACING));
        status = nbttagcompound.getBoolean(Nbt.Blocks.STATUS);
    }

    @Override
    public Packet getDescriptionPacket() {

        return PacketHandler.INSTANCE.getPacketFrom(new MessageTileEntityDYT(this));
    }

    public void handleDescriptionPacket(MessageTileEntityDYT msg) {

        this.facing = ForgeDirection.getOrientation(msg.facing);
        this.status = msg.status;
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

}
