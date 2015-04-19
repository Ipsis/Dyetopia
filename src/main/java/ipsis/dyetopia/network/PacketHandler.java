package ipsis.dyetopia.network;

import ipsis.dyetopia.network.message.*;
import ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ipsis.dyetopia.util.LogHelper;

public class PacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD_ID.toLowerCase());

    private enum ModMessage {
        GUI_WIDGET(MessageGuiWidget.class, Side.SERVER),
        GUI_FIXED_PROGRESS_BASE(MessageGuiFixedProgressBar.class, Side.CLIENT),
        GUI_FLUID_SYNC(MessageGuiFluidSync.class, Side.CLIENT),
        TE_DESCRIPTION(MessageTileEntityDYT.class, Side.CLIENT),
        TE_MULTIBLOCK(MessageTileEntityMultiBlock.class, Side.CLIENT),
        TE_MULTIBLOCK_MASTER(MessageTileEntityMultiBlockMaster.class, Side.CLIENT);

        private Class clazz;
        private Side side;
        private ModMessage(Class clazz, Side side) {
            this.clazz = clazz;
            this.side = side;
        }

        public Class getClazz() { return this.clazz; }
        public Side getSide() { return this.side; }
    }

    public static void init() {

        for (ModMessage m : ModMessage.values())
            INSTANCE.registerMessage(m.getClazz(), m.getClazz(), m.ordinal(), m.getSide());
    }
}
