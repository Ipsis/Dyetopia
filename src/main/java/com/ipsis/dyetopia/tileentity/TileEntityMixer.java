package com.ipsis.dyetopia.tileentity;

import com.ipsis.dyetopia.manager.EnergyManager;
import com.ipsis.dyetopia.manager.FactoryManager;
import com.ipsis.dyetopia.manager.MultiBlockPatternManager;
import com.ipsis.dyetopia.util.multiblock.MultiBlockPattern;
import net.minecraft.item.ItemStack;

 public class TileEntityMixer extends TileEntityMultiBlockMaster {

    private MultiBlockPattern pattern = MultiBlockPatternManager.getPattern(MultiBlockPatternManager.Type.MIXER);

    public TileEntityMixer() {
        super();
        this.setMaster(this);
        inventory = new ItemStack[1];
    }

     @Override
     public MultiBlockPattern getPattern() {
         return pattern;
     }
 }
