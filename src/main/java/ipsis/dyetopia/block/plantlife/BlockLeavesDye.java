package ipsis.dyetopia.block.plantlife;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockLeavesDye extends BlockLeavesDYT {

    public BlockLeavesDye() {

        super();
        this.setBlockName(Names.Blocks.BLOCK_LEAVES_DYE);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    private static final int RENDER_RED = 11743532;
    private static final int RENDER_YELLOW = 14602026;
    private static final int RENDER_BLUE = 2437522;
    private static final int RENDER_PURE = 14188952;

    @SideOnly(Side.CLIENT)
    public int getRenderColor(int p_149741_1_)
    {
        if (p_149741_1_ == 0)
            return RENDER_RED;
        else if (p_149741_1_ == 1)
            return RENDER_YELLOW;
        else if (p_149741_1_ == 2)
            return RENDER_BLUE;
        else if (p_149741_1_ == 3)
            return RENDER_PURE;

        return ColorizerFoliage.getFoliageColorBasic();
    }

    /* TODO too simplistic */
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess iblockaccess, int x, int y, int z) {

        return getRenderColor(iblockaccess.getBlockMetadata(x, y, z));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {

        icons = new IIcon[2];
        icons[0] = ir.registerIcon(
                Textures.RESOURCE_PREFIX + "plantlife/" +
                        Names.Blocks.BLOCK_LEAVES_DYE + "_fancy");
        icons[1] = ir.registerIcon(
                Textures.RESOURCE_PREFIX + "plantlife/" +
                        Names.Blocks.BLOCK_LEAVES_DYE + "_plain");
    }

    @Override
    public boolean isOpaqueCube()
    {
        return Blocks.leaves.isOpaqueCube();
    }

    @Override
    public IIcon getIcon(int side, int meta) {

        /* Renderer is only updated for specific blcoks and not ours */
         return icons[Blocks.leaves.isOpaqueCube() ? 1 : 0];
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return true;
    }

    @Override
    public String[] func_150125_e() {

        // Not sure what this is for!
        return new String[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0 ; i < Names.Blocks.BLOCK_SAPLING_DYE_TYPES.length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    /* No drops */
    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {

        return new ArrayList<ItemStack>();
    }
}
