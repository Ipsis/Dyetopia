package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import ipsis.dyetopia.gui.container.ContainerPainter;
import ipsis.dyetopia.gui.element.ElementEnergyStoredDYT;
import ipsis.dyetopia.gui.element.ElementFluidTankDYT;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityPainter;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPainter  extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Textures.Gui.GUI_PAINTER);

    private TileEntityPainter painter;
    private ElementDualScaled progress;

    public GuiPainter(TileEntityPainter painter, EntityPlayer player) {

        super(new ContainerPainter(painter, player), TEXTURE);
        this.painter = painter;

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTankDYT(this, 150, 12, this.painter.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, 7, 22, this.painter.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 78, 34).setMode(1).setBackground(false).setSize(24, 16).setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.PROGRESS, 64, 64)));
        addElement(progress);

    }
}
