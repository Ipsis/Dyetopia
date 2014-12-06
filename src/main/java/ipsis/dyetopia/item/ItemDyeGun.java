package ipsis.dyetopia.item;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.manager.dyeableblocks.DyeableBlocksManager;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Nbt;
import ipsis.dyetopia.reference.Settings;
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
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class ItemDyeGun extends ItemFluidContainerDYT {

    public ItemDyeGun() {

        super(0); /* itemID parameter not used */
        setCapacity(Settings.Items.dyeGunTankCapacity);
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
        FluidStack s = new FluidStack(ModFluids.fluidDyePure, Settings.Items.dyeGunTankCapacity);
        fill(itemStack, s, true);
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

    private static void setDefaultTags(ItemStack itemStack) {

        if (itemStack.stackTagCompound == null)
            itemStack.stackTagCompound = new NBTTagCompound();

        itemStack.stackTagCompound.setInteger(Nbt.Items.DYEGUN_COLOR_TAG, DyeHelper.DyeType.WHITE.ordinal());
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


        info.add(getColorTranslation(getColor(itemStack)));

        FluidStack f = getFluid(itemStack);
        info.add(f.amount + "/" + g.capacity + " mB");
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
        if (b != Blocks.air && !(b instanceof ITileEntityProvider)) {

            int meta = world.getBlockMetadata(x, y, z);

            DyeableBlocksManager.DyedBlockRecipe r = DyeableBlocksManager.getDyedBlock(new ItemStack(b, 1, meta), ((ItemDyeGun) itemStack.getItem()).getColor(itemStack));
            if (r != null && canShootGun(player, itemStack, r.getPureAmount())) {

                if (BlockSwapper.swap(player, world, x, y, z, r.getOutput())) {
                    shootGun(player, itemStack, r.getPureAmount());
                    return true;
                }
            }
        }

        return false;
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
