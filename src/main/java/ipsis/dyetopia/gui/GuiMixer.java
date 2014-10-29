package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import ipsis.dyetopia.gui.container.ContainerMixer;
import ipsis.dyetopia.gui.element.ElementEnergyStoredDYT;
import ipsis.dyetopia.gui.element.ElementFluidTankDYT;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityMixer;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;


public class GuiMixer extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Textures.Gui.GUI_MIXER);

    private TileEntityMixer mixer;
    private ElementDualScaled progress;

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
        addElement(new ElementFluidTankDYT(this, 42, 12, this.mixer.getTankMgr().getTank(TankType.RED.getName())));
        addElement(new ElementFluidTankDYT(this, 60, 12, this.mixer.getTankMgr().getTank(TankType.YELLOW.getName())));
        addElement(new ElementFluidTankDYT(this, 78, 12, this.mixer.getTankMgr().getTank(TankType.BLUE.getName())));
        addElement(new ElementFluidTankDYT(this, 96, 12, this.mixer.getTankMgr().getTank(TankType.WHITE.getName())));

        addElement(new ElementFluidTankDYT(this, 150, 12, this.mixer.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, 7, 22, this.mixer.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 120, 33).setMode(1).setBackground(false).setSize(24, 16).setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.PROGRESS, 64, 64)));
        addElement(progress);
    }

    @Override
    protected void updateElementInformation() {

        this.progress.setQuantity(this.mixer.getFactoryMgr().getScaledProgress(24));
    }
 }
