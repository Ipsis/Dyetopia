package ipsis.dyetopia.block.plantlife;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.block.DYTBlocks;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockSaplingDye extends BlockSaplingDYT {

    public BlockSaplingDye() {

        super();
        this.setBlockName(Names.BLOCK_SAPLING_DYE);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {

        icons = new IIcon[Names.BLOCK_SAPLING_DYE_TYPES.length];
        for (int i = 0; i < Names.BLOCK_SAPLING_DYE_TYPES.length; i++) {
            icons[i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX + "plantlife/" +
                    Names.BLOCK_SAPLING_DYE + "." +
                    Names.BLOCK_SAPLING_DYE_TYPES[i]);
        }

    }

    @Override
    public IIcon getIcon(int side, int meta) {

        if (meta < 0 || meta >= Names.BLOCK_SAPLING_DYE_TYPES.length)
            return null;

        return icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0 ; i < Names.BLOCK_SAPLING_DYE_TYPES.length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    /* Disable IGrowable */
    @Override
    public boolean func_149851_a(World p_149851_1_, int p_149851_2_, int p_149851_3_, int p_149851_4_, boolean p_149851_5_)
    {
        return false;
    }

    @Override
    public boolean func_149852_a(World p_149852_1_, Random p_149852_2_, int p_149852_3_, int p_149852_4_, int p_149852_5_)
    {
        return false;
    }

    @Override
    public void func_149853_b(World p_149853_1_, Random p_149853_2_, int p_149853_3_, int p_149853_4_, int p_149853_5_)
    {
    }

    /* Fake IGrowable interface, these cannot be grown by bonemeal only dyemeal */
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
        int meta = world.getBlockMetadata(x, y, z);

        world.setBlockToAir(x, y, z);

        boolean grew = false;

        grew = DYTBlocks.dyeTree.generate2(world, rand, x, y, z, meta);

        if (!grew)
            world.setBlock(x, y, z, this);
    }
}
