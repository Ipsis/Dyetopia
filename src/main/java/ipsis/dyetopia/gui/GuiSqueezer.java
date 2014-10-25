package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import ipsis.dyetopia.gui.container.ContainerSqueezer;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.tileentity.TileEntitySqueezer;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiSqueezer extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.GUI_TEXTURE_BASE + "squeezer.png");

    private TileEntitySqueezer squeezer;
    private ElementDualScaled progress;

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
        addElement(new ElementFluidTank(this, 96, 12, this.squeezer.getTankMgr().getTank(TankType.RED.getName())));
        addElement(new ElementFluidTank(this, 114, 12, this.squeezer.getTankMgr().getTank(TankType.YELLOW.getName())));
        addElement(new ElementFluidTank(this, 132, 12, this.squeezer.getTankMgr().getTank(TankType.BLUE.getName())));
        addElement(new ElementFluidTank(this, 150, 12, this.squeezer.getTankMgr().getTank(TankType.WHITE.getName())));

        /* energy */
        addElement(new ElementEnergyStored(this, 7, 22, this.squeezer.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 28, 54).setMode(1).setBackground(false).setSize(24, 16).setTexture(Reference.GUI_PROGRESS_TEXTURE, 64, 64)));
        addElement(progress);
    }

    @Override
    protected void updateElementInformation() {

        this.progress.setQuantity(this.squeezer.getFactoryMgr().getScaledProgress(24));
    }
}
