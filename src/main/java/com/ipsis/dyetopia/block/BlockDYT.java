package com.ipsis.dyetopia.block;

import com.ipsis.dyetopia.creative.CreativeTab;
import com.ipsis.dyetopia.reference.Reference;
import com.ipsis.dyetopia.tileentity.TileEntityDYT;
import com.ipsis.dyetopia.util.DirectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockDYT extends Block {

    public BlockDYT()
    {
        this(Material.rock);
    }

    public BlockDYT(Material material)
    {
        super(material);
        this.setCreativeTab(CreativeTab.DYT_TAB);
        this.setHardness(3.5F);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack itemStack)
    {
        if (!world.isRemote) {
            if (world.getTileEntity(x, y, z) instanceof TileEntityDYT) {
                ForgeDirection d = DirectionHelper.getFacing(entityLiving);
                ((TileEntityDYT) world.getTileEntity(x, y, z)).setDirectionFacing(d);
            }
        }
    }
}
