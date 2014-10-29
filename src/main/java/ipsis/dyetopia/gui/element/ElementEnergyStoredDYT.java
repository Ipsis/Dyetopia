package ipsis.dyetopia.gui.element;

import cofh.api.energy.IEnergyStorage;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementEnergyStored;
import ipsis.dyetopia.reference.Textures;

public class ElementEnergyStoredDYT extends ElementEnergyStored {

    public ElementEnergyStoredDYT(GuiBase gui, int posX, int posY, IEnergyStorage storage) {

        super(gui, posX, posY, storage);
        this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.ENERGY, 32, 64);
    }
}
