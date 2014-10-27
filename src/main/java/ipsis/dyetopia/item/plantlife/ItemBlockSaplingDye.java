package ipsis.dyetopia.item.plantlife;

import ipsis.dyetopia.block.DYTBlocks;
import ipsis.dyetopia.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockSaplingDye extends ItemMultiTexture {

    public ItemBlockSaplingDye(Block block) {

        super(DYTBlocks.blockSaplingDye, DYTBlocks.blockSaplingDye, Names.Blocks.BLOCK_SAPLING_DYE_TYPES);
    }
}
