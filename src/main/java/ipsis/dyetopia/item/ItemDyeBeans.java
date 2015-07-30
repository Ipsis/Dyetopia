package ipsis.dyetopia.item;

import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class ItemDyeBeans extends ItemDYT {

    public ItemDyeBeans() {

        super();
        setUnlocalizedName(Names.Items.DYE_BEANS);
        setHasSubtypes(true);
        setMaxStackSize(16);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, Names.Items.DYE_BEANS);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s.%s", Textures.RESOURCE_PREFIX, Names.Items.DYE_BEANS, Names.Items.DYE_BEANS_TYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.DYE_DROP_TYPES.length - 1)]);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < Names.Items.DYE_BEANS_TYPES.length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        return icons[MathHelper.clamp_int(dmg, 0, Names.Items.DYE_BEANS_TYPES.length - 1)];
    }

    @Override
    public void registerIcons(IIconRegister ir) {

        icons = new IIcon[Names.Items.DYE_BEANS_TYPES.length];

        for (int i = 0 ; i < Names.Items.DYE_BEANS_TYPES.length; i++) {
            icons[i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX +
                            Names.Items.DYE_BEANS + "." +
                            Names.Items.DYE_BEANS_TYPES[i]);
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (!player.canPlayerEdit(x, y, z, side, stack))
            return false;

        Block b = world.getBlock(x, y, z);
        if (b != ModBlocks.blockRootPureDye)
            return false;

        if (side == 0 || side == 1)
            return false;

        if (side == 2)
            z -= 1;
        else if (side == 3)
            z += 1;
        else if (side == 4)
            x -= 1;
        else if (side == 5)
            x += 1;

        if (!world.isAirBlock(x, y, z))
            return false;

        int bMeta = stack.getItemDamage();
        if (bMeta == 0) {
            int meta = ModBlocks.blockPodRedDye.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
            world.setBlock(x, y, z, ModBlocks.blockPodRedDye, meta, 2);
        } else if (bMeta == 1) {
            int meta = ModBlocks.blockPodYellowDye.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
            world.setBlock(x, y, z, ModBlocks.blockPodYellowDye, meta, 2);
        } else if (bMeta == 2) {
            int meta = ModBlocks.blockPodBlueDye.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
            world.setBlock(x, y, z, ModBlocks.blockPodBlueDye, meta, 2);
        } else {
            return false;
        }

        if (!player.capabilities.isCreativeMode)
            --stack.stackSize;

        return true;
    }

    @Override
    public void addInformation(ItemStack itemStack, EntityPlayer player, List info, boolean showAdvanced) {
        super.addInformation(itemStack, player, info, showAdvanced);

        if (itemStack == null)
            return;;

        int meta = itemStack.getItemDamage();
        if (meta >= 0 && meta < Names.Items.DYE_BEANS_TYPES.length)
            info.add(StringHelper.localize(Lang.Tooltips.ITEM_DYE_BEANS + "." + Names.Items.DYE_BEANS_TYPES[meta].toLowerCase()));
    }
}
