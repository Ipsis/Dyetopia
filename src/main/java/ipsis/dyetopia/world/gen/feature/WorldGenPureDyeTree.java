package ipsis.dyetopia.world.gen.feature;

import ipsis.dyetopia.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

/**
 * This tree has roots!
 */

public class WorldGenPureDyeTree extends WorldGenAbstractTree {

    private static final int minTreeHeight = 12;

    public WorldGenPureDyeTree() { super(false); }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {

        int numLogs = rand.nextInt(3) + this.minTreeHeight;

        /* Do we have space for the soil and roots */
        if (y < 2)
            return false;

        /* Is the tree + 1 leaf exceeding the last valid block placed value */
        if (y + numLogs + 1 - 1 > world.getHeight() - 2)
            return false;

        Block soilBlock = world.getBlock(x, y - 1, z);
        if (!soilBlock.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) ModBlocks.blockSaplingDye))
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

            for (int xPos = x - radius; xPos <= x + radius; xPos++) {
                for (int zPos = z - radius; zPos <= z + radius; zPos++) {
                    if (!this.isReplaceable(world, xPos, y + yOff, zPos))
                        return false;
                }
            }
        }

        soilBlock.onPlantGrow(world, x, y - 1, z, x, y, z);

        /**
         * Place the leaves
         */
        for (int currY = y + 4; currY < y + numLogs; currY++) {

            if (currY >= y + 4 && currY < y + numLogs - 2) {
                putLayer(world, x, currY, z, 4);
            } else if (currY == y + numLogs - 2) {
                putLayer(world, x, currY, z, 3);
            } else if (currY == y + numLogs - 1) {
                putLayer(world, x, currY, z, 2);
            } else if (currY == y + numLogs) {

                putLeaf(world, x - 1, currY, z);
                putLeaf(world, x + 1, currY, z);
                putLeaf(world, x, currY, z - 1);
                putLeaf(world, x, currY, z + 1);
                putLeaf(world, x, currY, z);
            }
        }

        putLeaf(world, x, y + numLogs, z);

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

        return true;
    }

    private void putLayer(World world, int x, int y, int z, int radius) {

        for (int r = radius * -1; r <= radius; r++) {

            int currX = x + r;
            int diff = (MathHelper.abs_int(r) * -1) + radius;

            for (int currZ = z - diff; currZ <= z + diff; currZ++)
                putLeaf(world, currX, y, currZ);
        }
    }

    private void putLog(World world, int x, int y, int z) {

        Block rBlock = world.getBlock(x, y, z);
        if (rBlock.isAir(world, x, y, z) || rBlock.isLeaves(world, x, y, z) || func_150523_a(rBlock))
            this.setBlockAndNotifyAdequately(world, x, y, z, ModBlocks.blockLogDye, 3);
    }

    private void putRoot(World world, int x, int y, int z) {

        Block rBlock = world.getBlock(x, y, z);
        if (rBlock != Blocks.bedrock && (rBlock.isAir(world, x, y, z) || rBlock == Blocks.grass || rBlock == Blocks.dirt || rBlock == Blocks.farmland))
            this.setBlockAndNotifyAdequately(world, x, y, z, ModBlocks.blockRootPureDye, 0);
    }

    private void putLeaf(World world, int x, int y, int z) {

        Block rBlock = world.getBlock(x, y, z);
        if (rBlock.isAir(world, x, y, z) || rBlock.isLeaves(world, x, y, z))
        {
            //this.setBlockAndNotifyAdequately(world, x, y, z, DYTBlocks.blockLeavesDye, world.rand.nextInt(4));
            this.setBlockAndNotifyAdequately(world, x, y, z, ModBlocks.blockLeavesDye, 3);
        }
    }
}
