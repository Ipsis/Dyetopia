package ipsis.dyetopia.item;

import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.creative.CreativeTab;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemDYT extends Item {

    private String tooltip;

    public ItemDYT()
    {
        super();
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTab.DYT_TAB);
        this.setNoRepair();
        this.tooltip = null;
    }

    protected void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    private boolean hasTooltip() { return this.tooltip != null; }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        itemIcon = iconRegister.registerIcon(this.getUnlocalizedName().substring(this.getUnlocalizedName().indexOf(".") + 1));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean showAdvanced) {
        super.addInformation(itemStack, player, info, showAdvanced);

        if (hasTooltip())
            info.add(StringHelper.localize(this.tooltip));
    }
}
