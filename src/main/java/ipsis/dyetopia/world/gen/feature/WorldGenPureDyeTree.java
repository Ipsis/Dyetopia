package ipsis.dyetopia.world.gen.feature;

import ipsis.dyetopia.block.DYTBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

/**
 * This tree has roots!
 */

public class WorldGenPureDyeTree extends WorldGenAbstractTree {

    private static final int minTreeHeight = 9;

    public WorldGenPureDyeTree() { super(false); }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {

        int numLogs = rand.nextInt(3) + this.minTreeHeight;

        /* Do we have space for the soil and roots */
        if (y < 2)
            return false;

        /* Are we exceeding the world height : numLogs + 1 leaf + 1 + 2 root */
        if (y + numLogs + 1 + 2 > 256)
            return false;

        Block soilBlock = world.getBlock(x, y - 1, z);
        if (!soilBlock.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) DYTBlocks.blockSaplingDye))
            return false;

        /**
         * Is there space for the tree to grow
         *
         */
        int radius;
        for (int yOff = -2; yOff < y + numLogs + 2; yOff++) {
            if (yOff > 0 && yOff < y + numLogs + 1 - 3)
                radius = 1;
            else
                radius = 2;

            for (int xOff = x - radius; xOff <= x + radius; xOff++) {
                for (int zOff = z - radius; zOff <= z + radius; zOff++) {
                    if (!this.isReplaceable(world, y + yOff, xOff, zOff))
                        return false;
                }
            }
        }

        soilBlock.onPlantGrow(world, x, y - 1, z, x, y, z);

        /**
         * Place the logs
         */
        for (int yOff = 0; yOff < numLogs; yOff++) {

            putLog(world, x, y + yOff, z);

            if (yOff == 0) {

                for (int xOff = x - 1; xOff <= x + 1; xOff++) {
                    for (int zOff = z - 1; zOff <= z + 1; zOff++) {
                        if (xOff == 0 && zOff == 0)
                            continue;

                        putLog(world, xOff, y + yOff, zOff);
                    }
                }

                for (int c = 0; c < 4; c++) {

                    int logX = x;
                    int logZ = z;

                    if (c == 0)
                        logX = x - 2;
                    else if (c == 1)
                        logX = x + 2;
                    else if (c == 2)
                        logZ = z - 2;
                    else
                        logZ = z + 2;

                    putLog(world, logX, y + yOff, logZ);
                }
            } else if (yOff == 1) {

                for (int c = 0; c < 4; c++) {

                    int logX = x;
                    int logZ = z;

                    if (c == 0)
                        logX = x - 1;
                    else if (c == 1)
                        logX = x + 1;
                    else if (c == 2)
                        logZ = z - 1;
                    else
                        logZ = z + 1;

                    putLog(world, logX, y + yOff, logZ);
                }
            }
        }

        /**
         * Place the roots
         */
        putRoot(world, x - 2, y - 1, z);
        putRoot(world, x - 2, y - 2, z);
        putRoot(world, x + 2, y - 1, z);
        putRoot(world, x + 2, y - 2, z);
        putRoot(world, x, y - 1, z - 2);
        putRoot(world, x, y - 2, z - 2);
        putRoot(world, x, y - 1, z + 2);
        putRoot(world, x, y - 2, z + 2);


        /**
         * Place the leaves
         */
        int i1 = numLogs - rand.nextInt(2) - 3;
        int j1 = numLogs - i1;
        int k1 = 1 + rand.nextInt(j1 + 1);

        int i3 = 0;
        for (int currY = y + numLogs; currY >= y + i1; --currY)
        {
            for (int currX = x - i3; currX <= x + i3; ++currX)
            {
                int j3 = currX - x;

                for (int currZ = z - i3; currZ <= z + i3; ++currZ)
                {
                    int l2 = currZ - z;

                    if ((Math.abs(j3) != i3 || Math.abs(l2) != i3 || i3 <= 0) && world.getBlock(currX, currY, currZ).canBeReplacedByLeaves(world, currX, currY, currZ))
                    {
                        this.setBlockAndNotifyAdequately(world, currX, currY, currZ, DYTBlocks.blockLeavesDye, 3);
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

        return true;
    }

    private void putLog(World world, int x, int y, int z) {

        Block rBlock = world.getBlock(x, y, z);
        if (rBlock.isAir(world, x, y, z) || rBlock.isLeaves(world, x, y, z))
            this.setBlockAndNotifyAdequately(world, x, y, z, DYTBlocks.blockLogDye, 3);
    }

    private void putRoot(World world, int x, int y, int z) {

        Block rBlock = world.getBlock(x, y, z);
        if (rBlock != Blocks.bedrock && (rBlock.isAir(world, x, y, z) || rBlock == Blocks.grass || rBlock == Blocks.dirt || rBlock == Blocks.farmland))
            this.setBlockAndNotifyAdequately(world, x, y, z, DYTBlocks.blockRootPureDye, 3);
    }
}
