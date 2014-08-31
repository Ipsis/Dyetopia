package com.ipsis.dyetopia.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityPainter extends TileEntityMachinePureDye {

    private static final int TANK_CAPACITY = 40000;

    public TileEntityPainter() {

        super(TANK_CAPACITY);
        inventory = new ItemStack[1];
    }

    /**
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
    }
}
