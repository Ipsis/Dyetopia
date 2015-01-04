package ipsis.dyetopia.item;

import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemDyeBlank extends ItemDYT {

    public ItemDyeBlank() {
        super();
        this.setUnlocalizedName(Names.Items.DYE_BLANK);
        this.setTooltip(Lang.Tooltips.ITEM_DYE_BLANK);
        this.setMaxStackSize(64);
    }
}
