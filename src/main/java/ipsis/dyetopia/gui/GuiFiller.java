package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import cofh.lib.gui.element.TabBase;
import ipsis.dyetopia.gui.container.ContainerFiller;
import ipsis.dyetopia.gui.element.*;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityFiller;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFiller extends GuiBaseDYT {

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
        addElement(new ElementFluidTankDYT(this, GuiLayout.Filler.PURE_TANK_X, GuiLayout.Filler.PURE_TANK_Y, this.filler.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, GuiLayout.Filler.ENERGY_X, GuiLayout.Filler.ENERGY_Y, this.filler.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementProgressBar)addElement(new ElementProgressBar(this, GuiLayout.Filler.PROGRESS_X, GuiLayout.Filler.PROGRESS_Y, ElementProgressBar.ProgressType.LEFT_TO_RIGHT)));
        addElement(progress);

        addTab(new TabEnergy(this, TabBase.RIGHT, this.filler));
        addTab(new TabInfo(this, TabBase.LEFT, buildInfoString(Lang.FILLER_INFO, Lang.FILLER_INFO_LEN)));
    }
}
