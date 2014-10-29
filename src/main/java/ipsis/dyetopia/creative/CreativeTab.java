package ipsis.dyetopia.creative;

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

            return Items.dye;
        }

        @Override
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel() {

            return StatCollector.translateToLocal("key.categories.dyetopia");
        }
    };
}