package com.ipsis.dyetopia.tileentity;

import com.ipsis.dyetopia.util.LogHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityValve extends TileEntityMultiBlockBase implements IFluidHandler{

    /* 0 - none, 1 - red, 2 - yellow, 3 - blue, 4 - white, 5 - pure */
    private int color;

    public TileEntityValve() {

        this.color = 0;
    }

    public int getColor() { return this.color; }

    private TileEntityMultiBlockMaster getMasterTE() {

        if (this.hasMaster) {
            TileEntity te = this.worldObj.getTileEntity(this.masterX, this.masterY, this.masterZ);
            if (te instanceof TileEntityMultiBlockMaster)
                return (TileEntityMultiBlockMaster)te;
        }

        return null;
    }

    /****************
     * IFluidHandler
     * This just passes through to the master block (if it exists) and it is a
     * FluidHandler
     */

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

        TileEntityMultiBlockMaster m = this.getMasterTE();
        if (m != null && m instanceof IFluidHandler)
            return ((IFluidHandler)m).fill(from, resource, doFill);

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

        TileEntityMultiBlockMaster m = this.getMasterTE();
        if (m != null && m instanceof IFluidHandler)
            return ((IFluidHandler)m).drain(from, resource, doDrain);

        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        TileEntityMultiBlockMaster m = this.getMasterTE();
        if (m != null && m instanceof IFluidHandler)
            return ((IFluidHandler)m).drain(from, maxDrain, doDrain);

        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {

        TileEntityMultiBlockMaster m = this.getMasterTE();
        if (m != null && m instanceof IFluidHandler)
            return ((IFluidHandler)m).canFill(from, fluid);

        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {

        TileEntityMultiBlockMaster m = this.getMasterTE();
        if (m != null && m instanceof IFluidHandler)
            return ((IFluidHandler)m).canDrain(from, fluid);

        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        TileEntityMultiBlockMaster m = this.getMasterTE();
        if (m != null && m instanceof IFluidHandler) {
            FluidTankInfo[] t = ((IFluidHandler) m).getTankInfo(from);
            LogHelper.info(t.length);
            for (FluidTankInfo tank : t) {
                LogHelper.info(tank);

            }
            return t;
        }

        return null;
    }
}
