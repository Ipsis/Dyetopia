package ipsis.dyetopia.util.multiblock;

import cofh.lib.util.position.BlockPosition;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.reference.Names;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityMixer;
import ipsis.dyetopia.tileentity.TileEntityMultiBlockMaster;
import ipsis.dyetopia.tileentity.TileEntitySqueezer;
import ipsis.dyetopia.util.IconRegistry;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class MultiBlockTextures {

    public static void registerIcons(TextureMap map) {

        String s = Textures.RESOURCE_PREFIX + "machines/multiblock/";

        IconRegistry.addIcon("Wall", map.registerIcon(s + "wall"));

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
        IconRegistry.addIcon("ValveCenter", map.registerIcon(s + "valveCenter"));
        IconRegistry.addIcon("ValveBottomLeft", map.registerIcon(s + "valveBottomLeft"));
        IconRegistry.addIcon("ValveBottomRight", map.registerIcon(s + "valveBottomRight"));

        /* Formed */
        IconRegistry.addIcon("Mixer", map.registerIcon(s + "mixer"));
        IconRegistry.addIcon("Squeezer", map.registerIcon(s + "squeezer"));

        /* Unformed */
        IconRegistry.addIcon("MixerUnformed", map.registerIcon(s + "mixer"));
        IconRegistry.addIcon("SqueezerUnformed", map.registerIcon(s + "squeezer"));
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
            if (info.isMiddle() && info.isCenter()) {
                if (master instanceof TileEntityMixer)
                    return IconRegistry.getIcon("Mixer");
                else if (master instanceof TileEntitySqueezer)
                    return IconRegistry.getIcon("Squeezer");
            }
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
            else if (info.isMiddle() && info.isLeft())
                return IconRegistry.getIcon("MiddleLeft");
            else if (info.isMiddle() && info.isRight())
                return IconRegistry.getIcon("MiddleRight");
            else if (info.isMiddle() && info.isCenter()) {
                if (master instanceof TileEntityMixer)
                    return IconRegistry.getIcon("ValveCenter");
                else if (master instanceof TileEntitySqueezer)
                    return IconRegistry.getIcon("Center");
            }
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
        else if (info.isMiddle() && info.isLeft())
            return IconRegistry.getIcon("MiddleLeft");
        else if (info.isMiddle() && info.isRight())
            return IconRegistry.getIcon("MiddleRight");
        else if (info.isMiddle() && info.isCenter())
            return IconRegistry.getIcon("Center");

        return null;
    }

    private static class BlockInfo {
        private boolean midX, midY, midZ;
        boolean top, bottom, middle;
        boolean left, right;

        public boolean isMiddle() { return middle; }
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

            if (facing == ForgeDirection.UP || facing == ForgeDirection.DOWN) {
                top = (masterZ - 1 == z) ? true : false;
                bottom = (masterZ + 1 == z) ? true : false;
                middle = midZ;
            } else {
                top = (masterY + 1 == y) ? true : false;
                bottom = (masterY - 1 == y) ? true : false;
                middle = midY;
            }

            /**
             * facing == what face are we looking at
             *
             * EAST  : left = -z, right = +z, top/bottom = y
             * WEST  : left = +z, right = -z, top/bottom = y
             * SOUTH : left = +x, right = -x, top/bottom = y
             * NORTH : left = -x, right = +x, top/bottom = y
             * UP    : left = -x, right = +x, top/bottom = z
             * DOWN  : left = -x, right = +x, top/bottom = z
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
            } else if (facing == ForgeDirection.NORTH || facing == ForgeDirection.UP || facing == ForgeDirection.DOWN) {
                left = (x < masterX) ? true : false;
                right = (x > masterX) ? true : false;
            }
        }
    }

}
