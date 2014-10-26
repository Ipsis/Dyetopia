package ipsis.dyetopia.item;


import ipsis.dyetopia.block.plantlife.BlockSaplingDye;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemDyemeal extends ItemDYT{

    public ItemDyemeal() {
        super();
        this.setUnlocalizedName("dyemeal");
        this.setMaxStackSize(16);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (!player.canPlayerEdit(x, y, z, side, stack))
        {
            return false;
        }

        Block block = world.getBlock(x, y, z);
        if (block instanceof BlockSaplingDye) {

            BlockSaplingDye igrowable = (BlockSaplingDye)block;
            /* Although we are not using IGrowable, keep to the same interface */
            if (igrowable.canGrow(world, x, y, z, world.isRemote)) {

                if (!world.isRemote) {
                    if (igrowable.hasGrown(world, world.rand, x, y, z)) {
                        igrowable.doGrow(world, world.rand, x, y, z);
                    }

                    stack.stackSize--;
                }

                return true;
            }

        }

        return false;
    }
}
