package ipsis.dyetopia.block;

import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityValve;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockValve extends BlockDYTMultiBlock implements ITileEntityProvider, ITooltipInfo{

    public BlockValve() {
        super();
        this.setBlockName(Names.Blocks.BLOCK_MACHINE_VALVE);
    }

    @SideOnly(Side.CLIENT)
    IIcon[] formedIcons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        formedIcons = new IIcon[6];

        /* unformed icon */
        blockIcon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_VALVE + ".Unformed");

        /* formed but uncolored */
        formedIcons[0] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_VALVE + ".Formed");

        /* formed and colored */
        formedIcons[1] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_VALVE + ".Red.Formed");
        formedIcons[2] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_VALVE + ".Yellow.Formed");
        formedIcons[3] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_VALVE + ".Blue.Formed");
        formedIcons[4] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_VALVE + ".White.Formed");
        formedIcons[5] = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + "machines/" + Names.Blocks.BLOCK_MACHINE_VALVE + ".Pure.Formed");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityValve) {
            if (((TileEntityValve)te).hasMaster()) {

                switch (((TileEntityValve)te).getColor()) {
                    case NONE:
                        return formedIcons[0];
                    case RED:
                        return formedIcons[1];
                    case YELLOW:
                        return formedIcons[2];
                    case BLUE:
                        return formedIcons[3];
                    case WHITE:
                        return formedIcons[4];
                    case PURE:
                        return formedIcons[5];
                    default:
                        return formedIcons[0];
                }
            }
        }

		/* Assume everything else is the same icon */
        return blockIcon;
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
