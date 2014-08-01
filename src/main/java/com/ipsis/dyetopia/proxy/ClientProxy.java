package com.ipsis.dyetopia.proxy;

import com.ipsis.dyetopia.block.DYTBlocks;
import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.util.LogHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public void textureHook(TextureStitchEvent.Post event) {

        if (event.map.getTextureType() == 0) {
            DYTFluids.fluidDyeRed.setIcons(DYTBlocks.blockFluidDyeRed.getBlockTextureFromSide(1), DYTBlocks.blockFluidDyeRed.getBlockTextureFromSide(2));
            DYTFluids.fluidDyeYellow.setIcons(DYTBlocks.blockFluidDyeYellow.getBlockTextureFromSide(1), DYTBlocks.blockFluidDyeYellow.getBlockTextureFromSide(2));
            DYTFluids.fluidDyeBlue.setIcons(DYTBlocks.blockFluidDyeBlue.getBlockTextureFromSide(1), DYTBlocks.blockFluidDyeBlue.getBlockTextureFromSide(2));
            DYTFluids.fluidDyeWhite.setIcons(DYTBlocks.blockFluidDyeWhite.getBlockTextureFromSide(1), DYTBlocks.blockFluidDyeWhite.getBlockTextureFromSide(2));
            DYTFluids.fluidDyePure.setIcons(DYTBlocks.blockFluidDyePure.getBlockTextureFromSide(1), DYTBlocks.blockFluidDyePure.getBlockTextureFromSide(2));
        }
    }
}
