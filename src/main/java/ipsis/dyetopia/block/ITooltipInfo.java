package ipsis.dyetopia.block;

import java.util.List;

public interface ITooltipInfo {

    void getTooltip(List<String> toolTip, boolean showAdvancedItemTooltips, int meta, boolean detail);
}
