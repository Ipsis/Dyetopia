package ipsis.dyetopia.block.plantlife;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class BlockLeavesDye extends BlockLeavesDYT {

    public BlockLeavesDye() {

        super();
        this.setBlockName(Names.BLOCK_LEAVES_DYE);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[][] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister ir) {

        icons = new IIcon[2][Names.BLOCK_SAPLING_DYE_TYPES.length];
        for (int i = 0; i < Names.BLOCK_SAPLING_DYE_TYPES.length; i++) {
            icons[0][i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX + "plantlife/" +
                            Names.BLOCK_LEAVES_DYE + "." +
                            Names.BLOCK_SAPLING_DYE_TYPES[i] + "_plain");
            icons[1][i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX + "plantlife/" +
                            Names.BLOCK_LEAVES_DYE + "." +
                            Names.BLOCK_SAPLING_DYE_TYPES[i] + "_fancy");
        }

    }

    @Override
    public IIcon getIcon(int side, int meta) {

        if (meta < 0 || meta >= Names.BLOCK_SAPLING_DYE_TYPES.length)
            return null;

        if (this.field_150121_P)
            return icons[1][meta];
        else
            return icons[0][meta];
    }

    @Override
    public String[] func_150125_e() {

        // Not sure what this is for!
        return new String[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0 ; i < Names.BLOCK_SAPLING_DYE_TYPES.length; i++)
            list.add(new ItemStack(item, 1, i));
    }
}
