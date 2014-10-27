package ipsis.dyetopia.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import java.util.List;

public class ItemDyeDrop extends ItemDYT {

    public ItemDyeDrop() {

        super();
        setUnlocalizedName(Names.Items.ITEM_DYE_DROP);
        setHasSubtypes(true);
        setMaxStackSize(16);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, Names.Items.ITEM_DYE_DROP);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s.%s", Textures.RESOURCE_PREFIX, Names.Items.ITEM_DYE_DROP, Names.Items.ITEM_DYE_DROP_TYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.ITEM_DYE_DROP_TYPES.length - 1)]);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < Names.Items.ITEM_DYE_DROP_TYPES.length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        return icons[MathHelper.clamp_int(dmg, 0, Names.Items.ITEM_DYE_DROP_TYPES.length - 1)];
    }

    @Override
    public void registerIcons(IIconRegister ir) {

        icons = new IIcon[Names.Items.ITEM_DYE_DROP_TYPES.length];

        for (int i = 0 ; i < Names.Items.ITEM_DYE_DROP_TYPES.length; i++) {
            icons[i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX +
                    Names.Items.ITEM_DYE_DROP + "." +
                    Names.Items.ITEM_DYE_DROP_TYPES[i]);
        }
    }
}
