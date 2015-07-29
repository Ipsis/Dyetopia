package ipsis.dyetopia.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.dyetopia.block.ITooltipInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.input.Keyboard;

public class ItemTooltipHandler {

    @SubscribeEvent
    public void onItemToolTip(ItemTooltipEvent event) {

        if (event.entityPlayer == null)
            return;

        if(event.itemStack == null)
            return;

        Block block = Block.getBlockFromItem(event.itemStack.getItem());
        if(block == null || block.getMaterial() == Material.air)
            return;

        if (block instanceof ITooltipInfo) {
            int meta = event.itemStack.getItemDamage();

            /* detail rather than the advanced debug value that the event contains */
            boolean detail = false;
            if (Keyboard.isKeyDown(org.lwjgl.input.Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))
                detail = true;

            ((ITooltipInfo)block).getTooltip(event.toolTip, event.showAdvancedItemTooltips, meta, detail);
        }
    }
}
