package com.ipsis.dyetopia.item;

import com.ipsis.dyetopia.util.BlockSwapper;
import com.ipsis.dyetopia.util.LogHelper;
import com.ipsis.dyetopia.util.OriginHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEraser extends ItemDYT {

    public ItemEraser() {
        super();
        this.setUnlocalizedName("eraser");
        this.setMaxStackSize(64);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote || !player.canPlayerEdit(x, y, z, side, stack))
            return false;

        Block b = world.getBlock(x, y, z);
        if (b != null && b != Blocks.air && !(b instanceof ITileEntityProvider)) {

            ItemStack origin = OriginHelper.getOrigin(new ItemStack(b));
            if (origin != null)
                return BlockSwapper.swap(player, world, x, y, z, origin);
        }

        return false;
    }
}
