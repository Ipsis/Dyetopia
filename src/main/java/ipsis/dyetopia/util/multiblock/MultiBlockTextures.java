package ipsis.dyetopia.util.multiblock;

import cofh.lib.util.position.BlockPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockMaster;
import ipsis.dyetopia.util.IconRegistry;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

import javax.swing.*;

@SideOnly(Side.CLIENT)
public class MultiBlockTextures {

    public enum MultiBlockType {
        CASING,
        VALVE,
        SQUEEZER,
        MIXER;
    }

    public static void registerIcons(TextureMap map) {

        String s = Textures.RESOURCE_PREFIX + "machines/multiblock/";

        IconRegistry.addIcon("Wall", map.registerIcon(s + "wall"));
        IconRegistry.addIcon("Mixer", map.registerIcon(s + "mixer"));
        IconRegistry.addIcon("Squeezer", map.registerIcon(s + "squeezer"));

        IconRegistry.addIcon("TopLeft", map.registerIcon(s + "topLeft"));
        IconRegistry.addIcon("TopCenter", map.registerIcon(s + "topCenter"));
        IconRegistry.addIcon("TopRight", map.registerIcon(s + "topRight"));

        IconRegistry.addIcon("MiddleLeft", map.registerIcon(s + "middleLeft"));
        IconRegistry.addIcon("Center", map.registerIcon(s + "center"));
        IconRegistry.addIcon("MiddleRight", map.registerIcon(s + "middleRight"));

        IconRegistry.addIcon("BottomLeft", map.registerIcon(s + "bottomLeft"));
        IconRegistry.addIcon("BottomCenter", map.registerIcon(s + "bottomCenter"));
        IconRegistry.addIcon("BottomRight", map.registerIcon(s + "bottomRight"));

        IconRegistry.addIcon("ValveTopLeft", map.registerIcon(s + "valveTopLeft"));
        IconRegistry.addIcon("ValveTopRight", map.registerIcon(s + "valveTopRight"));
        IconRegistry.addIcon("ValveBottomLeft", map.registerIcon(s + "valveBottomLeft"));
        IconRegistry.addIcon("ValveBottomRight", map.registerIcon(s + "valveBottomRight"));
    }

    /**
     * Side is the side facing the player.
     * But we want to lookup the texture based on the players facing direction.
     * Master is not centered
     */
    public static IIcon getIcon(TileEntityMultiBlockMaster master, int x, int y, int z, int side) {

        if (master == null || !master.isStructureValid())
            return IconRegistry.getIcon("Wall");

        /* Make the master centralised */
        BlockPosition p = new BlockPosition(master.xCoord, master.yCoord, master.zCoord, master.getDirectionFacing());
        p.moveBackwards(1);
        BlockInfo info = new BlockInfo(p.x, p.y, p.z, x, y, z, ForgeDirection.getOrientation(side).getOpposite());

        if (side == master.getDirectionFacing().ordinal()) {
            /* front - only center is special */
            if (info.isMidY() && info.isCenter())
                return IconRegistry.getIcon("Squeezer");
        } else if (side == master.getDirectionFacing().getOpposite().ordinal()) {
            /* rear - all special */
            if (info.isTop() && info.isLeft())
                return IconRegistry.getIcon("ValveTopLeft");
            else if (info.isTop() && info.isCenter())
                return IconRegistry.getIcon("TopCenter");
            else if (info.isTop() && info.isRight())
                return IconRegistry.getIcon("ValveTopRight");
            else if (info.isBottom() && info.isLeft())
                return IconRegistry.getIcon("ValveBottomLeft");
            else if (info.isBottom() && info.isCenter())
                return IconRegistry.getIcon("BottomCenter");
            else if (info.isBottom() && info.isRight())
                return IconRegistry.getIcon("ValveBottomRight");
            else if (info.isMidY() && info.isLeft())
                return IconRegistry.getIcon("MiddleLeft");
            else if (info.isMidY() && info.isRight())
                return IconRegistry.getIcon("MiddleRight");
            else if (info.isMidY() && info.isCenter())
                return IconRegistry.getIcon("Center");
        }

        if (info.isTop() && info.isLeft())
            return IconRegistry.getIcon("TopLeft");
        else if (info.isTop() && info.isCenter())
            return IconRegistry.getIcon("TopCenter");
        else if (info.isTop() && info.isRight())
            return IconRegistry.getIcon("TopRight");
        else if (info.isBottom() && info.isLeft())
            return IconRegistry.getIcon("BottomLeft");
        else if (info.isBottom() && info.isCenter())
            return IconRegistry.getIcon("BottomCenter");
        else if (info.isBottom() && info.isRight())
            return IconRegistry.getIcon("BottomRight");
        else if (info.isMidY() && info.isLeft())
            return IconRegistry.getIcon("MiddleLeft");
        else if (info.isMidY() && info.isRight())
            return IconRegistry.getIcon("MiddleRight");
        else if (info.isMidY() && info.isCenter())
            return IconRegistry.getIcon("Center");

        return null;
    }

    private static class BlockInfo {
        boolean midX, midY, midZ;
        boolean top, bottom;
        boolean left, right;

        public boolean isMidX() { return midX; }
        public boolean isMidY() { return midY; }
        public boolean isMidZ() { return midZ; }
        public boolean isTop() { return top; }
        public boolean isBottom() { return bottom; }
        public boolean isLeft() { return left; }
        public boolean isRight() { return right; }
        public boolean isCenter() { return !left && !right; }


        private BlockInfo() {}
        public BlockInfo(int masterX, int masterY, int masterZ, int x, int y, int z, ForgeDirection facing) {
            midX = (masterX == x) ? true : false;
            midY = (masterY == y) ? true : false;
            midZ = (masterZ == z) ? true : false;

            top = (masterY + 1 == y) ? true : false;
            bottom = (masterY - 1 == y) ? true : false;

            /**
             * facing == what face are we looking at
             *
             * EAST  : left = -z, right = +z
             * WEST  : left = +z, right = -z
             * SOUTH : left = +x, right = -x
             * NORTH : left = -x, right = +x
             * UP    : ????
             * DOWN  : ???
             */

            if (facing == ForgeDirection.EAST) {
                left = (z < masterZ) ? true : false;
                right = (z > masterZ) ? true : false;
            } else if (facing == ForgeDirection.WEST) {
                left = (z > masterZ) ? true : false;
                right = (z < masterZ) ? true : false;
            } else if (facing == ForgeDirection.SOUTH) {
                left = (x > masterX) ? true : false;
                right = (x < masterX) ? true : false;
            } else if (facing == ForgeDirection.NORTH) {
                left = (x < masterX) ? true : false;
                right = (x > masterX) ? true : false;
            } else {
                left = false;
                right = false;
            }
        }
    }

}
