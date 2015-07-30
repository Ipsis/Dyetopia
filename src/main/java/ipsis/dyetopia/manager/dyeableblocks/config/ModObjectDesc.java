package ipsis.dyetopia.manager.dyeableblocks.config;

import ipsis.dyetopia.util.DyeHelper;

public class ModObjectDesc {

    String name;
    int attr;

    public ModObjectDesc() {
        reset();
    }

    public ModObjectDesc(String name, int attr) {
        this.name = name;
        this.attr = attr;
    }

    public void reset() {
        name = "";
        attr = -1;
    }

    public boolean isValid() {
        return name != null && !name.equals("") && attr >= 0 && attr <= 15;
    }

    public String getName() { return this.name; }
    public int getAttr() { return this.attr; }

    public static class ModObjectDyedDesc extends ModObjectDesc {

        DyeHelper.DyeType dye;

        private ModObjectDyedDesc() { }

        public ModObjectDyedDesc(String name, int attr, DyeHelper.DyeType dye) {
            super(name, attr);
            this.dye = dye;
        }

        @Override
        public boolean isValid() {
            return super.isValid() && dye != null && dye != DyeHelper.DyeType.INVALID;
        }

        @Override
        public void reset() {
            super.reset();
            this.dye = DyeHelper.DyeType.INVALID;
        }
    }
}
