package ipsis.dyetopia.util;

import net.minecraftforge.common.util.ForgeDirection;

public class BlockLocation {
    private boolean midX, midY, midZ;
    boolean top, bottom, middle;
    boolean left, right;

    public enum MBLocation {
        TOPLEFT, TOPCENTER, TOPRIGHT, MIDDLELEFT, CENTER, MIDDLERIGHT, BOTTOMLEFT, BOTTOMCENTER, BOTTOMRIGHT
    }

    public boolean isMiddle() {
        return middle;
    }

    public boolean isTop() {
        return top;
    }

    public boolean isBottom() {
        return bottom;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isCenter() {
        return !left && !right;
    }

    public MBLocation getLocation() {
        MBLocation l = MBLocation.CENTER;
        if (isTop() && isLeft())
            l = MBLocation.TOPLEFT;
        else if (isTop() && isCenter())
            l = MBLocation.TOPCENTER;
        else if (isTop() && isRight())
            l = MBLocation.TOPRIGHT;
        else if (isBottom() && isLeft())
            l = MBLocation.BOTTOMLEFT;
        else if (isBottom() && isCenter())
            l = MBLocation.BOTTOMCENTER;
        else if (isBottom() && isRight())
            l = MBLocation.BOTTOMRIGHT;
        else if (isMiddle() && isLeft())
            l = MBLocation.MIDDLELEFT;
        else if (isMiddle() && isRight())
            l = MBLocation.MIDDLERIGHT;
        else if (isMiddle() && isCenter())
            l = MBLocation.CENTER;
        return l;
    }

    private BlockLocation() {
    }

    public BlockLocation(int masterX, int masterY, int masterZ, int x, int y, int z, ForgeDirection facing) {

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
