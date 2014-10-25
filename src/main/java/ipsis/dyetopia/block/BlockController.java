package ipsis.dyetopia.block;

import ipsis.dyetopia.tileentity.TileEntityController;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockController extends BlockDYTMultiBlock implements ITileEntityProvider {

    public BlockController() {
        super();
        this.setBlockName("controller");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityController();
    }
}
