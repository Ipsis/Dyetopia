package ipsis.dyetopia.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementFluidTank;
import ipsis.dyetopia.reference.Textures;
import net.minecraftforge.fluids.IFluidTank;

public class ElementFluidTankDYT extends ElementFluidTank {

    public ElementFluidTankDYT(GuiBase gui, int posX, int posY, IFluidTank tank) {

        super(gui, posX, posY, tank, Textures.RESOURCE_PREFIX + Textures.Gui.FLUID_TANK);
    }
}
