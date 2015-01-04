package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.gui.container.ContainerSqueezer;
import ipsis.dyetopia.gui.element.*;
import ipsis.dyetopia.reference.GuiLayout;
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
        this.name = StringHelper.localize(Lang.Gui.TITLE_SQUEEZER);

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTankDYT(this, GuiLayout.Squeezer.RED_TANK_X, GuiLayout.Squeezer.TANK_Y, this.squeezer.getTankMgr().getTank(TankType.RED.getName())));
        addElement(new ElementFluidTankDYT(this, GuiLayout.Squeezer.YELLOW_TANK_X, GuiLayout.Squeezer.TANK_Y, this.squeezer.getTankMgr().getTank(TankType.YELLOW.getName())));
        addElement(new ElementFluidTankDYT(this, GuiLayout.Squeezer.BLUE_TANK_X, GuiLayout.Squeezer.TANK_Y, this.squeezer.getTankMgr().getTank(TankType.BLUE.getName())));
        addElement(new ElementFluidTankDYT(this, GuiLayout.Squeezer.WHITE_TANK_X, GuiLayout.Squeezer.TANK_Y, this.squeezer.getTankMgr().getTank(TankType.WHITE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, GuiLayout.Squeezer.ENERGY_X, GuiLayout.Squeezer.ENERGY_Y, this.squeezer.getEnergyMgr().getEnergyStorage()));


        this.progress = ((ElementProgressBar)addElement(new ElementProgressBar(this, GuiLayout.Squeezer.PROGRESS_X, GuiLayout.Squeezer.PROGRESS_Y, ElementProgressBar.ProgressType.LEFT_TO_RIGHT)));
        addElement(progress);

        addTab(new TabEnergy(this, TabBase.RIGHT, this.squeezer));
        addTab(new TabInfo(this, TabBase.LEFT, buildInfoString(Lang.Gui.INFO_SQUEEZER)));
    }

    @Override
    protected void updateElementInformation() {

        this.progress.setQuantity(this.squeezer.getFactoryMgr().getScaledProgress(24));
    }
}
