package ipsis.dyetopia.block;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityController;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockController extends BlockDYTMultiBlock implements ITileEntityProvider, ITooltipInfo {

    public BlockController() {
        super();
        this.setBlockName(Names.Blocks.BLOCK_MACHINE_CONTROLLER);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityController();
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(
                Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CONTROLLER);
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvancedItemTooltips, int meta, boolean detail) {
        toolTip.add(StringHelper.localize(Lang.Tooltips.BLOCK_CONTROLLER));
        toolTip.add(StringHelper.localize(Lang.Tooltips.MULTIBLOCK));
    }
}
