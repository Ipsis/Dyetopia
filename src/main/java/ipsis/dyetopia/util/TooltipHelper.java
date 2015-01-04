package ipsis.dyetopia.util;


import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.reference.Lang;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class TooltipHelper {

    public static void addMoreInfo(List<String> toolTip) {
        toolTip.add(EnumChatFormatting.AQUA + StringHelper.localize(Lang.Tooltips.HAS_DETAIL));
    }

    public static void addRequires(List<String> toolTip, String ... requires) {

        for (int i = 0; i < requires.length; ++i)
            toolTip.add(EnumChatFormatting.GREEN + StringHelper.localize(requires[i]));
    }
}
