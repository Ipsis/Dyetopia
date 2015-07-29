package ipsis.dyetopia.network.message;

import cofh.lib.util.helpers.FluidHelper;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import ipsis.dyetopia.gui.IGuiFluidSyncHandler;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraftforge.fluids.FluidStack;

import java.io.IOException;

public class MessageGuiFluidSync implements IMessage, IMessageHandler<MessageGuiFluidSync, IMessage> {

    public int tankId;
    public FluidStack fluidStack;

    public MessageGuiFluidSync() { }

    public MessageGuiFluidSync(int tankId, FluidStack fluidStack) {

        this.tankId = tankId;
        this.fluidStack = fluidStack;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.tankId = buf.readInt();
        try {
            this.fluidStack = FluidHelper.readFluidStackFromPacket(new ByteBufInputStream(buf));
        } catch (IOException e) {
            e.printStackTrace();
            this.fluidStack = null;
        }

    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.tankId);
        try {
            FluidHelper.writeFluidStackToPacket(this.fluidStack, new ByteBufOutputStream(buf));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IMessage onMessage(MessageGuiFluidSync message, MessageContext ctx) {

        EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
        if (player != null && player.openContainer != null && player.openContainer instanceof IGuiFluidSyncHandler)
            ((IGuiFluidSyncHandler) player.openContainer).handleGuiFluidSync(message);

        return null;
    }

    @Override
    public String toString() {

        return String.format("MessageGuiFluidSync - tankid:%d fluidStack:%s", this.tankId, this.fluidStack);
    }
}
