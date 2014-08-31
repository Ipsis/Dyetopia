package com.ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import com.ipsis.dyetopia.gui.container.ContainerFiller;
import com.ipsis.dyetopia.gui.container.ContainerPainter;
import com.ipsis.dyetopia.reference.Reference;
import com.ipsis.dyetopia.tileentity.TileEntityFiller;
import com.ipsis.dyetopia.tileentity.TileEntityPainter;
import com.ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiFiller extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.GUI_TEXTURE_BASE + "filler.png");

    private TileEntityFiller filler;
    private ElementDualScaled progress;

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
        addElement(new ElementFluidTank(this, 150, 12, this.filler.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStored(this, 7, 22, this.filler.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 28, 54).setMode(1).setBackground(false).setSize(24, 16).setTexture(Reference.GUI_PROGRESS_TEXTURE, 64, 64)));
        addElement(progress);
    }
}
