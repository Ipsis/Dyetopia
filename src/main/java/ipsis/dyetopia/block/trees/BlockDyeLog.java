package ipsis.dyetopia.block.trees;


import ipsis.dyetopia.creative.CreativeTab;
import ipsis.dyetopia.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * The Dye tree wood blocks
 *
 * BiomesOPlenty/src/main/java/biomesoplenty/common/blocks/BlockBOPLog.java
 *
 *
 * type 0 - log
 * type 1 - root
 *
 * orientation 0 - y
 * orientation 4 - z
 * orientation 8 - x
 *
 * Meta : 0x3 - type
 * Meta : 0xC - orientation
 *
 */
public class BlockDyeLog extends Block {

    private IIcon[] textures;
    private IIcon[] logHearts;

    public BlockDyeLog()
    {
        super(Material.wood);

        this.setHardness(2.0F);
        this.setHarvestLevel("axe", 0);

        this.setResistance(5.0F);
        this.setStepSound(Block.soundTypeWood);

        this.setCreativeTab(CreativeTab.DYT_TAB);
    }

    private int getTypeFromMeta(int meta) {

        return MathHelper.clamp_int(meta & 0x3, 0, 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

        textures = new IIcon[2];
        logHearts = new IIcon[2];

        textures[0] = iconRegister.registerIcon(String.format(Reference.MOD_ID + ":tree/" + "barkDye"));
        textures[1] = iconRegister.registerIcon(String.format(Reference.MOD_ID + ":tree/" + "barkRootDye"));

        logHearts[0] = iconRegister.registerIcon(String.format(Reference.MOD_ID + ":tree/" + "heartDye"));
        logHearts[1] = iconRegister.registerIcon(String.format(Reference.MOD_ID + ":tree/" + "heartRootDye"));
    }

    @Override
    public void getSubBlocks(Item block, CreativeTabs creativeTabs, List list)
    {
        for (int i = 0; i < 2; ++i) {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        int pos = meta & 0xC;
        int type = getTypeFromMeta(meta);

        if (pos == 0 && (side == 1 || side == 0) || pos == 4 && (side == 5 || side == 4) || pos == 8 && (side == 2 || side == 3))
            return logHearts[type];
        else
            return textures[type];
    }

    @Override
    public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
    {
        int type = getTypeFromMeta(meta);
        byte orientation = 0;

        switch (side)
        {
            case 0:
            case 1:
                orientation = 0;
                break;

            case 2:
            case 3:
                orientation = 8;
                break;

            case 4:
            case 5:
                orientation = 4;
        }

        return type | orientation;
    }

    @Override
    public int damageDropped(int meta)
    {
        return getTypeFromMeta(meta);
    }

    @Override
    protected ItemStack createStackedBlock(int meta)
    {
        return new ItemStack(this, 1, getTypeFromMeta(meta));
    }

    @Override
    public int getRenderType()
    {
        return 31;
    }

    @Override
    public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }
}
