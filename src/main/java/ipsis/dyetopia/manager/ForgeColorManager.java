package ipsis.dyetopia.manager;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ForgeColorManager {

    private static ForgeColorManager manager;
    private List<Block> blacklistBlocks;

    private ForgeColorManager() {
        blacklistBlocks = new ArrayList<Block>();
    }

    public static ForgeColorManager getInstance() {
        if (manager == null)
            manager = new ForgeColorManager();
        return manager;
    }

    public void addBlacklistedModBlocks() {

        /**
         * These are blocks that would return true from recolourBlock but dont work the
         * way that fits in with this items use case.
         */

        /**
         * Extra Utilities
         * Forge color changes the current block through various shades, where I want it to change
         * it to the different color block, so blacklist.
         */
        String xuBlocks[] = {
                "colorStoneBrick",
                "colorWoodPlanks",
                "color_lightgem",
                "color_stone",
                "color_quartzBlock",
                "color_hellsand",
                "color_redstoneLight",
                "color_brick",
                "color_stonebrick",
                "color_blockLapis",
                "color_obsidian",
                "color_blockRedstone",
                "color_blockCoal",
                "greenscreen"
        };
        for (String s : xuBlocks) {
            Block b = GameRegistry.findBlock("ExtraUtilities", s);
            if (b != null) {
                blacklistBlocks.add(b);
            }
        }
    }

    public boolean isBlacklisted(Block b) {
        if (b == null)
            return false;

        return blacklistBlocks.contains(b);
    }
}
