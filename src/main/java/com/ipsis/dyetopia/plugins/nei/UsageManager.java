package com.ipsis.dyetopia.plugins.nei;

import cofh.lib.inventory.ComparableItemStack;
import cofh.lib.inventory.ComparableItemStackSafe;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;
import com.ipsis.dyetopia.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class UsageManager {

    private UsageManager() { }

    private static ArrayList<UsageInfo> map = new ArrayList<UsageInfo>();

    public static void addUsage(ItemStack itemStack, String[] usageTags) {
        map.add(new UsageInfo(itemStack, usageTags));
    }

    public static void addUsage(Block block, String[] usageTags) {
        addUsage(new ItemStack(block), usageTags);
    }

    public static void addUsage(Item item, String[] usageTags) {
        addUsage(new ItemStack(item), usageTags);
    }

    public static UsageInfo getUsage(ItemStack itemStack) {

        for (UsageInfo i : map) {

            if (ItemHelper.areItemsEqual(ItemHelper.getItemFromStack(i.itemStack), ItemHelper.getItemFromStack(itemStack)))
                return i;
        }

        return null;
    }

    public static class UsageInfo {

        private ItemStack itemStack;
        private String[] usageTags;

        public UsageInfo(ItemStack itemStack, String[] usageTags) {

            this.usageTags = new String[usageTags.length];
            this.itemStack = itemStack;

            int x = 0;
            for (String tag : usageTags) {
                this.usageTags[x] = tag;
                x++;
            }
        }

        public int getNumUsageTags() { return this.usageTags.length; }
        public String getDisplayTag(int idx) {

            if (idx >= 0 && idx < this.usageTags.length)
                return StringHelper.localize(this.usageTags[idx]);

            return null;
        }

        public String getDisplayName() {
            return StringHelper.localize(this.itemStack.getItem().getUnlocalizedName());
        }

        public int getNumRecipes() {
            return this.usageTags.length;
        }

        @Override
        public String toString() {
            return new String(this.itemStack.getUnlocalizedName() + ":" + getNumRecipes() + " pages");
        }
    }
}
