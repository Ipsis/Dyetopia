package ipsis.dyetopia.manager;

import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.util.multiblock.MultiBlockPattern;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class MultiBlockPatternManager {

    public enum Type {
        SQUEEZER,
        MIXER
    }

    private static HashMap<Type,MultiBlockPattern> patternMap = new HashMap<Type,MultiBlockPattern>();

    private static void addSqueezer() {

        MultiBlockPattern pattern = new MultiBlockPattern(3, 3, 3);
        pattern.addSlice(0, "sss", "sss", "vsv");
        pattern.addSlice(1, "sqs", "scs", "sss");
        pattern.addSlice(2, "sss", "sss", "vsv");
        pattern.addCharMap('s', new ItemStack(ModBlocks.blockCasing));
        pattern.addCharMap('v', new ItemStack(ModBlocks.blockValve));
        pattern.addCharMap('c', new ItemStack(ModBlocks.blockController));
        pattern.addCharMap('q', new ItemStack(ModBlocks.blockSqueezer));
        if (pattern.validatePattern())
            patternMap.put(Type.SQUEEZER, pattern);

    }

    private static void addMixer() {

        MultiBlockPattern pattern = new MultiBlockPattern(3, 3, 3);
        pattern.addSlice(0, "sss", "sss", "vsv");
        pattern.addSlice(1, "sms", "scs", "svs");
        pattern.addSlice(2, "sss", "sss", "vsv");
        pattern.addCharMap('s', new ItemStack(ModBlocks.blockCasing));
        pattern.addCharMap('v', new ItemStack(ModBlocks.blockValve));
        pattern.addCharMap('c', new ItemStack(ModBlocks.blockController));
        pattern.addCharMap('m', new ItemStack(ModBlocks.blockMixer));
        if (pattern.validatePattern())
            patternMap.put(Type.MIXER, pattern);
    }

    public static void registerPatterns() {

        addSqueezer();
        addMixer();
    }

    public static MultiBlockPattern getPattern(Type patternType) {
        return patternMap.get(patternType);
    }
}
