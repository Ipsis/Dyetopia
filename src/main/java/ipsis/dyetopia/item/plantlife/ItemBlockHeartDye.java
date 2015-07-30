package ipsis.dyetopia.item.plantlife;

import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockHeartDye extends ItemMultiTexture {

    public ItemBlockHeartDye(Block block) {

        super(ModBlocks.blockHeartDye, ModBlocks.blockHeartDye, Names.Blocks.BLOCK_HEART_DYE_TYPES);
    }
}
