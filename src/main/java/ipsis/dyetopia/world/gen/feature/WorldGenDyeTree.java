package ipsis.dyetopia.world.gen.feature;

import ipsis.dyetopia.block.DYTBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

/**
 * Based off WorldGenOriginalTree from BiomesOPlenty
 * Based off the growth pattern from vanilla Taiga1
 */
public class WorldGenDyeTree extends WorldGenAbstractTree {

    private Block wood;
    private Block leaves;
    private int metaWood;
    private int metaLeaves;
    private int minTreeHeight;

    public WorldGenDyeTree(Block wood, Block leaves, int metaWood, int metaLeaves) {

        this(wood, leaves, metaWood, metaLeaves, false, 6);
    }

    public WorldGenDyeTree(Block wood, Block leaves, int metaWood, int metaLeaves, boolean doBlockNotify, int minTreeHeight) {
        super(doBlockNotify);
        this.wood = wood;
        this.leaves = leaves;
        this.metaWood = metaWood;
        this.metaLeaves = metaLeaves;
        this.minTreeHeight = minTreeHeight;
    }

    /**
     * The tree height defines the number of logs.
     * We require space for the logs + 1 top leaf + 1 air above.
     * This means that required space is numLogs + 2
     * A tree of numLogs  takes up blocks y -> y + numLogs - 1
     *
     */
    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {

        int numLogs = rand.nextInt(3) + this.minTreeHeight;

        /* Do we have space for the soil and roots */
        if (y < 1)
            return false;

        /* Are we exceeding the world height : numLogs + 1 leaf + 1 */
        if (y + numLogs + 1 > 256)
            return false;

        Block soilBlock = world.getBlock(x, y - 1, z);
        if (!soilBlock.canSustainPlant(world, x, y, z, ForgeDirection.UP, (IPlantable) DYTBlocks.blockSaplingDye))
            return false;

        /**
         * Is there space for the tree to grow
         *
         */
        int radius;
        for (int yOff = 0; yOff < y + numLogs + 2; yOff++) {
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
                        this.setBlockAndNotifyAdequately(world, currX, currY, currZ, leaves, metaLeaves);
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

        /**
         * Place the logs
         */
        for (int yOff = 0; yOff < numLogs; yOff++) {

            if (yOff == 0) {

                Block rBlock = world.getBlock(x, y, z);
                if (rBlock.isAir(world, x, y, z) || rBlock.isLeaves(world, x, y + yOff, z))
                    this.setBlockAndNotifyAdequately(world, x, y, z, DYTBlocks.blockHeartDye, this.metaWood);

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

                    rBlock = world.getBlock(logX, y, logZ);
                    if (rBlock.isAir(world, logX, y, logZ) || rBlock.isLeaves(world, logX, y, logZ))
                        this.setBlockAndNotifyAdequately(world, logX, y , logZ, DYTBlocks.blockLogDye, this.metaWood);

                }
            } else {

                Block rBlock = world.getBlock(x, y + yOff, z);
                if (rBlock.isAir(world, x, y + yOff, z) || rBlock.isLeaves(world, x, y + yOff, z))
                    this.setBlockAndNotifyAdequately(world, x, y + yOff, z, this.wood, this.metaWood);
            }
        }



        return true;
    }
}
