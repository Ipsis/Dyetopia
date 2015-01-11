package ipsis.dyetopia.creative;

import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.StatCollector;

public class CreativeTab {

    public static final CreativeTabs DYT_TAB = new CreativeTabs(Reference.MOD_ID) {

        @Override
        public Item getTabIconItem() {

            return ModItems.itemDyeChunk;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel() {

            return StringHelper.localize(Lang.TAG_TAB);
        }
    };
}
