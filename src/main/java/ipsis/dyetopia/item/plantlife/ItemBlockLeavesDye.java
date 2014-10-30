package ipsis.dyetopia.item.plantlife;

import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockLeavesDye extends ItemMultiTexture {

    public ItemBlockLeavesDye(Block block) {

        super(ModBlocks.blockLeavesDye, ModBlocks.blockLeavesDye, Names.Blocks.BLOCK_SAPLING_DYE_TYPES);
    }
}
