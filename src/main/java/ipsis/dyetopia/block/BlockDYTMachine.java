package ipsis.dyetopia.block;

import ipsis.dyetopia.Dyetopia;
import ipsis.dyetopia.reference.GuiIds;
import ipsis.dyetopia.tileentity.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public abstract class BlockDYTMachine extends BlockDYT implements ITileEntityProvider {

    public BlockDYTMachine() {
        super();
    }

    @SideOnly(Side.CLIENT)
    IIcon frontIconActive;
    IIcon frontIconInactive;

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int metadata) {

        /**
         * As we override the IBlockAccess version this will only be called for the toolbar
         * Is is oriented as facing south
         */
        if (side == ForgeDirection.SOUTH.ordinal())
            return this.frontIconInactive;

        return this.blockIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityMachine) {

            TileEntityMachine m = (TileEntityMachine)te;
            if (side == m.getDirectionFacing().ordinal()) {
                if (m.getStatus())
                    return frontIconActive;
                else
                    return frontIconInactive;
            } else {
                return blockIcon;
            }
        }

		/* Assume everything else is the same icon */
        return blockIcon;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int p_149727_6_, float hitX, float hitY, float hitZ) {

        if (entityPlayer.isSneaking())
            return false;

        if (world.isRemote)
            return true;

        TileEntity te = world.getTileEntity(x, y, z);
        if (te instanceof TileEntityMachine) {

            if (te instanceof TileEntityPainter)
                entityPlayer.openGui(Dyetopia.instance, GuiIds.GUI_PAINTER, world, x, y, z);
            else if (te instanceof TileEntityStamper)
                entityPlayer.openGui(Dyetopia.instance, GuiIds.GUI_STAMPER, world, x, y, z);
            else if (te instanceof TileEntityFiller)
                entityPlayer.openGui(Dyetopia.instance, GuiIds.GUI_FILLER, world, x, y, z);
        }

        return true;
    }
}
