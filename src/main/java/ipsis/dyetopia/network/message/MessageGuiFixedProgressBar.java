package ipsis.dyetopia.network.message;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityClientPlayerMP;

/**
 * This is based off Railcraft's fixing of the updateProgressBar packet.
 * In the original code you had int id, int data but they were both shorts.
 * Therefore CovertJaguar created his own packet that calls into the same handler when it is received.
 * This is great as it means you can pass the Fluid amounts over in 32-bits rather than
 * splitting it into 2*16-bit values in two different messages
 */
public class MessageGuiFixedProgressBar implements IMessage, IMessageHandler<MessageGuiFixedProgressBar, IMessage> {

    public int guiId;
    public int id;
    public int data;

    public MessageGuiFixedProgressBar() { }

    public MessageGuiFixedProgressBar(int guiId, int id, int data) {

        this.guiId = guiId;
        this.id = id;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {

        this.guiId = buf.readInt();
        this.id = buf.readInt();
        this.data = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {

        buf.writeInt(this.guiId);
        buf.writeInt(this.id);
        buf.writeInt(this.data);
    }

    @Override
    public IMessage onMessage(MessageGuiFixedProgressBar message, MessageContext ctx) {

        EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
        if (player != null && player.openContainer != null && player.openContainer.windowId == message.guiId)
            player.openContainer.updateProgressBar(message.id, message.data);

        return null;
    }
}
