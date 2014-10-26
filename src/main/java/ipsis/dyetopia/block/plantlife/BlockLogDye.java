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

/**
 * Meta: top 2 bits are the orientation
 * Type: bottom 2 bits
 */
public class BlockLogDye extends BlockLogDYT {

    public BlockLogDye() {

        super();
        this.setBlockName(Names.BLOCK_LOG_DYE);
    }

    private int getTypeFromMeta(int meta) {

        return (meta & 0x3) & 0xF;
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[][] icons;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {

        icons = new IIcon[2][Names.BLOCK_SAPLING_DYE_TYPES.length];
        for (int i = 0; i < Names.BLOCK_SAPLING_DYE_TYPES.length; i++) {
            icons[0][i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX + "plantlife/" +
                    Names.BLOCK_LOG_DYE + "." +
                    Names.BLOCK_SAPLING_DYE_TYPES[i] + "_bark");
            icons[1][i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX + "plantlife/" +
                            Names.BLOCK_LOG_DYE + "." +
                            Names.BLOCK_SAPLING_DYE_TYPES[i] + "_heart");
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list) {

        for (int i = 0 ; i < Names.BLOCK_SAPLING_DYE_TYPES.length; i++)
            list.add(new ItemStack(item, 1, i));
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int meta) {
        return icons[0][getTypeFromMeta(meta)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int meta) {
        return icons[1][getTypeFromMeta(meta)];
    }
}
