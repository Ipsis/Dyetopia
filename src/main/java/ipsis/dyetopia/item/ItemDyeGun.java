package ipsis.dyetopia.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.manager.DyeLiquidManager;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlocksManager;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.util.BlockSwapper;
import ipsis.dyetopia.util.DyeHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemDyeGun extends ItemDYT {

    private static final int CAPACITY = DyeLiquidManager.DYE_BASE_AMOUNT * 20;
    private static final String FLUID_TAG = "FluidAmount";
    private static final String COLOR_TAG = "CurrColor";

    public ItemDyeGun() {

        super();
        setUnlocalizedName(Names.Items.ITEM_DYE_GUN);
    }

    @SideOnly(Side.CLIENT)
    private IIcon pass0Icon;
    private IIcon pass1Icon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        pass0Icon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.ITEM_DYE_GUN + ".Pass0");
        pass1Icon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.ITEM_DYE_GUN + ".Pass1");
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {

        if (pass == 0) {
            return pass0Icon; /* fixed */
        } else {
            return pass1Icon; /* transparency */
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getRenderPasses(int metadata) {
        return 2;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack itemStack, int renderPass)
    {
        if (renderPass == 0)
            return 0xFFFFFF;
        else
            return getColor(itemStack).getColorCode();
    }

    /**
     * Creation
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {

        ItemStack itemStack = new ItemStack(ModItems.itemDyeGun);

        setDefaultTags(itemStack);
        setFluidAmount(itemStack, CAPACITY);
        list.add(itemStack);
    }

    @Override
    public boolean getShareTag() {

        return true;
    }

    @Override
    public void onCreated(ItemStack itemStack, World world,	EntityPlayer entityPlayer) {

        setDefaultTags(itemStack);
    }

    /**
     * NBT stuff
     */
    public static void setDefaultTags(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            itemStack.stackTagCompound = new NBTTagCompound();

        /* White and empty */
        itemStack.stackTagCompound.setInteger(COLOR_TAG, DyeHelper.DyeType.WHITE.ordinal());
        itemStack.stackTagCompound.setInteger(FLUID_TAG, 0);
    }

    public static int getFluidAmount(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        return itemStack.stackTagCompound.getInteger(FLUID_TAG);
    }

    public static void setFluidAmount(ItemStack itemStack, int amount) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        if (amount > CAPACITY)
            amount = CAPACITY;
        else if (amount < 0)
            amount = 0;

        itemStack.stackTagCompound.setInteger(FLUID_TAG, amount);
    }

    public static void fillGun(ItemStack itemStack, int amount) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        int newAmount = getFluidAmount(itemStack) + amount;
        if (newAmount > CAPACITY)
            newAmount = CAPACITY;

        setFluidAmount(itemStack, newAmount);
    }

    public static boolean isFull(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        if (getFluidAmount(itemStack) == CAPACITY)
            return true;

        return false;
    }

    public static void drainGun(ItemStack itemStack, int amount) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        int newAmount = getFluidAmount(itemStack) - amount;
        if (newAmount < 0)
            newAmount = 0;

        setFluidAmount(itemStack, newAmount);

    }

    public DyeHelper.DyeType getColor(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        return DyeHelper.DyeType.getDye(itemStack.stackTagCompound.getInteger(COLOR_TAG));
    }

    public void setColor(ItemStack itemStack, DyeHelper.DyeType color) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        itemStack.stackTagCompound.setInteger(COLOR_TAG, color.ordinal());
    }

    public void setNextColor(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        DyeHelper.DyeType color = getColor(itemStack);
        setColor(itemStack, color.getNext());
    }

    /**
     * Using
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer entityPlayer) {

        if (!world.isRemote && entityPlayer.isSneaking()) {

            setNextColor(itemStack);
            entityPlayer.addChatComponentMessage(new ChatComponentText(getColorTranslation(getColor(itemStack))));
        }

        return itemStack;
    }

    @Override
    public boolean onItemUseFirst(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {

        if (world.isRemote || player.isSneaking())
            return false;

        if (itemStack.getItem() != ModItems.itemDyeGun)
            return false;

        if (!player.canPlayerEdit(x, y, z, side, itemStack))
            return true;

        if (!player.capabilities.isCreativeMode && getFluidAmount(itemStack) < DyeLiquidManager.DYE_BASE_AMOUNT)
            return false;

        Block b = world.getBlock(x, y, z);
        if (b != Blocks.air && !(b instanceof ITileEntityProvider)) {

            int meta = world.getBlockMetadata(x, y, z);

            DyeableBlocksManager.DyedBlockRecipe r = DyeableBlocksManager.getDyedBlock(new ItemStack(b, 1, meta), ((ItemDyeGun) itemStack.getItem()).getColor(itemStack));
            if (r != null) {

                if (BlockSwapper.swap(player, world, x, y, z, r.getOutput())) {
                    if (!player.capabilities.isCreativeMode)
                        drainGun(itemStack, DyeLiquidManager.DYE_BASE_AMOUNT);

                    return true;
                }
            }
        }

        return false;
    }

    /* Straight from vanilla ItemDye */
    public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLiving)
    {
        if (entityLiving instanceof EntitySheep && itemStack.getItem() == ModItems.itemDyeGun)
        {
            EntitySheep entitysheep = (EntitySheep)entityLiving;
            DyeHelper.DyeType t = getColor(itemStack);

            int color = BlockColored.func_150032_b(t.getDmg());
            if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != color)
            {
                entitysheep.setFleeceColor(color);
                if (!entityPlayer.capabilities.isCreativeMode)
                    drainGun(itemStack, DyeLiquidManager.DYE_BASE_AMOUNT);
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    private String getColorTranslation(DyeHelper.DyeType type) {

        return StatCollector.translateToLocal("tooltip.dyetopia:dyeGun." + type.getOreDictName());
    }

    /**
     * Information
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack,	EntityPlayer entityPlayer, List info, boolean useExtraInformation) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        info.add(getColorTranslation(getColor(itemStack)));
        info.add(itemStack.stackTagCompound.getInteger(FLUID_TAG) + "/" + CAPACITY + " mB");
    }

}
