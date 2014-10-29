package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import ipsis.dyetopia.gui.container.ContainerFiller;
import ipsis.dyetopia.gui.element.ElementEnergyStoredDYT;
import ipsis.dyetopia.gui.element.ElementFluidTankDYT;
import ipsis.dyetopia.gui.element.ElementProgressBar;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityFiller;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFiller extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Textures.Gui.GUI_FILLER);

    private TileEntityFiller filler;
    private ElementProgressBar progress;

    public GuiFiller(TileEntityFiller filler, EntityPlayer player) {

        super(new ContainerFiller(filler, player), TEXTURE);
        this.filler = filler;

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTankDYT(this, 150, 12, this.filler.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, 7, 22, this.filler.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementProgressBar)addElement(new ElementProgressBar(this, 28, 54, ElementProgressBar.ProgressType.LEFT_TO_RIGHT)));
        addElement(progress);
    }
}
