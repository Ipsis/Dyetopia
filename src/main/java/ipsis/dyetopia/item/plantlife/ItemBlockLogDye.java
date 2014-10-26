package ipsis.dyetopia.item.plantlife;

import ipsis.dyetopia.block.DYTBlocks;
import ipsis.dyetopia.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockLogDye extends ItemMultiTexture {

    public ItemBlockLogDye(Block block) {

        super(DYTBlocks.blockLogDye, DYTBlocks.blockLogDye, Names.BLOCK_SAPLING_DYE_TYPES);
    }
}
