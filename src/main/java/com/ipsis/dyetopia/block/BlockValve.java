package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import com.ipsis.dyetopia.tileentity.TileEntityValve;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockValve extends BlockDYTMultiBlock implements ITileEntityProvider{

    public BlockValve() {
        super();
        this.setBlockName("valve");
    }

    @SideOnly(Side.CLIENT)
    IIcon[] formedColorIcons;
    IIcon formedIcon;

    @SideOnly(Side.CLIENT)
    private String[] colors = { "red", "yellow", "blue", "white" };

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));

        int i = 0;
        formedColorIcons = new IIcon[colors.length];
        for (String s : colors) {
            formedColorIcons[i] = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_" + s + "_formed")));
            i++;
        }
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
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityValve();
    }
}
