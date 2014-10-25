package ipsis.dyetopia.block;

import ipsis.dyetopia.tileentity.TileEntityValve;
import ipsis.dyetopia.util.TankType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockValve extends BlockDYTMultiBlock implements ITileEntityProvider{

    public BlockValve() {
        super();
        this.setBlockName("valve");
    }

    @SideOnly(Side.CLIENT)
    IIcon[] formedIcons;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        formedIcons = new IIcon[6];

        /* unformed icon */
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));

        /* formed but uncolored */
        formedIcons[0] = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_formed")));

        /* formed and colored */
        formedIcons[1] = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_" + TankType.RED.getName() + "_formed")));
        formedIcons[2] = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_" + TankType.YELLOW.getName() + "_formed")));
        formedIcons[3] = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_" + TankType.BLUE.getName() + "_formed")));
        formedIcons[4] = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_" + TankType.WHITE.getName() + "_formed")));
        formedIcons[5] = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_" + TankType.PURE.getName() + "_formed")));
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
}
