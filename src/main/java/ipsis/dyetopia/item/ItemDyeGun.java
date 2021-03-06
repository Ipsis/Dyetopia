package ipsis.dyetopia.item;


import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.manager.ForgeColorManager;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlocksManager;
import ipsis.dyetopia.reference.*;
import ipsis.dyetopia.util.BlockSwapper;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class ItemDyeGun extends ItemFluidContainerDYT {

    public ItemDyeGun() {

        super(0); /* itemID parameter not used */
        setCapacity(Settings.Items.dyeGunTankCapacity);
        setUnlocalizedName(Names.Items.DYE_GUN);
        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("item.%s%s", Textures.RESOURCE_PREFIX, Names.Items.DYE_GUN);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack)
    {
        if (itemStack.getItemDamage() == 0)
            return getUnlocalizedName();
        else
            return String.format("item.%s%s.creative", Textures.RESOURCE_PREFIX, Names.Items.DYE_GUN);
    }

    @SideOnly(Side.CLIENT)
    private IIcon pass0Icon;
    private IIcon pass1Icon;

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister)
    {
        pass0Icon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.DYE_GUN + ".Pass0");
        pass1Icon = iconRegister.registerIcon(Textures.RESOURCE_PREFIX + Names.Items.DYE_GUN + ".Pass1");
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

        ItemStack itemStack = new ItemStack(ModItems.itemDyeGun, 1, 0);
        setDefaultTags(itemStack);
        list.add(itemStack);

        ItemStack creativeItemStack = new ItemStack(ModItems.itemDyeGun, 1, 1);
        setDefaultTags(creativeItemStack);
        ItemDyeGun.fillGun(creativeItemStack, Settings.Items.dyeGunTankCapacity, true);
        list.add(creativeItemStack);
    }

    @Override
    public boolean getShareTag() {

        /* Send NBT when writing the ItemStack for sending to the client */
        return true;
    }

    @Override
    public void onCreated(ItemStack itemStack, World world,	EntityPlayer entityPlayer) {

        setDefaultTags(itemStack);
    }

    private static void setDefaultTags(ItemStack itemStack) {

        /* Default color of white, with 0 pure dye in the gun */
        if (itemStack.stackTagCompound == null)
            itemStack.stackTagCompound = new NBTTagCompound();

        itemStack.stackTagCompound.setInteger(Nbt.Items.DYEGUN_COLOR_TAG, DyeHelper.DyeType.WHITE.ordinal());

        /* The fluid is part of the default tags, so fill with 0 of pure dye */
        ItemDyeGun.fillGun(itemStack, 0, true);
    }

    /**
     * Color NBT
     */
    public DyeHelper.DyeType getColor(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        return DyeHelper.DyeType.getDye(itemStack.stackTagCompound.getInteger(Nbt.Items.DYEGUN_COLOR_TAG));
    }

    public void setColor(ItemStack itemStack, DyeHelper.DyeType color) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        itemStack.stackTagCompound.setInteger(Nbt.Items.DYEGUN_COLOR_TAG, color.ordinal());
    }

    private void setNextColor(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        DyeHelper.DyeType color = getColor(itemStack);
        setColor(itemStack, color.getNext());
    }

    public static boolean isFull(ItemStack itemStack) {

        if (itemStack == null || !(itemStack.getItem() instanceof ItemDyeGun))
            return false;

        ItemDyeGun gun = (ItemDyeGun)itemStack.getItem();
        FluidStack f = gun.getFluid(itemStack);
        if (f == null)
            return false;

        return (f.amount == gun.capacity);
    }

    public static int fillGun(ItemStack itemStack, int amount, boolean doFill) {

        if (itemStack == null || !(itemStack.getItem() instanceof ItemDyeGun))
            return 0;

        ItemDyeGun gun = (ItemDyeGun)itemStack.getItem();
        return gun.fill(itemStack, new FluidStack(ModFluids.fluidDyePure, amount), doFill);
    }

    /**
     * Information
     */

    private String getColorTranslation(DyeHelper.DyeType type) {
        return StatCollector.translateToLocal("tooltip.dyetopia:dyeGun." + type.getOreDictName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack,	EntityPlayer entityPlayer, List info, boolean useExtraInformation) {

        if (itemStack.getItem() != ModItems.itemDyeGun)
            return;

        ItemDyeGun g = (ItemDyeGun)itemStack.getItem();
        if (itemStack.stackTagCompound == null)
            setDefaultTags(itemStack);

        info.add(StringHelper.localize(Lang.Tooltips.ITEM_DYE_GUN));
        info.add(getColorTranslation(getColor(itemStack)));

        if (itemStack.getItemDamage() == 0) {
            FluidStack f = getFluid(itemStack);
            info.add(f.amount + "/" + g.capacity + " mB");
        }

        if (itemStack.getItemDamage() == 1)
            info.add(StringHelper.localize(Lang.Tooltips.CREATIVE_MODE));
    }

    /**
     * Use
     *
     * Sneak RightClick change color
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

        Block b = world.getBlock(x, y, z);
        if (!b.isAir(world, x, y, z)) {

            if (!ForgeColorManager.getInstance().isBlacklisted(b)) {
                /**
                 * If we are using the Forge recolorBlock routine then we cannot lookup a dye
                 * cost. Therefore we use a default value
                 */
                int vanillaPure = DyeHelper.getLCM();
                if (canShootGun(player, itemStack, vanillaPure)) {
                    DyeHelper.DyeType dyetype = ((ItemDyeGun) itemStack.getItem()).getColor(itemStack);
                    int color = BlockColored.func_150032_b(dyetype.getDmg());
                    if (b.recolourBlock(world, x, y, z, ForgeDirection.getOrientation(side), color)) {
                        shootGun(player, itemStack, vanillaPure);
                        return true;
                    }
                } else {
                    player.addChatComponentMessage(new ChatComponentText(StringHelper.localize(Lang.Messages.NOT_ENOUGH_DYE)));
                }
            }

            int meta = world.getBlockMetadata(x, y, z);

            /**
             * Need to work out the true metadata for this block if it was being placed in the world.
             * So change it from ... what for it ...
             *
             * Block + metadata -> item -> itemstack + item.metadata
             */
            Item t = Item.getItemFromBlock(b);
            if (t == null)
                return true;

            ItemStack tmp = new ItemStack(t, 1, t.getMetadata(meta));

            if (!DyeableBlocksManager.canDyeBlock(tmp)) {
                player.addChatComponentMessage(new ChatComponentText(String.format(StringHelper.localize(Lang.Messages.NO_RECOLOR), tmp.getDisplayName())));
                return true;
            }

            DyeableBlocksManager.DyedBlockRecipe r = DyeableBlocksManager.getDyedBlock(tmp, ((ItemDyeGun) itemStack.getItem()).getColor(itemStack));
            if (r != null) {

                if (!r.isValidForBlock()) {
                    player.addChatComponentMessage(new ChatComponentText(StringHelper.localize(Lang.Messages.PAINTER_ONLY)));
                    return true;
                }

                if (canShootGun(player, itemStack, r.getPureAmount())) {
                    if (BlockSwapper.swap(player, world, x, y, z, r.getOutput())) {
                        shootGun(player, itemStack, r.getPureAmount());
                    }
                } else {
                    player.addChatComponentMessage(new ChatComponentText(StringHelper.localize(Lang.Messages.NOT_ENOUGH_DYE)));
                }
            }
        }

        return true;
    }

    private boolean canDyeSheep(EntityPlayer player, ItemStack itemStack) {

        return canShootGun(player, itemStack, DyeHelper.getLCM());
    }

    private boolean canShootGun(EntityPlayer player, ItemStack itemStack, int amount) {

        if (player.capabilities.isCreativeMode)
            return true;

        FluidStack fStack = drain(itemStack, amount, false);
        if (fStack == null)
            return false;

        return (fStack.amount == amount);
    }

    private void shootGun(EntityPlayer player, ItemStack itemStack) {

        /* Fixed cost operation */
        shootGun(player, itemStack, DyeHelper.getLCM());
    }

    private void shootGun(EntityPlayer player, ItemStack itemStack, int amount) {

        if (player == null || itemStack == null)
            return;

        if (player.capabilities.isCreativeMode)
            return;

        drain(itemStack, amount, true);
    }

    /**
     * Sheep!
     * Straight from vanilla ItemDye */
    public boolean itemInteractionForEntity(ItemStack itemStack, EntityPlayer entityPlayer, EntityLivingBase entityLiving)
    {
        if (entityLiving instanceof EntitySheep && itemStack.getItem() == ModItems.itemDyeGun && canDyeSheep(entityPlayer, itemStack))
        {
            EntitySheep entitysheep = (EntitySheep)entityLiving;
            DyeHelper.DyeType t = getColor(itemStack);

            int color = BlockColored.func_150032_b(t.getDmg());

            if (!entitysheep.getSheared() && entitysheep.getFleeceColor() != color)
            {
                entitysheep.setFleeceColor(color);
                shootGun(entityPlayer, itemStack);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
}
