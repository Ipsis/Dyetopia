package ipsis.dyetopia.item.plantlife;

import ipsis.dyetopia.block.DYTBlocks;
import ipsis.dyetopia.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;

/**
 * Created by Ipsis on 27/10/2014.
 */
public class ItemBlockHeartDye extends ItemMultiTexture {

    public ItemBlockHeartDye(Block block) {

        super(DYTBlocks.blockHeartDye, DYTBlocks.blockHeartDye, Names.Blocks.BLOCK_HEART_DYE_TYPES);
    }
}
