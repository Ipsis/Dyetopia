package ipsis.dyetopia.block.trees;

import ipsis.dyetopia.block.DYTBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class DyeTree extends WorldGenAbstractTree {

    public DyeTree() {
        super(false);
    }

    /**
     * The tree height defines the number of logs.
     * We require space for the logs + 1 top leaf + 1 air above.
     * This means that required space is height + 2
     * A tree of height h takes up blocks y -> y + height - 1
     */
    public boolean generate2(World world, Random rand, int x, int y, int z, int meta) {

        int height = rand.nextInt(3) + 5;

        /* Do we have space for the soil and roots */
        if (y < 1)
            return false;

        /* Are we exceeding the world height : log height + 1 leaf + 1 */
        if (y + height + 1 > 256)
            return false;

        /**
         *  Is there space for the tree
         */

        /* Is there soil below the tree */
        Block soilBlock = world.getBlock(x, y - 1, z);
        if (!soilBlock.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable)DYTBlocks.blockSaplingDye))
            return false;

        soilBlock.onPlantGrow(world, x, y - 1, z, x, y, z);

        /* Place leaves */
        int i1 = height - rand.nextInt(2) - 3;
        int j1 = height - i1;
        int k1 = 1 + rand.nextInt(j1 + 1);

        int i3 = 0;
        for (int currY = y + height; currY >= y + i1; --currY)
        {
            for (int currX = x - i3; currX <= x + i3; ++currX)
            {
                int j3 = currX - x;

                for (int currZ = z - i3; currZ <= z + i3; ++currZ)
                {
                    int l2 = currZ - z;

                    if ((Math.abs(j3) != i3 || Math.abs(l2) != i3 || i3 <= 0) && world.getBlock(currX, currY, currZ).canBeReplacedByLeaves(world, currX, currY, currZ))
                    {
                        this.setBlockAndNotifyAdequately(world, currX, currY, currZ, DYTBlocks.blockLeavesDye, meta);
                    }
                }
            }

            if (i3 >= 1 && currY == y + i1 + 1)
            {
                --i3;
            }
            else if (i3 < k1)
            {
                ++i3;
            }
        }

        /* Place logs */
        for (int yOff = 0; yOff < height - 1; yOff++)
        {
            Block rBlock = world.getBlock(x, y + yOff, z);

            if (rBlock.isAir(world, x, y + yOff, z) || rBlock.isLeaves(world, x, y + yOff, z))
            {
                this.setBlockAndNotifyAdequately(world, x, y + yOff, z, DYTBlocks.blockLogDye, meta);
            }
        }

        /* Place root */

        return true;
    }

    @Override
    public boolean generate(World p_76484_1_, Random p_76484_2_, int p_76484_3_, int p_76484_4_, int p_76484_5_) {

        /* Standard tree gen Taiga1 for now */
        int l = p_76484_2_.nextInt(5) + 7;
        int i1 = l - p_76484_2_.nextInt(2) - 3;
        int j1 = l - i1;
        int k1 = 1 + p_76484_2_.nextInt(j1 + 1);
        boolean flag = true;

        if (p_76484_4_ >= 1 && p_76484_4_ + l + 1 <= 256)
        {
            int i2;
            int j2;
            int i3;

            for (int l1 = p_76484_4_; l1 <= p_76484_4_ + 1 + l && flag; ++l1)
            {
                boolean flag1 = true;

                if (l1 - p_76484_4_ < i1)
                {
                    i3 = 0;
                }
                else
                {
                    i3 = k1;
                }

                for (i2 = p_76484_3_ - i3; i2 <= p_76484_3_ + i3 && flag; ++i2)
                {
                    for (j2 = p_76484_5_ - i3; j2 <= p_76484_5_ + i3 && flag; ++j2)
                    {
                        if (l1 >= 0 && l1 < 256)
                        {
                            Block block = p_76484_1_.getBlock(i2, l1, j2);

                            if (!this.isReplaceable(p_76484_1_, i2, l1, j2))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block block1 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ - 1, p_76484_5_);

                boolean isSoil = block1.canSustainPlant(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, ForgeDirection.UP, (BlockSapling) Blocks.sapling);
                if (isSoil && p_76484_4_ < 256 - l - 1)
                {
                    block1.onPlantGrow(p_76484_1_, p_76484_3_, p_76484_4_ - 1, p_76484_5_, p_76484_3_, p_76484_4_, p_76484_5_);
                    i3 = 0;

                    for (i2 = p_76484_4_ + l; i2 >= p_76484_4_ + i1; --i2)
                    {
                        for (j2 = p_76484_3_ - i3; j2 <= p_76484_3_ + i3; ++j2)
                        {
                            int j3 = j2 - p_76484_3_;

                            for (int k2 = p_76484_5_ - i3; k2 <= p_76484_5_ + i3; ++k2)
                            {
                                int l2 = k2 - p_76484_5_;

                                if ((Math.abs(j3) != i3 || Math.abs(l2) != i3 || i3 <= 0) && p_76484_1_.getBlock(j2, i2, k2).canBeReplacedByLeaves(p_76484_1_, j2, i2, k2))
                                {
                                    this.setBlockAndNotifyAdequately(p_76484_1_, j2, i2, k2, Blocks.leaves, 1);
                                }
                            }
                        }

                        if (i3 >= 1 && i2 == p_76484_4_ + i1 + 1)
                        {
                            --i3;
                        }
                        else if (i3 < k1)
                        {
                            ++i3;
                        }
                    }

                    for (i2 = 0; i2 < l - 1; ++i2)
                    {
                        Block block2 = p_76484_1_.getBlock(p_76484_3_, p_76484_4_ + i2, p_76484_5_);

                        if (block2.isAir(p_76484_1_, p_76484_3_, p_76484_4_ + i2, p_76484_5_) || block2.isLeaves(p_76484_1_, p_76484_3_, p_76484_4_ + i2, p_76484_5_))
                        {
                            //this.setBlockAndNotifyAdequately(p_76484_1_, p_76484_3_, p_76484_4_ + i2, p_76484_5_, DYTBlocks.blockDyeLog, 1);
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
}
