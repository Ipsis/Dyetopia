package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import cofh.lib.gui.element.TabBase;
import ipsis.dyetopia.gui.container.ContainerSqueezer;
import ipsis.dyetopia.gui.element.*;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntitySqueezer;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSqueezer extends GuiBaseDYT {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Textures.Gui.GUI_SQUEEZER);

    private TileEntitySqueezer squeezer;
    private ElementProgressBar progress;

    public GuiSqueezer(TileEntitySqueezer squeezer, EntityPlayer player) {

        super(new ContainerSqueezer(squeezer, player), TEXTURE);
        this.squeezer = squeezer;

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTankDYT(this, 96, 12, this.squeezer.getTankMgr().getTank(TankType.RED.getName())));
        addElement(new ElementFluidTankDYT(this, 114, 12, this.squeezer.getTankMgr().getTank(TankType.YELLOW.getName())));
        addElement(new ElementFluidTankDYT(this, 132, 12, this.squeezer.getTankMgr().getTank(TankType.BLUE.getName())));
        addElement(new ElementFluidTankDYT(this, 150, 12, this.squeezer.getTankMgr().getTank(TankType.WHITE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, 7, 22, this.squeezer.getEnergyMgr().getEnergyStorage()));


        this.progress = ((ElementProgressBar)addElement(new ElementProgressBar(this, 61, 34, ElementProgressBar.ProgressType.LEFT_TO_RIGHT)));
        addElement(progress);

        addTab(new TabEnergy(this, TabBase.RIGHT, this.squeezer));
        addTab(new TabInfo(this, TabBase.LEFT, buildInfoString(Lang.SQUEEZER_INFO, Lang.SQUEEZER_INFO_LEN)));
    }

    @Override
    protected void updateElementInformation() {

        this.progress.setQuantity(this.squeezer.getFactoryMgr().getScaledProgress(24));
    }
}
