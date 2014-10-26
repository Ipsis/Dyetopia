package ipsis.dyetopia.block.plantlife;

import ipsis.dyetopia.creative.CreativeTab;
import ipsis.dyetopia.reference.Reference;
import net.minecraft.block.BlockLeaves;

public abstract class BlockLeavesDYT extends BlockLeaves {

    public BlockLeavesDYT() {
        super();
        setCreativeTab(CreativeTab.DYT_TAB);
    }

    @Override
    public String getUnlocalizedName()
    {
        return String.format("tile.%s%s", Reference.MOD_ID + ":", getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName)
    {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }
}
