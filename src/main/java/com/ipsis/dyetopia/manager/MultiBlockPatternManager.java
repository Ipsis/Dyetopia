package com.ipsis.dyetopia.manager;

import com.ipsis.dyetopia.block.DYTBlocks;
import com.ipsis.dyetopia.util.multiblock.MultiBlockPattern;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

public class MultiBlockPatternManager {

    public static enum Type {
        SQUEEZER,
        MIXER
    }

    private static HashMap<Type,MultiBlockPattern> patternMap = new HashMap<Type,MultiBlockPattern>();

    public static void registerPatterns() {

        MultiBlockPattern squeezerPattern = new MultiBlockPattern(3, 3, 3);
        squeezerPattern.addSlice(0, "ssv", "sss", "ssv");
        squeezerPattern.addSlice(1, "sqs", "scs", "sss");
        squeezerPattern.addSlice(2, "ssv", "sss", "ssv");
        squeezerPattern.addCharMap('s', new ItemStack(DYTBlocks.blockCasing));
        squeezerPattern.addCharMap('v', new ItemStack(DYTBlocks.blockValve));
        squeezerPattern.addCharMap('c', new ItemStack(DYTBlocks.blockController));
        squeezerPattern.addCharMap('q', new ItemStack(DYTBlocks.blockSqueezer));
        if (squeezerPattern.validatePattern())
            patternMap.put(Type.SQUEEZER, squeezerPattern);

    }

    public static MultiBlockPattern getPattern(Type patternType) {
        return patternMap.get(patternType);
    }
}
