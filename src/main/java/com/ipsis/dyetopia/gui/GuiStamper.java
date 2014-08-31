package com.ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import com.ipsis.dyetopia.gui.container.ContainerPainter;
import com.ipsis.dyetopia.gui.container.ContainerStamper;
import com.ipsis.dyetopia.reference.Reference;
import com.ipsis.dyetopia.tileentity.TileEntityPainter;
import com.ipsis.dyetopia.tileentity.TileEntityStamper;
import com.ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiStamper extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.GUI_TEXTURE_BASE + "stamper.png");

    private TileEntityStamper stamper;
    private ElementDualScaled progress;

    public GuiStamper(TileEntityStamper stamper, EntityPlayer player) {

        super(new ContainerStamper(stamper, player), TEXTURE);
        this.stamper = stamper;

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTank(this, 150, 12, this.stamper.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStored(this, 7, 22, this.stamper.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 28, 54).setMode(1).setBackground(false).setSize(24, 16).setTexture(Reference.GUI_PROGRESS_TEXTURE, 64, 64)));
        addElement(progress);
    }
}
