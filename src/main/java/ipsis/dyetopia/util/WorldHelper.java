package ipsis.dyetopia.util;


import net.minecraft.block.Block;
import net.minecraft.world.World;

public class WorldHelper {

    /** Only returns the block if the chunk is loaded */
    public static Block getBlockChunkLoaded(World world, int x, int y, int z) {

        Block b = null;

        if (world != null && world.blockExists(x, y, z))
            b = world.getBlock(x, y, z);

        return b;
    }
}
