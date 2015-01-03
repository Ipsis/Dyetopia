package ipsis.dyetopia.manager.dyeableblocks.config;

import ipsis.dyetopia.handler.DyeFileHandler;
import ipsis.dyetopia.util.DyeHelper;

public class BlockDescSimple extends BlockDescBase {

    /* Required base object name */
    String baseName;

    /* Vanilla WHITE = 0, BLACK = 15 */
    boolean vanillaOrder;
    
    public BlockDescSimple() {
        super();
        this.vanillaOrder = true;
    }

    @Override
    public boolean isValid() {
        return baseName != null && !baseName.equals("") && !DyeableBlocksConfigManager.getInstance().isBlockBlacklisted(baseName);
    }

    public int getColorAttr(DyeHelper.DyeType dye) {

        /* Vanilla white dye dmg = 16, but wool white dmg is 0 */
        if (isVanillaOrder())
            return 15 - dye.getDmg();
        else
            return dye.getDmg();
    }

    public void setBaseName(String baseName) { this.baseName = baseName; }
    public String getBaseName() { return this.baseName; }
    public boolean isVanillaOrder() { return this.vanillaOrder; }
    public void setVanillaOrder(boolean vanillaOrder) { this.vanillaOrder = vanillaOrder; }
}
