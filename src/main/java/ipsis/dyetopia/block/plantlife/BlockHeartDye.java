package ipsis.dyetopia.block.plantlife;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.block.BlockDYT;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockHeartDye extends BlockDYT {

    public BlockHeartDye() {

        super();
        this.setBlockName(Names.Blocks.BLOCK_HEART_DYE);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {

        icons = new IIcon[Names.Blocks.BLOCK_HEART_DYE_TYPES.length];

        for (int i = 0; i < Names.Blocks.BLOCK_HEART_DYE_TYPES.length; i++) {
            icons[i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX + "plantlife/" +
                            Names.Blocks.BLOCK_HEART_DYE + "." +
                            Names.Blocks.BLOCK_HEART_DYE_TYPES[i]);
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {

        if (meta < 0 || meta >= Names.Blocks.BLOCK_HEART_DYE_TYPES.length)
            return null;

        return icons[meta];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0 ; i < Names.Blocks.BLOCK_HEART_DYE_TYPES.length; i++)
            list.add(new ItemStack(item, 1, i));
    }


    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (metadata >= 0 && metadata < Names.Blocks.BLOCK_HEART_DYE_TYPES.length)
           ret.add(new ItemStack(ModItems.itemDyeBeans, world.rand.nextInt(fortune) + 1, metadata));

        return ret;
    }
}
