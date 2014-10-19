package com.ipsis.dyetopia.block.trees;

import com.ipsis.dyetopia.Dyetopia;
import com.ipsis.dyetopia.block.BlockDYT;
import com.ipsis.dyetopia.block.DYTBlocks;
import com.ipsis.dyetopia.creative.CreativeTab;
import com.ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

import java.util.List;
import java.util.Random;

import static net.minecraftforge.common.EnumPlantType.Plains;

public class BlockDyeSapling extends BlockDYT implements IPlantable {

    public BlockDyeSapling()
    {
        this.setHardness(0.0F);
        this.setStepSound(Block.soundTypeGrass);
        this.setCreativeTab(CreativeTab.DYT_TAB);
        this.setBlockName("saplingDye");

        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        String name = getUnwrappedUnlocalizedName(this.getUnlocalizedName());
        name = name.substring(name.indexOf(":") + 1);
        blockIcon = iconRegister.registerIcon(String.format(Reference.MOD_ID + ":tree/" + name));
    }


    @Override
    public IIcon getIcon(int side, int metadata)
    {
        return blockIcon;
    }

    /**
     * From BlockBush
     * We are not a growable sapling in the normal sense
     */

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 1;
    }

    /* IGrowable-ish */
    public boolean canGrow(World world, int x, int y, int z, boolean isRemote)
    {
        return true;
    }

    public boolean hasGrown(World world, Random rand, int x, int y, int z)
    {
        return true;
    }

    public void doGrow(World world, Random rand, int x, int y, int z)
    {
        world.setBlockToAir(x, y, z);
        if (!DYTBlocks.dyeTree.generate2(world, rand, x, y, z))
            world.setBlock(x, y, z, this);
    }

    /* IPlantable */
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, int x, int y, int z) {
        return Plains;
    }

    @Override
    public Block getPlant(IBlockAccess world, int x, int y, int z) {
        return this;
    }

    @Override
    public int getPlantMetadata(IBlockAccess world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }
}
