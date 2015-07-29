package ipsis.dyetopia.block;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityFiller;
import ipsis.dyetopia.util.TooltipHelper;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class BlockFiller extends BlockDYTMachine implements ITooltipInfo {

    public BlockFiller() {

        super();
        this.setBlockName(Names.Blocks.BLOCK_MACHINE_FILLER);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {

        frontIconActive = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_FILLER + ".Active");
        frontIconInactive = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_FILLER);
        blockIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_CASING + ".Unformed");
    }

    @Override
    public TileEntity createNewTileEntity(World world, int p_149915_2_) {
        return new TileEntityFiller();
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvancedItemTooltips, int meta, boolean detail) {
        toolTip.add(StringHelper.localize(Lang.Tooltips.BLOCK_FILLER));

        if (!detail)
            TooltipHelper.addMoreInfo(toolTip);
        else
            TooltipHelper.addRequires(toolTip, Lang.Tooltips.REQUIRES_RF, Lang.Tooltips.REQUIRES_DYE);
    }
}
