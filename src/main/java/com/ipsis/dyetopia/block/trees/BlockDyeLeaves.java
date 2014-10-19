package com.ipsis.dyetopia.block.trees;

import com.ipsis.dyetopia.creative.CreativeTab;
import com.ipsis.dyetopia.reference.Reference;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.IIcon;

public class BlockDyeLeaves extends BlockLeaves {

    public BlockDyeLeaves() {

        super();
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTab.DYT_TAB);
        this.setHardness(0.2F);
        this.setLightOpacity(1);
        this.setStepSound(soundTypeGrass);
        this.setBlockName("dyeLeaves");
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return field_150129_M[(!isOpaqueCube() ? 0 : 1)][0];
    }

    @Override
    public String[] func_150125_e() {
        return new String[0];
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {

        field_150129_M[0][0] = iconRegister.registerIcon(String.format(Reference.MOD_ID + ":tree/leavesDyePure"));
        field_150129_M[1][0] = iconRegister.registerIcon(String.format(Reference.MOD_ID + ":tree/leavesDyePure"));
    }
}
