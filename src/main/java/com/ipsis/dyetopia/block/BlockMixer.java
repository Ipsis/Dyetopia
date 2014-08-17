package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.tileentity.TileEntityMixer;
import com.ipsis.dyetopia.tileentity.TileEntitySqueezer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMixer extends BlockDYTMultiBlock implements ITileEntityProvider {

    public BlockMixer() {
        super();
        this.setBlockName("mixer");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityMixer();
    }

    /* TODO on placed the texture should be forward only */
}
