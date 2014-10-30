package ipsis.dyetopia.item.plantlife;

import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockLogDye extends ItemMultiTexture {

    public ItemBlockLogDye(Block block) {

        super(ModBlocks.blockLogDye, ModBlocks.blockLogDye, Names.Blocks.BLOCK_SAPLING_DYE_TYPES);
    }
}
