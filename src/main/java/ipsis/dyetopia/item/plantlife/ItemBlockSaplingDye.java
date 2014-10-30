package ipsis.dyetopia.item.plantlife;

import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockSaplingDye extends ItemMultiTexture {

    public ItemBlockSaplingDye(Block block) {

        super(ModBlocks.blockSaplingDye, ModBlocks.blockSaplingDye, Names.Blocks.BLOCK_SAPLING_DYE_TYPES);
    }
}
