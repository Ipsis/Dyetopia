package ipsis.dyetopia.block.plantlife;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.init.ModItems;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.ArrayList;

/**
 * Metadata covers stage and direction
 * Therefore one instance per color :(
 */
public class BlockPodDye extends BlockCocoa {

    private IIcon icons[];
    private int dyeMeta;
    private String name;

    public BlockPodDye(int dyeMeta, String name) {

        super();
        this.dyeMeta = dyeMeta;
        this.setBlockName(name);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister ir) {

        icons = new IIcon[3];
        for (int i = 0; i < 3; i++) {
            icons[i] = ir.registerIcon(
                    Textures.RESOURCE_PREFIX + "plantlife/" +
                    getUnwrappedUnlocalizedName(super.getUnlocalizedName()) + ".Stage" + i);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getCocoaIcon(int p_149988_1_)
    {
        if (p_149988_1_ < 0 || p_149988_1_ >= this.icons.length)
        {
            p_149988_1_ = this.icons.length - 1;
        }

        return this.icons[p_149988_1_];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return this.icons[2];
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        int l = getDirection(world.getBlockMetadata(x, y, z));
        x += Direction.offsetX[l];
        z += Direction.offsetZ[l];
        Block block = world.getBlock(x, y, z);
        return block == ModBlocks.blockRootPureDye;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> dropped = new ArrayList<ItemStack>();
        int stage = func_149987_c(metadata);

        if (stage < 2) {
            dropped.add(new ItemStack(ModItems.itemDyeBeans, 1, this.dyeMeta));
        } else {
            /**
             *  Fully grown
             *
             *  1+ drop
             *  40% change of pure drop
             *  1 dye bean
             *  20% chance of second dye bean
             */
            int numDrops = world.rand.nextInt(3) + 1;
            for (int i = 0; i < numDrops; i++)
                dropped.add(new ItemStack(ModItems.itemDyeDrop, 1, this.dyeMeta));

            if (world.rand.nextFloat() * 100 <= 40.0F)
                dropped.add(new ItemStack(ModItems.itemDyeDrop, 1, 3));

            dropped.add(new ItemStack(ModItems.itemDyeBeans, 1, this.dyeMeta));
            if (world.rand.nextFloat() * 100 <= 20.0F)
                dropped.add(new ItemStack(ModItems.itemDyeBeans, 1, this.dyeMeta));
        }

        return dropped;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z)
    {
        return ModItems.itemDyeBeans;
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    @Override
    public int getDamageValue(World p_149643_1_, int p_149643_2_, int p_149643_3_, int p_149643_4_)
    {
        return dyeMeta;
    }

}
