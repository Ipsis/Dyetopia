package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.plugins.nei.UsageManager;
import com.ipsis.dyetopia.reference.Reference;
import com.ipsis.dyetopia.tileentity.TileEntityMixer;
import com.ipsis.dyetopia.tileentity.TileEntityMultiBlockMaster;
import com.ipsis.dyetopia.tileentity.TileEntitySqueezer;
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
        this.setBlockName("squeezer");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntitySqueezer();
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
        if (te != null && te instanceof TileEntityMultiBlockMaster) {

            TileEntityMultiBlockMaster mte = (TileEntityMultiBlockMaster) te;
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
