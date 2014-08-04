package com.ipsis.dyetopia.tileentity;

import cofh.util.BlockHelper;
import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.manager.TankManager;
import com.ipsis.dyetopia.util.LogHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntitySqueezer extends TileEntityMultiBlockMaster implements IFluidHandler {

    private TankManager tankMgr;
    private static final String RED_TANK = "redTank";
    private static final String YELLOW_TANK = "yellowTank";
    private static final String BLUE_TANK = "blueTank";
    private static final String WHITE_TANK = "whiteTank";
    private static final int TANK_CAPACITY = 40000;


    public TileEntitySqueezer() {
        super();
        this.setMaster(this);

        setupTanks();

        this.tankMgr.fill(RED_TANK, ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeRed, 40000), true);
        this.tankMgr.fill(YELLOW_TANK, ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeYellow, 40000), true);
        this.tankMgr.fill(BLUE_TANK, ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeBlue, 40000), true);
        this.tankMgr.fill(WHITE_TANK, ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeWhite, 40000), true);
    }

    /***************
     * IFluidHandler
     */

    private void setupTanks() {

        this.tankMgr = new TankManager();
        this.tankMgr.registerTank(RED_TANK, TANK_CAPACITY);
        this.tankMgr.registerTank(YELLOW_TANK, TANK_CAPACITY);
        this.tankMgr.registerTank(BLUE_TANK, TANK_CAPACITY);
        this.tankMgr.registerTank(WHITE_TANK, TANK_CAPACITY);

        setupFluidDirections();
    }
    private void setupFluidDirections() {

        //this.tankMgr.blockTankDrainAll(RED_TANK);
        //this.tankMgr.blockTankFillAll(RED_TANK);

        /* All tanks can only be drained from the right side as you face the front */
        ForgeDirection drainSide = this.getDirectionFacing().getOpposite();
        drainSide = ForgeDirection.getOrientation(BlockHelper.getRightSide(drainSide.ordinal()));
        LogHelper.info("setupFluidDirections " + drainSide);

        //this.tankMgr.allowTankDrain(RED_TANK, drainSide, true);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

        return this.tankMgr.drain(RED_TANK, from, resource, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        return this.tankMgr.drain(RED_TANK, from, maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {

        return this.tankMgr.canDrain(RED_TANK, from, fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        return this.tankMgr.getTankInfo(from);
    }
}
