package ipsis.dyetopia.block;

import ipsis.dyetopia.tileentity.TileEntityCasing;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCasing extends BlockDYTMultiBlock implements ITileEntityProvider {

    public BlockCasing() {
        super();
        this.setBlockName("casing");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityCasing();
    }
}