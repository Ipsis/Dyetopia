package com.ipsis.dyetopia.gui.container;

import com.ipsis.dyetopia.tileentity.TileEntitySqueezer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class ContainerSqueezer extends Container {

    private TileEntitySqueezer squeezer;

    public ContainerSqueezer(TileEntitySqueezer squeezer, EntityPlayer player) {
        this.squeezer = squeezer;

    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return this.squeezer.isUseableByPlayer(player);
    }
}
