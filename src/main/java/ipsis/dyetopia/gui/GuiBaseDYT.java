package ipsis.dyetopia.gui;


import cofh.lib.gui.GuiBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.proxy.ClientProxy;
import ipsis.dyetopia.util.IconRegistry;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

public class GuiBaseDYT extends GuiBase {

    public GuiBaseDYT(Container container, ResourceLocation resourceLocation) {
        super(container, resourceLocation);
    }

    @Override
    public IIcon getIcon(String s) {
        return IconRegistry.getIcon(s);
    }

    protected String buildInfoString(String localTag, int num) {

        StringBuilder infoString = new StringBuilder();
        for (int i = 0; i < num; i++)
            infoString.append(StringHelper.localize(localTag + "." + Integer.toString(i)) + " ");

        return infoString.toString();
    }
}
