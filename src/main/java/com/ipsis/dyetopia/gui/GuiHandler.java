package com.ipsis.dyetopia.gui;

import com.ipsis.dyetopia.gui.container.ContainerSqueezer;
import com.ipsis.dyetopia.tileentity.TileEntitySqueezer;
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
        }

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {

        TileEntity te = world.getTileEntity(x, y, z);

        if (te instanceof TileEntitySqueezer) {
            return new GuiSqueezer((TileEntitySqueezer)te, player);
        }
        return null;
    }
}
