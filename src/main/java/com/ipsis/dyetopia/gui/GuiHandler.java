package com.ipsis.dyetopia.gui;

import com.ipsis.dyetopia.gui.container.*;
import com.ipsis.dyetopia.tileentity.*;
import com.ipsis.dyetopia.util.LogHelper;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity te = world.getTileEntity(x, y, z);

        if (te instanceof TileEntitySqueezer) {
            return new ContainerSqueezer((TileEntitySqueezer)te, player);
        } else if (te instanceof TileEntityMixer) {
            return new ContainerMixer((TileEntityMixer)te, player);
        } else if (te instanceof TileEntityPainter) {
            return new ContainerPainter((TileEntityPainter)te, player);
        } else if (te instanceof TileEntityStamper) {
            return new ContainerStamper((TileEntityStamper)te, player);
        } else if (te instanceof TileEntityFiller) {
            return new ContainerFiller((TileEntityFiller)te, player);
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity te = world.getTileEntity(x, y, z);

        if (te instanceof TileEntitySqueezer) {
            return new GuiSqueezer((TileEntitySqueezer)te, player);
        } else if (te instanceof TileEntityMixer) {
            return new GuiMixer((TileEntityMixer)te, player);
        } else if (te instanceof TileEntityPainter) {
            return new GuiPainter((TileEntityPainter)te, player);
        }  else if (te instanceof TileEntityStamper) {
            return new GuiStamper((TileEntityStamper)te, player);
        } else if (te instanceof TileEntityFiller) {
            return new GuiFiller((TileEntityFiller)te, player);
        }

        return null;
    }
}
