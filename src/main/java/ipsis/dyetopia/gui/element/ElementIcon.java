package ipsis.dyetopia.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementBase;
import net.minecraft.util.IIcon;

public class ElementIcon extends ElementBase {

    protected IIcon icon;

    public ElementIcon(GuiBase gui, int posX, int posY) {

        super(gui, posX, posY);
    }

    @Override
    public void drawBackground(int i, int i2, float v) {

    }

    @Override
    public void drawForeground(int i, int i2) {

        if (icon != null)
            gui.drawIcon(icon, posX, posY, 1);
    }

    public ElementIcon setIcon(IIcon icon) {

        this.icon = icon;
        return this;
    }
}
