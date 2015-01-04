package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import cofh.lib.gui.element.TabBase;
import ipsis.dyetopia.gui.container.ContainerMixer;
import ipsis.dyetopia.gui.element.*;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityMixer;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiMixer extends GuiBaseDYT {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Textures.Gui.GUI_MIXER);

    private TileEntityMixer mixer;
    private ElementProgressBar progress;

    public GuiMixer(TileEntityMixer mixer, EntityPlayer player) {

        super(new ContainerMixer(mixer, player), TEXTURE);
        this.mixer = mixer;

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTankDYT(this, GuiLayout.Mixer.RED_TANK_X, GuiLayout.Mixer.TANK_Y, this.mixer.getTankMgr().getTank(TankType.RED.getName())));
        addElement(new ElementFluidTankDYT(this, GuiLayout.Mixer.YELLOW_TANK_X, GuiLayout.Mixer.TANK_Y, this.mixer.getTankMgr().getTank(TankType.YELLOW.getName())));
        addElement(new ElementFluidTankDYT(this, GuiLayout.Mixer.BLUE_TANK_X, GuiLayout.Mixer.TANK_Y, this.mixer.getTankMgr().getTank(TankType.BLUE.getName())));
        addElement(new ElementFluidTankDYT(this, GuiLayout.Mixer.WHITE_TANK_X, GuiLayout.Mixer.TANK_Y, this.mixer.getTankMgr().getTank(TankType.WHITE.getName())));

        addElement(new ElementFluidTankDYT(this, GuiLayout.Mixer.PURE_TANK_X, GuiLayout.Mixer.TANK_Y, this.mixer.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, GuiLayout.Mixer.ENERGY_X, GuiLayout.Mixer.ENERGY_Y, this.mixer.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementProgressBar)addElement(new ElementProgressBar(this, GuiLayout.Mixer.PROGRESS_X, GuiLayout.Mixer.PROGRESS_Y, ElementProgressBar.ProgressType.LEFT_TO_RIGHT)));
        addElement(progress);

        addTab(new TabEnergy(this, TabBase.RIGHT, this.mixer));
        addTab(new TabInfo(this, TabBase.LEFT, buildInfoString(Lang.Gui.INFO_MIXER)));
    }

    @Override
    protected void updateElementInformation() {

        this.progress.setQuantity(this.mixer.getFactoryMgr().getScaledProgress(24));
    }
 }
