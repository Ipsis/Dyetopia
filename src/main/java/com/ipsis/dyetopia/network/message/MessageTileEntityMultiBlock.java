package com.ipsis.dyetopia.network.message;

import com.ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;


public class MessageTileEntityMultiBlock implements IMessage, IMessageHandler<MessageTileEntityMultiBlock, IMessage> {

    public int x, y, z;
    public int facing;
    public int masterX, masterY, masterZ;
    public boolean hasMaster;

    public MessageTileEntityMultiBlock() {}

    public MessageTileEntityMultiBlock(TileEntityMultiBlockBase te)
    {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;
        this.facing = (byte) te.getDirectionFacing().ordinal();
        this.masterX = te.getMasterX();
        this.masterY = te.getMasterY();
        this.masterZ = te.getMasterZ();
        this.hasMaster = te.hasMaster();
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.facing = buf.readByte();
        this.masterX = buf.readInt();
        this.masterY = buf.readInt();
        this.masterZ = buf.readInt();
        this.hasMaster = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(facing);
        buf.writeInt(masterX);
        buf.writeInt(masterY);
        buf.writeInt(masterZ);
        buf.writeBoolean(hasMaster);
    }

    @Override
    public IMessage onMessage(MessageTileEntityMultiBlock message, MessageContext ctx) {

        TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (te instanceof TileEntityMultiBlockBase)
            ((TileEntityMultiBlockBase)te).handleDescriptionPacket(message);

        return null;
    }

    @Override
    public String toString()
    {
        return String.format("MessageTileEntityMultiBlock - x:%s, y:%s, z:%s, facing:%s", x, y, z, facing);
    }
}
