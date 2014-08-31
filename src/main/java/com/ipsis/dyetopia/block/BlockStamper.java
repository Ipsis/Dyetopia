package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.tileentity.TileEntityStamper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockStamper extends BlockDYTMachine {

    public BlockStamper() {

        super();
        this.setBlockName("stamper");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityStamper();
    }
}
