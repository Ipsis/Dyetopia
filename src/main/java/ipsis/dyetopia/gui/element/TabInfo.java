package ipsis.dyetopia.gui.element;

import cofh.api.tileentity.IEnergyInfo;
import cofh.lib.gui.GuiBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Textures;
import org.lwjgl.opengl.GL11;

/**
 * This is based off CoFHCore/src/main/java/cofh/core/gui/element/TabInfo.java
 */

public class TabInfo extends TabScrolledText {

    public TabInfo(GuiBase gui, int side, String infoString) {

        super(gui, side, infoString);
        backgroundColor = Textures.Gui.INFO_TAB_BACKGROUND;
    }

    @Override
    public String getIcon() {

        return "Icon_Info";
    }

    @Override
    public String getTitle() {

        return StringHelper.localize(Lang.Gui.TAB_INFO);
    }
}
