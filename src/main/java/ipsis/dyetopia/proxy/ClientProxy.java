package ipsis.dyetopia.proxy;

import ipsis.dyetopia.handler.ItemTooltipHandler;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.init.ModBlocks;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import ipsis.dyetopia.util.IconRegistry;
import ipsis.dyetopia.util.multiblock.MultiBlockTextures;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    @SubscribeEvent
    public void textureHook(TextureStitchEvent.Post event) {

        if (event.map.getTextureType() == 0) {
            /* Only register when the terrain texture type */
            ModFluids.fluidDyeRed.setIcons(ModBlocks.blockFluidDyeRed.getBlockTextureFromSide(1), ModBlocks.blockFluidDyeRed.getBlockTextureFromSide(2));
            ModFluids.fluidDyeYellow.setIcons(ModBlocks.blockFluidDyeYellow.getBlockTextureFromSide(1), ModBlocks.blockFluidDyeYellow.getBlockTextureFromSide(2));
            ModFluids.fluidDyeBlue.setIcons(ModBlocks.blockFluidDyeBlue.getBlockTextureFromSide(1), ModBlocks.blockFluidDyeBlue.getBlockTextureFromSide(2));
            ModFluids.fluidDyeWhite.setIcons(ModBlocks.blockFluidDyeWhite.getBlockTextureFromSide(1), ModBlocks.blockFluidDyeWhite.getBlockTextureFromSide(2));
            ModFluids.fluidDyePure.setIcons(ModBlocks.blockFluidDyePure.getBlockTextureFromSide(1), ModBlocks.blockFluidDyePure.getBlockTextureFromSide(2));
        }
    }

    @SubscribeEvent
    public void registerIcons(TextureStitchEvent.Pre event) {

        if (event.map.getTextureType() == 0) {
            MultiBlockTextures.registerIcons(event.map);
        } else if (event.map.getTextureType() == 1) {
            /* Register our gui icons */
            IconRegistry.addIcon("Icon_Energy", event.map.registerIcon("dyetopia:icons/Icon_Energy"));
            IconRegistry.addIcon("Icon_Info", event.map.registerIcon("dyetopia:icons/Icon_Info"));
            IconRegistry.addIcon("Icon_Tank", event.map.registerIcon("dyetopia:icons/Icon_Tank"));

            IconRegistry.addIcon("Icon_Up_Active", event.map.registerIcon("dyetopia:icons/Icon_Up_Active"));
            IconRegistry.addIcon("Icon_Up_Inactive", event.map.registerIcon("dyetopia:icons/Icon_Up_Inactive"));
            IconRegistry.addIcon("Icon_Dn_Active", event.map.registerIcon("dyetopia:icons/Icon_Dn_Active"));
            IconRegistry.addIcon("Icon_Dn_Inactive", event.map.registerIcon("dyetopia:icons/Icon_Dn_Inactive"));
        }
    }

    @Override
    public void registerEventHandlers() {
        super.registerEventHandlers();

        MinecraftForge.EVENT_BUS.register(new ItemTooltipHandler());
    }
}
