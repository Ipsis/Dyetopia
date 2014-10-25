package ipsis.dyetopia.util.multiblock;

import ipsis.dyetopia.util.LogHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class MultiBlockPattern {

    private int slices;
    private int rows;
    private int cols;

    private String pattern[][];
    private HashMap<Character, ItemStack> charMap;

    private MultiBlockPattern() { }
    public MultiBlockPattern(int slices, int rows, int cols) {
        this.slices = slices;
        this.rows = rows;
        this.cols = cols;

        this.pattern = new String[slices][rows];
        this.charMap = new HashMap<Character, ItemStack>();
    }

    public int getSlices() {
        return this.slices;
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public boolean addSlice(int slice, String... desc) {

        if (desc.length > this.rows) {
            LogHelper.error("MultiBlockPattern incorrect row size");
            return false;
        }

        for (int i = 0; i < desc.length; i++) {
            if (desc[i].length() > this.cols) {
                LogHelper.error("MultiBlockPattern incorrect col size");
                return false;
            }
            this.pattern[slice][i] = desc[i];
        }

        return true;
    }

    public void addCharMap(Character c, ItemStack itemStack) {
        this.charMap.put(c, itemStack);
    }

    public boolean validatePattern() {

        for (int slice = this.slices; slice < this.slices; slice++) {
            for (int row = this.rows; row < this.rows; row++) {
                for (int col = this.cols; col < this.cols; col++) {
                    Character c = this.pattern[slice][row].charAt(col);
                    if (c == null) {
                        LogHelper.error("validatePattern: invalid character in pattern " + slice + " " + row + " " + col);
                        return false;
                    }

                    if (this.charMap.get(c) == null) {
                        LogHelper.error("validatePattern: invalid character lookup for " + c);
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public ItemStack getItemStackAt(int slice, int row, int col) {
        if (slice >= this.slices || row >= this.rows || col >= this.cols)
            return null;

        return this.charMap.get(this.pattern[slice][row].charAt(col));
    }

}
