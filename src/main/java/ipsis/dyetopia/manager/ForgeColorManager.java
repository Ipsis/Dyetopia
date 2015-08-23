package ipsis.dyetopia.manager;

import cpw.mods.fml.common.registry.GameRegistry;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ForgeColorManager {

    private static ForgeColorManager manager;
    private List<Block> validBlocks;

    private ForgeColorManager() {
        validBlocks = new ArrayList<Block>();
    }

    public static ForgeColorManager getInstance() {
        if (manager == null)
            manager = new ForgeColorManager();
        return manager;
    }

    public void addModItems() {

        /* AE2 */
        String ae2blocks[] = { "tile.BlockCableBus" };
        for (String s : ae2blocks) {
            Block b = GameRegistry.findBlock("appliedenergistics2", s);
            if (b != null) {
                validBlocks.add(b);
            }
        }
    }

    public boolean isValid(Block b) {
        if (b == null)
            return false;

        return validBlocks.contains(b);
    }
}
