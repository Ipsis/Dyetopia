package ipsis.dyetopia.item.plantlife;

import ipsis.dyetopia.block.DYTBlocks;
import ipsis.dyetopia.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

public class ItemBlockLeavesDye extends ItemMultiTexture {

    public ItemBlockLeavesDye(Block block) {

        super(DYTBlocks.blockLeavesDye, DYTBlocks.blockLeavesDye, Names.Blocks.BLOCK_SAPLING_DYE_TYPES);
    }
}
