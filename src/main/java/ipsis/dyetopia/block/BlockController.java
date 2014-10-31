package ipsis.dyetopia.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityController;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockController extends BlockDYTMultiBlock implements ITileEntityProvider {

    public BlockController() {
        super();
        this.setBlockName(Names.Blocks.BLOCK_MACHINE_CONTROLLER);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityController();
    }

    @SideOnly(Side.CLIENT)
    private IIcon formedIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        formedIcon = iconRegister.registerIcon(
                Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CONTROLLER + ".Formed");
        blockIcon = iconRegister.registerIcon(
                Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CONTROLLER + ".Unformed");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityMultiBlockBase) {
            if (((TileEntityMultiBlockBase)te).hasMaster())
                return formedIcon;
        }

        return blockIcon;
    }
}
