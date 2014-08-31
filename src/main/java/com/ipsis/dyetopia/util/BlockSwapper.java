package com.ipsis.dyetopia.util;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/* Swap one block for another block */
public class BlockSwapper {

    public static boolean swap(EntityPlayer player, World world, int x, int y, int z, ItemStack stack) {

        if (player == null || world == null || world.isRemote || stack == null)
            return false;

        Block b = world.getBlock(x, y, z);
        if (b == null || b == Blocks.air || b instanceof ITileEntityProvider)
            return false;

        /* Remove the old block */
        world.setBlockToAir(x, y, z);

        LogHelper.info("Place " + stack + "->" + Block.getBlockFromItem(stack.getItem()));

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
