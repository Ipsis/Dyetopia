package ipsis.dyetopia.manager.dyeableblocks.config;

public abstract class BlockDescBase {

    public String refname;
    public boolean associative;
    public boolean validForBlock;

    /* Optional origin */
    ModObjectDesc origin;

    public abstract boolean isValid();

    public BlockDescBase() {

        associative = false;
        validForBlock = true;
        origin = new ModObjectDesc();
    }

    public boolean isAssociative() { return this.associative; }

    public boolean hasOrigin() {
        return origin.isValid() && !DyeableBlocksConfigManager.getInstance().isBlockBlacklisted(origin.name);
    }

    public void setOrigin(String name, int attr) {
        origin.name = name;
        origin.attr = attr;
    }

    public void setOrigin(ModObjectDesc origin) {
        setOrigin(origin.getName(), origin.getAttr());
    }

    public ModObjectDesc getOrigin() {
        return hasOrigin() ? origin : null;
    }
}
