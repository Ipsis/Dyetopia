package ipsis.dyetopia.block;

import ipsis.dyetopia.tileentity.TileEntityPainter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPainter extends BlockDYTMachine {

    public BlockPainter() {

        super();
        this.setBlockName("painter");
    }


    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityPainter();
    }
}
