package ipsis.dyetopia.item;

import ipsis.dyetopia.manager.dyeableblocks.DyeableBlocksManager;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.util.BlockSwapper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEraser extends ItemDYT {

    public ItemEraser() {
        super();
        this.setUnlocalizedName(Names.Items.ERASER);
        this.setTooltip(Lang.Tooltips.ITEM_ERASER);
        this.setMaxStackSize(64);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote || player.isSneaking())
            return false;

        if (!player.canPlayerEdit(x, y, z, side, stack))
            return true;

        Block b = world.getBlock(x, y, z);
        if (b != null && !b.isAir(world, x, y, z) && !(b instanceof ITileEntityProvider)) {

            int meta = world.getBlockMetadata(x, y, z);

            ItemStack originStack = DyeableBlocksManager.getOrigin(new ItemStack(b, 1, meta));
            if (originStack != null) {
                BlockSwapper.swap(player, world, x, y, z, originStack);
                return true;
            }
        }

        return false;
    }
}
