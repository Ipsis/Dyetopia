package com.ipsis.dyetopia.network.message;

import com.ipsis.dyetopia.tileentity.TileEntityDYT;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.tileentity.TileEntity;

public class MessageTileEntityDYT implements IMessage, IMessageHandler<MessageTileEntityDYT, IMessage> {

    public int x, y, z;
    public int facing;

    public MessageTileEntityDYT() {}

    public MessageTileEntityDYT(TileEntityDYT te)
    {
        this.x = te.xCoord;
        this.y = te.yCoord;
        this.z = te.zCoord;
        this.facing = (byte) te.getDirectionFacing().ordinal();
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.facing = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeByte(facing);
    }

    @Override
    public IMessage onMessage(MessageTileEntityDYT message, MessageContext ctx) {

        TileEntity te = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);

        if (te instanceof TileEntityDYT)
            ((TileEntityDYT)te).handleDescriptionPacket(message);

        return null;
    }

    @Override
    public String toString() {
        return String.format("MessageTileEntityDYT - x:%s, y:%s, z:%s, facing:%s", x, y, z, facing);
    }
}
