package ipsis.dyetopia.block;

import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityValve;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.util.IconRegistry;
import ipsis.dyetopia.util.multiblock.MultiBlockTextures;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class BlockValve extends BlockDYTMultiBlock implements ITileEntityProvider, ITooltipInfo{

    public BlockValve() {
        super();
        this.setBlockName(Names.Blocks.BLOCK_MACHINE_VALVE);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {

        return IconRegistry.getIcon("ValveCenter");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityValve)
            return MultiBlockTextures.getIcon(this, ((TileEntityValve) te).getMasterTE(), x, y, z, side);

        return IconRegistry.getIcon("ValveCenter");

    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityValve();
    }

    @Override
    public void getTooltip(List<String> toolTip, boolean showAdvancedItemTooltips, int meta, boolean detail) {
        toolTip.add(StringHelper.localize(Lang.Tooltips.BLOCK_VALVE));
        toolTip.add(StringHelper.localize(Lang.Tooltips.MULTIBLOCK));
    }
}
