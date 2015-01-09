package ipsis.dyetopia.block;

import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityMixer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.util.TooltipHelper;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockMixer extends BlockDYTMultiBlock implements ITileEntityProvider, ITooltipInfo {

    public BlockMixer() {
        super();
        this.setBlockName(Names.Blocks.BLOCK_MACHINE_MIXER);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityMixer();
    }

    @SideOnly(Side.CLIENT)
    IIcon frontIconInactive;
    @SideOnly(Side.CLIENT)
    IIcon frontIconActive;
    @SideOnly(Side.CLIENT)
    IIcon casingFormedIcon;
    @SideOnly(Side.CLIENT)
    IIcon casingUnformedIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_MIXER + ".Unformed");
        frontIconInactive = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_MIXER + ".Formed");
        frontIconActive = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_MIXER + ".Formed.Active");
        casingFormedIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CASING + ".Formed");
        casingUnformedIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CASING + ".Unformed");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {

        /**
         * As we override the IBlockAccess version this will only be called for the toolbar
         * Is is oriented as facing south
         */
        if (side == ForgeDirection.SOUTH.ordinal())
            return blockIcon;

        return casingUnformedIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityMixer) {

            TileEntityMixer mte = (TileEntityMixer) te;
            if (side == mte.getDirectionFacing().ordinal()) {
                if (mte.isStructureValid()) {
                    if (mte.getStatus())
                        return frontIconActive;
                    else
                        return frontIconInactive;
                } else {
                    return blockIcon;
                }
            } else {
                return casingUnformedIcon;
            }
        }

        return casingUnformedIcon;
    }


    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvancedItemTooltips, int meta, boolean detail) {
        toolTip.add(StringHelper.localize(Lang.Tooltips.BLOCK_MIXER));
        toolTip.add(StringHelper.localize(Lang.Tooltips.MULTIBLOCK));

        if (!detail)
            TooltipHelper.addMoreInfo(toolTip);
        else
            TooltipHelper.addRequires(toolTip, Lang.Tooltips.REQUIRES_RF);
    }
}
