package com.ipsis.dyetopia.handler;

import com.ipsis.dyetopia.util.LogHelper;
import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.event.entity.player.FillBucketEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Handle the pickup of our mod liquids.
 * NB: In creative mode the bucket doesn't change from unfilled->filled.
 * It is part of the ItemBucket event processing.
 */
public class BucketHandler {

    public static BucketHandler INSTANCE = new BucketHandler();
    public Map<Block, Item> buckets = new HashMap<Block, Item>();

    private BucketHandler() {}

    @SubscribeEvent
    public void onFillBucketEvent(FillBucketEvent event) {

        if (event.world.isRemote || event.current.getItem() != Items.bucket || event.target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK)
            return;

        Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
        Item bucket = buckets.get(block);
        if (bucket != null) {
            event.world.setBlockToAir(event.target.blockX, event.target.blockY, event.target.blockZ);
            event.setResult(Event.Result.ALLOW);
            event.result = new ItemStack(bucket, 1);
        }
    }
}
