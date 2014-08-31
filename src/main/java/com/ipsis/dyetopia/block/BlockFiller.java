package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.tileentity.TileEntityFiller;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockFiller extends BlockDYTMachine {

    public BlockFiller() {

        super();
        this.setBlockName("filler");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityFiller();
    }
}
