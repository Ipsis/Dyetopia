package com.ipsis.dyetopia.tileentity;

import cofh.util.position.BlockPosition;
import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.manager.TankManager;
import com.ipsis.dyetopia.util.TankType;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileEntitySqueezer extends TileEntityMultiBlockMaster implements ITankHandler {

    private TankManager tankMgr;
    private static final int TANK_CAPACITY = 40000;


    public TileEntitySqueezer() {
        super();
        this.setMaster(this);

        setupTanks();

        this.tankMgr.fill(TankType.RED.getName(), ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeRed, 40000), true);
        this.tankMgr.fill(TankType.YELLOW.getName(), ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeYellow, 40000), true);
        this.tankMgr.fill(TankType.BLUE.getName(), ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeBlue, 40000), true);
        this.tankMgr.fill(TankType.WHITE.getName(), ForgeDirection.DOWN, new FluidStack(DYTFluids.fluidDyeWhite, 40000), true);
    }

    private void setValveColor(BlockPosition p, TileEntityValve.Color color) {

        TileEntity te = this.worldObj.getTileEntity(p.x, p.y, p.z);
        if (te instanceof TileEntityValve) {
            ((TileEntityValve)te).setColor(color);
            this.worldObj.markBlockForUpdate(p.x, p.y, p.z);
        }
    }

    @Override
    public void onStructureValidChanged(boolean isNowValid) {

        /* Assign the valve blocks */
        BlockPosition o = new BlockPosition(
                this.xCoord, this.yCoord, this.zCoord,
                this.getPatternOrientation());
        BlockPosition p;

        /* Red tank */
        p = o.copy();
        p.moveUp(1);
        p.moveRight(1);
        setValveColor(p, isNowValid ? TileEntityValve.Color.RED : TileEntityValve.Color.NONE);

        /* Yellow tank */
        p = o.copy();
        p.moveDown(1);
        p.moveRight(1);
        setValveColor(p, isNowValid ? TileEntityValve.Color.YELLOW : TileEntityValve.Color.NONE);

        /* Blue tank */
        p = o.copy();
        p.moveUp(1);
        p.moveRight(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.BLUE : TileEntityValve.Color.NONE);

        /* White tank */
        p = o.copy();
        p.moveDown(1);
        p.moveRight(1);
        p.moveForwards(2);
        setValveColor(p, isNowValid ? TileEntityValve.Color.WHITE : TileEntityValve.Color.NONE);
    }

    private void setupTanks() {

        this.tankMgr = new TankManager();
        this.tankMgr.registerTank(TankType.RED.getName(), TANK_CAPACITY);
        this.tankMgr.registerTank(TankType.YELLOW.getName(), TANK_CAPACITY);
        this.tankMgr.registerTank(TankType.BLUE.getName(), TANK_CAPACITY);
        this.tankMgr.registerTank(TankType.WHITE.getName(), TANK_CAPACITY);
    }

    /***************
     * ITankHandler
     */

    public int fill(TankType tank, ForgeDirection from, FluidStack resource, boolean doFill) {

        return 0;
    }

    public FluidStack drain(TankType tank, ForgeDirection from, FluidStack resource, boolean doDrain) {

        return this.tankMgr.drain(tank.getName(), from, resource, doDrain);
    }

    public FluidStack drain(TankType tank, ForgeDirection from, int maxDrain, boolean doDrain) {

        return this.tankMgr.drain(tank.getName(), from, maxDrain, doDrain);
    }

    public boolean canFill(TankType tank, ForgeDirection from, Fluid fluid) {
        return false;
    }

    public boolean canDrain(TankType tank, ForgeDirection from, Fluid fluid) {

        return this.tankMgr.canDrain(tank.getName(), from, fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        return this.tankMgr.getTankInfo(from);
    }
}
