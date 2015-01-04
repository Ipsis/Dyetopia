package ipsis.dyetopia.gui;


import cofh.lib.gui.GuiBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.proxy.ClientProxy;
import ipsis.dyetopia.util.IconRegistry;
import ipsis.dyetopia.util.LogHelper;
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

    protected String buildInfoString(String baseTag) {

        StringBuilder infoString = new StringBuilder();
        if (!StringHelper.localize(baseTag).equals(baseTag)) {
            /* Only have one line */
            infoString.append(StringHelper.localize(baseTag));
        } else {
            int idx = 0;
            String tag = baseTag + "." + idx;
            String t = StringHelper.localize(baseTag + "." + idx);
            while (!t.equals(tag)) {
                infoString.append(t + " ");
                idx++;
                tag = baseTag + "." + idx;
                t = StringHelper.localize(tag);

                /* Someone might think they are funny and write a tome! */
                if (idx > 15)
                    break;
            }
        }

        return infoString.toString();
    }
}
