package com.ipsis.dyetopia.tileentity;

import com.ipsis.dyetopia.fluid.DYTFluids;
import com.ipsis.dyetopia.manager.TankManager;
import com.ipsis.dyetopia.util.LogHelper;
import com.ipsis.dyetopia.util.TankType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TileEntityMachinePureDye extends TileEntityMachine implements IFluidHandler {

    private TankManager tankMgr;

    public TileEntityMachinePureDye(int capacity) {

        super();
        setupTanks(capacity);
    }

    private void setupTanks(int capacity) {

        this.tankMgr = new TankManager();
        this.tankMgr.registerTank(TankType.PURE.getName(), capacity);
        this.tankMgr.addToWhitelist(TankType.PURE.getName(), DYTFluids.fluidDyePure);
        this.tankMgr.blockTankDrainAll(TankType.PURE.getName());

        LogHelper.info(this.tankMgr);
    }

    /**
     * ************
     * IFluidHandler
     */

    public TankManager getTankMgr() {
        return this.tankMgr;
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {

        return this.tankMgr.fill(TankType.PURE.getName(), from, resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {

        return this.tankMgr.drain(TankType.PURE.getName(), from, resource, doDrain);
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {

        return this.tankMgr.drain(TankType.PURE.getName(), from, maxDrain, doDrain);
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {

        return this.tankMgr.canFill(TankType.PURE.getName(), from, fluid);
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {

        return this.tankMgr.canDrain(TankType.PURE.getName(), from, fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        return this.tankMgr.getTankInfo(from);
    }

    /**
     * NBT
     */
    @Override
    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);

        this.tankMgr.writeToNBT(nbttagcompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);

        this.tankMgr.readFromNBT(nbttagcompound);
    }


}
