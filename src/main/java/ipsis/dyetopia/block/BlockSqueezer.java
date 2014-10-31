package ipsis.dyetopia.block;

import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockMaster;
import ipsis.dyetopia.tileentity.TileEntitySqueezer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSqueezer extends BlockDYTMultiBlock implements ITileEntityProvider {

    public BlockSqueezer() {
        super();
        this.setBlockName(Names.Blocks.BLOCK_MACHINE_SQUEEZER);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntitySqueezer();
    }

    @SideOnly(Side.CLIENT)
    IIcon formedIcon;
    @SideOnly(Side.CLIENT)
    IIcon formedActiveIcon;
    @SideOnly(Side.CLIENT)
    IIcon casingFormedIcon;
    @SideOnly(Side.CLIENT)
    IIcon casingUnformedIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_SQUEEZER + ".Unformed");
        formedIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_SQUEEZER + ".Formed");
        formedActiveIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_SQUEEZER + ".Formed.Active");
        casingFormedIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CASING + ".Formed");
        casingUnformedIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CASING + ".Unformed");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {

        /**
         * As we override the IBlockAccess version this will only be called for the toolbar
         * Is is oriented as facing south
         */
        if (side == ForgeDirection.SOUTH.ordinal())
            return blockIcon;

        return casingUnformedIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityMultiBlockMaster) {

            TileEntityMultiBlockMaster mte = (TileEntityMultiBlockMaster) te;
            if (side == mte.getDirectionFacing().ordinal()) {
                if (mte.isStructureValid())
                    return formedIcon; // TODO check active
                else
                    return blockIcon;
            } else {
                return casingUnformedIcon;
            }
        }

        return casingUnformedIcon;
    }
}
