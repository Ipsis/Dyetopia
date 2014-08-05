package com.ipsis.dyetopia.tileentity;

import com.ipsis.dyetopia.util.TankType;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

/**
 * Wrapper interface around IFluidHandler to cope with the TankType parameter
 */
public interface ITankHandler {

    int fill(TankType tank, ForgeDirection from, FluidStack resource, boolean doFill);
    FluidStack drain(TankType tank, ForgeDirection from, FluidStack resource, boolean doDrain);
    FluidStack drain(TankType tank, ForgeDirection from, int maxDrain, boolean doDrain);
    boolean canFill(TankType tank, ForgeDirection from, Fluid fluid);
    boolean canDrain(TankType tank, ForgeDirection from, Fluid fluid);
    FluidTankInfo[] getTankInfo(ForgeDirection from);

}
