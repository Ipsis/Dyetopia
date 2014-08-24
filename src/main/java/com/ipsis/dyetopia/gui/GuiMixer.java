package com.ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import com.ipsis.dyetopia.gui.container.ContainerMixer;
import com.ipsis.dyetopia.gui.container.ContainerSqueezer;
import com.ipsis.dyetopia.reference.Reference;
import com.ipsis.dyetopia.tileentity.TileEntityMixer;
import com.ipsis.dyetopia.tileentity.TileEntitySqueezer;
import com.ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;


public class GuiMixer extends GuiBase {

    private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.GUI_TEXTURE_BASE + "mixer.png");

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
        addElement(new ElementFluidTank(this, 60, 12, this.mixer.getTankMgr().getTank(TankType.RED.getName())));
        addElement(new ElementFluidTank(this, 78, 12, this.mixer.getTankMgr().getTank(TankType.YELLOW.getName())));
        addElement(new ElementFluidTank(this, 96, 12, this.mixer.getTankMgr().getTank(TankType.BLUE.getName())));
        addElement(new ElementFluidTank(this, 114, 12, this.mixer.getTankMgr().getTank(TankType.WHITE.getName())));

        addElement(new ElementFluidTank(this, 150, 12, this.mixer.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStored(this, 7, 22, this.mixer.getEnergyMgr().getEnergyStorage()));

        this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 28, 54).setMode(1).setBackground(false).setSize(24, 16).setTexture(Reference.GUI_PROGRESS_TEXTURE, 64, 64)));
        addElement(progress);
    }

    @Override
    protected void updateElementInformation() {

        this.progress.setQuantity(this.mixer.getFactoryMgr().getScaledProgress(24));
    }
 }
