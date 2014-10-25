package ipsis.dyetopia.block;

import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.tileentity.TileEntityMixer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockMixer extends BlockDYTMultiBlock implements ITileEntityProvider {

    public BlockMixer() {
        super();
        this.setBlockName("mixer");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityMixer();
    }

    @SideOnly(Side.CLIENT)
    IIcon formedIcon;
    IIcon blankIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        formedIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_formed")));
        blankIcon = iconRegister.registerIcon(Reference.MOD_ID + ":multi_blank");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {

        /**
         * As we override the IBlockAccess version this will only be called for the toolbar
         * Is is oriented as facing south
         */
        if (side == ForgeDirection.SOUTH.ordinal())
            return this.formedIcon;

        return this.blankIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityMixer) {

            TileEntityMixer mte = (TileEntityMixer) te;
            if (side == mte.getDirectionFacing().ordinal()) {
                if (mte.isStructureValid())
                    return formedIcon;
                else
                    return blockIcon;
            } else {
                return blankIcon;
            }
        }

        return blockIcon;
    }
}
