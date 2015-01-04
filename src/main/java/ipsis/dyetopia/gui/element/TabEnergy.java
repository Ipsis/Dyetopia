package ipsis.dyetopia.gui.element;

import cofh.api.tileentity.IEnergyInfo;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Textures;
import org.lwjgl.opengl.GL11;

import java.util.List;

/**
 * This is based off CoFHCore/src/main/java/cofh/core/gui/element/TabEnergy.java
 */
public class TabEnergy extends TabBase {

    IEnergyInfo energyInfo;

    public TabEnergy(GuiBase gui, int side, IEnergyInfo energyInfo) {

        super(gui, side);

        this.energyInfo = energyInfo;
        maxHeight = 92;
        maxWidth = 100;
        backgroundColor = Textures.Gui.ENERGY_TAB_BACKGROUND;

        if (side == LEFT)
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.TAB_LEFT, 256, 256);
        else
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.TAB_RIGHT, 256, 256);
    }

    @Override
    public void draw() {

        drawBackground();
        drawTabIcon("Icon_Energy");
        if (!isFullyOpened()) {
            return;
        }

        getFontRenderer().drawStringWithShadow(getTitle(), posXOffset() + 18, posY + 6, headerColor);

        getFontRenderer().drawStringWithShadow(StringHelper.localize(Lang.Gui.ENERGY_DRAW) + ":", posXOffset() + 6, posY + 20, subheaderColor);
        getFontRenderer().drawString(energyInfo.getInfoEnergyPerTick() + " RF/t", posXOffset() + 14, posY + 32, textColor);

        /* We never produce energy only receive/use it */
        getFontRenderer().drawStringWithShadow(StringHelper.localize(Lang.Gui.ENERGY_RX_TICK) + ":", posXOffset() + 6, posY + 44, subheaderColor);
        getFontRenderer().drawString(energyInfo.getInfoMaxEnergyPerTick() + " RF/t", posXOffset() + 14, posY + 56, textColor);

        getFontRenderer().drawStringWithShadow(StringHelper.localize(Lang.Gui.ENERGY_STORED) + ":", posXOffset() + 6, posY + 68, subheaderColor);
        getFontRenderer().drawString(energyInfo.getInfoEnergyStored() + " RF", posXOffset() + 14, posY + 80, textColor);

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private String getTitle() {

        return StringHelper.localize(Lang.Gui.TAB_ENERGY);
    }

    @Override
    public void addTooltip(List<String> list) {

        if (!isFullyOpened()) {
            list.add(getTitle());
            return;
        }
    }
}
