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

public class BlockRootDye extends BlockLogDYT {

    public BlockRootDye() {

        super();
        this.setBlockName(Names.Blocks.BLOCK_ROOT_DYE);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {

        icons = new IIcon[2];

        icons[0] = ir.registerIcon(
                Textures.RESOURCE_PREFIX + "plantlife/" +
                        Names.Blocks.BLOCK_ROOT_DYE + "_bark");
        icons[1] = ir.registerIcon(
                Textures.RESOURCE_PREFIX + "plantlife/" +
                        Names.Blocks.BLOCK_ROOT_DYE + "_heart");
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int meta) {
        return icons[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int meta) {
        return icons[1];
    }

}
