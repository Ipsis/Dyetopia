package ipsis.dyetopia.block;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityCasing;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCasing extends BlockDYTMultiBlock implements ITileEntityProvider, ITooltipInfo {

    public BlockCasing() {
        super();
        this.setBlockName(Names.Blocks.BLOCK_MACHINE_CASING);
    }

    @SideOnly(Side.CLIENT)
    private IIcon formedIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        formedIcon = iconRegister.registerIcon(
                Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CASING + ".Formed");
        blockIcon = iconRegister.registerIcon(
                Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CASING + ".Unformed");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityMultiBlockBase) {
            if (((TileEntityMultiBlockBase)te).hasMaster())
                return formedIcon;
        }

        return blockIcon;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityCasing();
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvancedItemTooltips, int meta, boolean detail) {
        toolTip.add(StringHelper.localize(Lang.Tooltips.BLOCK_CASING));
        toolTip.add(StringHelper.localize(Lang.Tooltips.MULTIBLOCK));
    }
}
