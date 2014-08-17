package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.Dyetopia;
import com.ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import com.ipsis.dyetopia.tileentity.TileEntityMultiBlockMaster;
import com.ipsis.dyetopia.util.LogHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockDYTMultiBlock extends BlockDYT implements ITileEntityProvider {

    public BlockDYTMultiBlock() {
        super();
    }

    @SideOnly(Side.CLIENT)
    IIcon formedIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        formedIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_formed")));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityMultiBlockBase) {
            if (((TileEntityMultiBlockBase)te).hasMaster())
                return formedIcon;
        }

		/* Assume everything else is the same icon */
        return blockIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float hitX, float hitY, float hitZ) {

        if (entityPlayer.isSneaking()) {
            return false;
        } else {
            if (!world.isRemote) {
                TileEntity te = world.getTileEntity(x, y, z);
                if (te instanceof TileEntityMultiBlockBase && (((TileEntityMultiBlockBase) te).hasMaster())) {
                    TileEntityMultiBlockBase mbb = (TileEntityMultiBlockBase)te;
                    entityPlayer.openGui(Dyetopia.instance, 0, world, mbb.getMasterX(), mbb.getMasterY(), mbb.getMasterZ());
                    return true;
                }
            }

            return false;
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

        if (!world.isRemote) {
            TileEntity te = world.getTileEntity(x, y, z);
            if (te instanceof TileEntityMultiBlockBase) {
                ((TileEntityMultiBlockBase)te).breakStructure();
            }
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

}
