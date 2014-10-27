package ipsis.dyetopia.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.block.DYTBlocks;
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
        setUnlocalizedName(Names.Items.ITEM_DYE_BEANS);
        setHasSubtypes(true);
        setMaxStackSize(16);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, Names.Items.ITEM_DYE_BEANS);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        return String.format("item.%s%s.%s", Textures.RESOURCE_PREFIX, Names.Items.ITEM_DYE_BEANS, Names.Items.ITEM_DYE_BEANS_TYPES[MathHelper.clamp_int(itemStack.getItemDamage(), 0, Names.Items.ITEM_DYE_DROP_TYPES.length - 1)]);
    }

    @SideOnly(Side.CLIENT)
    private static IIcon[] icons;

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list)
    {
        for (int meta = 0; meta < Names.Items.ITEM_DYE_BEANS_TYPES.length; ++meta)
        {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    public IIcon getIconFromDamage(int dmg) {
        return icons[MathHelper.clamp_int(dmg, 0, Names.Items.ITEM_DYE_BEANS_TYPES.length - 1)];
    }

    @Override
    public void registerIcons(IIconRegister ir) {

        icons = new IIcon[Names.Items.ITEM_DYE_BEANS_TYPES.length];

        for (int i = 0 ; i < Names.Items.ITEM_DYE_BEANS_TYPES.length; i++) {
            icons[i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX +
                            Names.Items.ITEM_DYE_BEANS + "." +
                            Names.Items.ITEM_DYE_BEANS_TYPES[i]);
        }
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (!player.canPlayerEdit(x, y, z, side, stack))
            return false;

        Block b = world.getBlock(x, y, z);
        if (b != DYTBlocks.blockRootPureDye)
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
            int meta = DYTBlocks.blockPodRedDye.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
            world.setBlock(x, y, z, DYTBlocks.blockPodRedDye, meta, 2);
        } else if (bMeta == 1) {
            int meta = DYTBlocks.blockPodYellowDye.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
            world.setBlock(x, y, z, DYTBlocks.blockPodYellowDye, meta, 2);
        } else if (bMeta == 2) {
            int meta = DYTBlocks.blockPodBlueDye.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, 0);
            world.setBlock(x, y, z, DYTBlocks.blockPodBlueDye, meta, 2);
        } else {
            return false;
        }

        if (!player.capabilities.isCreativeMode)
            --stack.stackSize;

        return true;
    }
}
