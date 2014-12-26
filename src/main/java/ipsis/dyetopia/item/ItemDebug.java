package ipsis.dyetopia.item;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class ItemDebug extends ItemDYT {

    public ItemDebug() {
        super();
        this.setUnlocalizedName("debug");
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (!world.isRemote)
            return false;

        /* client output only */
        player.addChatComponentMessage(new ChatComponentText("Block: @" + x + "," + y + "," + z + ":" + side));

        Block b = world.getBlock(x, y, z);
        if (!b.isAir(world, x, y, z)) {
            player.addChatComponentMessage(new ChatComponentText("Block:" + b + "meta:" + world.getBlockMetadata(x, y, z)));
            player.addChatComponentMessage(new ChatComponentText("Block: item:" + b.getItem(world, x, y, z)));
            if (b instanceof ITileEntityProvider)
                player.addChatComponentMessage(new ChatComponentText("Block is a TEProvider"));
        }

        return true;
    }
}
