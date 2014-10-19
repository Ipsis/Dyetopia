package com.ipsis.dyetopia.item;

import com.ipsis.dyetopia.block.trees.BlockDyeLog;
import com.ipsis.dyetopia.reference.Reference;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockDyeLog extends ItemBlock {

    public ItemBlockDyeLog(Block block)
    {
        super(block);

        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta & 3;
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        int type = itemStack.getItemDamage() & 3;
        if (type == 0)
            return String.format("tile.%s%s", Reference.MOD_ID + ":", "barkDye");
        else
            return String.format("tile.%s%s", Reference.MOD_ID + ":", "rootDye");

    }
}
