package ipsis.dyetopia.item;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import javax.tools.Tool;
import java.util.List;

/**
 * Damage values match up with the vanilla dye ones
 */
public class ItemDyeChunk extends ItemDYT {

    public ItemDyeChunk() {

        super();
        setUnlocalizedName(Names.Items.DYE_CHUNK);
        setTooltip(Lang.Tooltips.ITEM_DYE_CHUNK);
        setHasSubtypes(true);
        setMaxStackSize(64);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, Names.Items.DYE_CHUNK);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s.%s", Textures.RESOURCE_PREFIX, Names.Items.DYE_CHUNK, Names.Items.DYE_CHUNK_TYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.DYE_CHUNK_TYPES.length - 1)]);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < Names.Items.DYE_CHUNK_TYPES.length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        return icons[MathHelper.clamp_int(dmg, 0, Names.Items.DYE_CHUNK_TYPES.length - 1)];
    }

    @Override
    public void registerIcons(IIconRegister ir) {

        icons = new IIcon[Names.Items.DYE_CHUNK_TYPES.length];

        for (int i = 0 ; i < Names.Items.DYE_CHUNK_TYPES.length; i++) {
            icons[i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX +
                            Names.Items.DYE_CHUNK + "." +
                            Names.Items.DYE_CHUNK_TYPES[i]);
        }
    }

}
