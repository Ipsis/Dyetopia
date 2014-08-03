package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import com.ipsis.dyetopia.tileentity.TileEntitySqueezer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSqueezer extends BlockDYTMultiBlock implements ITileEntityProvider {

    public BlockSqueezer() {
        super();
        this.setBlockName("squeezer");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntitySqueezer();
    }
}
