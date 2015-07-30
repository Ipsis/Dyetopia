package ipsis.dyetopia.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.util.LogHelper;

/** Our standard progress bar */
public class ElementProgressBar extends ElementDualScaled {

    public enum ProgressType {

        BOTTOM_TO_TOP,
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
    }

    public ElementProgressBar(GuiBase gui, int posX, int posY, ProgressType type) {

        super(gui, posX, posY);
        this.setMode(type.ordinal());
        this.setBackground(false);
        this.setSize(24, 16);

        if (type == ProgressType.LEFT_TO_RIGHT)
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.PROGRESS_LEFT_RIGHT, 48, 16);
        else
            LogHelper.debug("ElementProgressBar: type with no texture selected");

    }
}
