package ipsis.dyetopia.block;

import ipsis.dyetopia.Dyetopia;
import ipsis.dyetopia.tileentity.TileEntityMixer;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockBase;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockMaster;
import ipsis.dyetopia.tileentity.TileEntitySqueezer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockDYTMultiBlock extends BlockDYT implements ITileEntityProvider {

    public BlockDYTMultiBlock() {
        super();
    }

    @SideOnly(Side.CLIENT)
    IIcon formedIcon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
        blockIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName())));
        formedIcon = iconRegister.registerIcon(String.format("%s", getUnwrappedUnlocalizedName(this.getUnlocalizedName() + "_formed")));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess iblockaccess, int x, int y, int z, int side) {

        TileEntity te = iblockaccess.getTileEntity(x, y, z);
        if (te != null && te instanceof TileEntityMultiBlockBase) {
            if (((TileEntityMultiBlockBase)te).hasMaster())
                return formedIcon;
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
        if (te instanceof  TileEntityMultiBlockBase) {

            TileEntityMultiBlockBase bte = (TileEntityMultiBlockBase)te;
            if (bte.isStructureValid()) {

                TileEntityMultiBlockMaster mte = bte.getMasterTE();
                if (mte != null) {
                    if (mte instanceof TileEntitySqueezer) {
                        entityPlayer.openGui(Dyetopia.instance, 0, world, mte.getMasterX(), mte.getMasterY(), mte.getMasterZ());
                    } else if (mte instanceof TileEntityMixer) {
                        entityPlayer.openGui(Dyetopia.instance, 1, world, mte.getMasterX(), mte.getMasterY(), mte.getMasterZ());
                    }
                }
            }
        }
        
        return true;
    }

    @Override
         public void breakBlock(World world, int x, int y, int z, Block block, int meta) {

             if (!world.isRemote) {
                 TileEntity te = world.getTileEntity(x, y, z);
                 if (te instanceof TileEntityMultiBlockBase) {
                     ((TileEntityMultiBlockBase)te).breakStructure();
                 }
             }

             super.breakBlock(world, x, y, z, block, meta);
         }

}
