package ipsis.dyetopia.block;

import ipsis.dyetopia.creative.CreativeTab;
import ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * Based off BlockFluidCoFHBase
 */
public class BlockFluidDYT extends BlockFluidClassic {

    protected String name = "";
    @SideOnly(Side.CLIENT)
    protected IIcon[] icons;

    public BlockFluidDYT(Fluid fluid, Material material, String name) {

        super(fluid, material);

        this.setCreativeTab(CreativeTab.DYT_TAB);
        this.name = name;
        setBlockName(Reference.MOD_ID + ":fluid." + name);
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

        this.icons = new IIcon[]{
                iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "fluid/Fluid_" + name + "_Still"),
                iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "fluid/Fluid_" + name + "_Flow")
        };
    }

    @Override
    public IIcon getIcon(int side, int meta) {

        /* For top/bottom return still, all other sides get flowing */
        if (side == 0 || side == 1)
            return this.icons[0];

        return this.icons[1];
    }
}
