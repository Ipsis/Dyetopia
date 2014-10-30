package ipsis.dyetopia.proxy;

import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.init.ModBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public void textureHook(TextureStitchEvent.Post event) {

        if (event.map.getTextureType() == 0) {
            ModFluids.fluidDyeRed.setIcons(ModBlocks.blockFluidDyeRed.getBlockTextureFromSide(1), ModBlocks.blockFluidDyeRed.getBlockTextureFromSide(2));
            ModFluids.fluidDyeYellow.setIcons(ModBlocks.blockFluidDyeYellow.getBlockTextureFromSide(1), ModBlocks.blockFluidDyeYellow.getBlockTextureFromSide(2));
            ModFluids.fluidDyeBlue.setIcons(ModBlocks.blockFluidDyeBlue.getBlockTextureFromSide(1), ModBlocks.blockFluidDyeBlue.getBlockTextureFromSide(2));
            ModFluids.fluidDyeWhite.setIcons(ModBlocks.blockFluidDyeWhite.getBlockTextureFromSide(1), ModBlocks.blockFluidDyeWhite.getBlockTextureFromSide(2));
            ModFluids.fluidDyePure.setIcons(ModBlocks.blockFluidDyePure.getBlockTextureFromSide(1), ModBlocks.blockFluidDyePure.getBlockTextureFromSide(2));
        }
    }
}
