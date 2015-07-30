package ipsis.dyetopia.util;


import cofh.lib.util.position.BlockPosition;
import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WorldHelper {

    /** Only returns the block if the chunk is loaded */
    public static Block getBlockChunkLoaded(World world, int x, int y, int z) {

        Block b = null;

        if (world != null && world.blockExists(x, y, z))
            b = world.getBlock(x, y, z);

        return b;
    }

    public static List<BlockPosition> getSurroundingBlockPositions(int x, int y, int z, int radius) {

        List<BlockPosition> posList = new ArrayList<BlockPosition>();

        if (radius < 1)
            return posList;

        for (int yOff = -radius; yOff <= radius; yOff++) {
            for (int xOff = -radius; xOff <= radius; xOff++) {
                for (int zOff = -radius; zOff <= radius; zOff++) {
                    posList.add(new BlockPosition(x + xOff, y + yOff, z + zOff));
                }
            }
        }

        return posList;

    }
}
