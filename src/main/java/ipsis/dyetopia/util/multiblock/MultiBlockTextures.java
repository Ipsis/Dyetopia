package ipsis.dyetopia.util.multiblock;

import cofh.lib.util.position.BlockPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.block.*;
import ipsis.dyetopia.manager.MultiBlockPatternManager;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityMixer;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockMaster;
import ipsis.dyetopia.tileentity.TileEntitySqueezer;
import ipsis.dyetopia.util.BlockLocation;
import ipsis.dyetopia.util.IconRegistry;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class MultiBlockTextures {

    private static void registerIcon(TextureMap map, String key, String texture) {

        String s = Textures.RESOURCE_PREFIX + "machines/multiblock/";
        IconRegistry.addIcon(key, map.registerIcon(s + texture));
    }

    public static void registerIcons(TextureMap map) {

        registerIcon(map, "Wall", "wall");

        registerIcon(map, "TopLeft", "topLeft");
        registerIcon(map, "TopCenter", "topCenter");
        registerIcon(map, "TopRight", "topRight");
        registerIcon(map, "MiddleLeft", "middleLeft");
        registerIcon(map, "Center", "center");
        registerIcon(map, "MiddleRight", "middleRight");
        registerIcon(map, "BottomLeft", "bottomLeft");
        registerIcon(map, "BottomCenter", "bottomCenter");
        registerIcon(map, "BottomRight", "bottomRight");

        registerIcon(map, "ValveTopLeft", "valveTopLeft");
        registerIcon(map, "ValveTopLeftYellow", "valveTopLeftYellow");
        registerIcon(map, "ValveTopLeftRed", "valveTopLeftRed");

        registerIcon(map, "ValveTopRight", "valveTopRight");
        registerIcon(map, "ValveTopRightRed", "valveTopRightRed");
        registerIcon(map, "ValveTopRightYellow", "valveTopRightYellow");

        registerIcon(map, "ValveBottomLeft", "valveBottomLeft");
        registerIcon(map, "ValveBottomLeftWhite", "valveBottomLeftWhite");
        registerIcon(map, "ValveBottomLeftBlue", "valveBottomLeftBlue");

        registerIcon(map, "ValveBottomRight", "valveBottomRight");
        registerIcon(map, "ValveBottomRightBlue", "valveBottomRightBlue");
        registerIcon(map, "ValveBottomRightWhite", "valveBottomRightWhite");

        registerIcon(map, "ValveCenter", "valveCenter");
        registerIcon(map, "ValveCenterPure", "valveCenterPure");

        /* Formed */
        registerIcon(map, "Mixer", "mixer");
        registerIcon(map, "Squeezer", "squeezer");
        registerIcon(map, "MixerActive", "mixerActive");
        registerIcon(map, "SqueezerActive", "squeezerActive");

        /* Unformed */
        registerIcon(map, "MixerUnformed", "mixerUnformed");
        registerIcon(map, "SqueezerUnformed", "squeezerUnformed");
    }

    private static String SQUEEZER_TEXTURES[][] = {
            { "TopLeft", "TopCenter", "TopRight", "MiddleLeft", "Squeezer", "MiddleRight", "BottomLeft", "BottomCenter", "BottomRight" },
            { "ValveTopLeftYellow", "TopCenter", "ValveTopRightRed", "MiddleLeft", "Center", "MiddleRight", "ValveBottomLeftWhite", "BottomCenter", "ValveBottomRightBlue" },
            { "TopLeft", "TopCenter", "TopRight", "MiddleLeft", "Center", "MiddleRight", "BottomLeft", "BottomCenter", "BottomRight" }
    };

    private static String MIXER_TEXTURES[][] = {
            { "TopLeft", "TopCenter", "TopRight", "MiddleLeft", "Mixer", "MiddleRight", "BottomLeft", "BottomCenter", "BottomRight" },
            { "ValveTopLeftRed", "TopCenter", "ValveTopRightYellow", "MiddleLeft", "ValveCenterPure", "MiddleRight", "ValveBottomRightBlue", "BottomCenter", "ValveBottomRightWhite" },
            { "TopLeft", "TopCenter", "TopRight", "MiddleLeft", "Center", "MiddleRight", "BottomLeft", "BottomCenter", "BottomRight" }
    };

    /* Side 0 = front, 1 = rear, 2 = other */
    private static IIcon getFormedIcon(TileEntityMultiBlockMaster master, BlockLocation.MBLocation p, int side) {

        MultiBlockPatternManager.Type type;
        if (master instanceof TileEntitySqueezer)
            type = MultiBlockPatternManager.Type.SQUEEZER;
        else if (master instanceof TileEntityMixer)
            type = MultiBlockPatternManager.Type.MIXER;
        else
            return null;

        String iconName;
        if (side == 0 && p == BlockLocation.MBLocation.CENTER) {
            if (type == MultiBlockPatternManager.Type.SQUEEZER)
                iconName = (master.getStatus() ? "SqueezerActive" : "Squeezer");
            else
                iconName = (master.getStatus() ? "MixerActive" : "Mixer");
        } else {
            if (type == MultiBlockPatternManager.Type.SQUEEZER)
                iconName = SQUEEZER_TEXTURES[side][p.ordinal()];
            else
                iconName = MIXER_TEXTURES[side][p.ordinal()];
        }

        return IconRegistry.getIcon(iconName);
    }

    /**
     * Side is the side facing the player.
     * But we want to lookup the texture based on the players facing direction.
     * Master is not centered
     */
    public static IIcon getIcon(BlockDYTMultiBlock b, TileEntityMultiBlockMaster master, int x, int y, int z, int side) {

        if (b == null)
            return null;

        if (master == null || !master.isStructureValid()) {
            if (b instanceof BlockCasing)
                return IconRegistry.getIcon("Wall");
            else if (b instanceof BlockValve)
                return IconRegistry.getIcon("ValveCenter");
            else if (b instanceof BlockMixer)
                return (side == ForgeDirection.SOUTH.ordinal()) ? IconRegistry.getIcon("Mixer") : IconRegistry.getIcon("Wall");
            else if (b instanceof BlockSqueezer)
                return (side == ForgeDirection.SOUTH.ordinal()) ? IconRegistry.getIcon("Squeezer") : IconRegistry.getIcon("Wall");
            else
                return null;
        }

        /* Make the master centralised */
        BlockPosition p = new BlockPosition(master.xCoord, master.yCoord, master.zCoord, master.getDirectionFacing());
        p.moveBackwards(1);
        BlockLocation info = new BlockLocation(p.x, p.y, p.z, x, y, z, ForgeDirection.getOrientation(side).getOpposite());

        int facing;
        if (side == master.getDirectionFacing().ordinal())
            facing = 0; /* front */
        else if (side == master.getDirectionFacing().getOpposite().ordinal())
            facing = 1; /* back */
        else
            facing = 2;

        return getFormedIcon(master, info.getLocation(), facing);
    }

}
