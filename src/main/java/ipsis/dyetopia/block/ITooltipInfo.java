package ipsis.dyetopia.block;

import java.util.List;

public interface ITooltipInfo {

    public abstract void getTooltip(List<String> toolTip, boolean showAdvancedItemTooltips, int meta, boolean detail);
}
