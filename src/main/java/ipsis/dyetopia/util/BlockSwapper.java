package ipsis.dyetopia.util;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/* Swap one block for another block */
public class BlockSwapper {

    public static boolean swap(EntityPlayer player, World world, int x, int y, int z, ItemStack stack) {

        if (player == null || world == null || world.isRemote || stack == null)
            return false;

        Block b = world.getBlock(x, y, z);
        if (b == null || b.isAir(world, x, y, z))
            return false;

        /* Don't replace the block with itself */
        if (b == Block.getBlockFromItem(stack.getItem()) &&
                world.getBlockMetadata(x, y, z) == stack.getItemDamage())
            return false;

        /* Remove the old block */
        world.setBlockToAir(x, y, z);

        /**
         * This tries to replicate the ItemBlock placeBlockAt code
         */
        Block block = Block.getBlockFromItem(stack.getItem());
        if (world.setBlock(x, y, z, block, stack.getItemDamage(), 3))
        {
            if (world.getBlock(x, y, z) == block)
            {
                block.onBlockPlacedBy(world, x, y, z, player, stack);
                block.onPostBlockPlaced(world, x, y, z, stack.getItemDamage());
                return true;
            }
        }

        return false;
    }
}
