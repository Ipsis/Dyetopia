package ipsis.dyetopia.item;

import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;

public class ItemDyeBlank extends ItemDYT {

    public ItemDyeBlank() {
        super();
        this.setUnlocalizedName(Names.Items.DYE_BLANK);
        this.setTooltip(Lang.Tooltips.ITEM_DYE_BLANK);
        this.setMaxStackSize(64);
    }
}
