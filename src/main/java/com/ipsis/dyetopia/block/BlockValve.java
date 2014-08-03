package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockValve extends BlockDYTMultiBlock implements ITileEntityProvider{

    public BlockValve() {
        super();
        this.setBlockName("valve");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityMultiBlockBase();
    }
}
