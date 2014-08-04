package com.ipsis.dyetopia.manager;

import com.ipsis.dyetopia.util.LogHelper;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TankManager {

    private static class TankConfig {

        public FluidTank tank;
        private boolean[] allowDrain;
        private boolean[] allowFill;
        private List<Integer> fluidWhitelist;

        public TankConfig(int capacity) {

            tank = new FluidTank(capacity);
            allowDrain = new boolean[] { true, true, true, true, true, true };
            allowFill = new boolean[] { true, true, true, true, true, true };
            fluidWhitelist = new ArrayList<Integer>();
        }

        public void setAllowDrain(ForgeDirection dir, boolean allow) {
            this.allowDrain[dir.ordinal()] = allow;
        }

        public boolean getAllowDrain(ForgeDirection dir) {
            return this.allowDrain[dir.ordinal()];
        }

        public void setAllowFill(ForgeDirection dir, boolean allow) {
            this.allowFill[dir.ordinal()] = allow;
        }

        public boolean getAllowFill(ForgeDirection dir) {
            return this.allowFill[dir.ordinal()];
        }

        public void addToWhiteList(Fluid f) {

            fluidWhitelist.add(f.getID());
        }

        public boolean isOnWhitelist(Fluid f) {

            if (fluidWhitelist.isEmpty())
                return true;

            return fluidWhitelist.contains(f.getID());
        }
    }

    private HashMap<String, TankConfig> tanks;

    public TankManager() {

        tanks = new HashMap<String, TankConfig>();
    }


    /**
     * By default you can fill/drain from any direction
     */
    public boolean registerTank(String name, int capacity) {

        if (tanks.get(name) != null)
            return false;

        return tanks.put(name, new TankConfig(capacity)) != null;
    }


    public void blockTankDrainAll(String name) {

        if (tanks.get(name) == null)
            return;

        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
            tanks.get(name).setAllowDrain(d, false);
    }

    public void blockTankFillAll(String name) {

        if (tanks.get(name) == null)
            return;

        for (ForgeDirection d : ForgeDirection.VALID_DIRECTIONS)
            tanks.get(name).setAllowFill(d, false);
    }

    public void allowTankDrain(String name, ForgeDirection from, boolean allow) {

        if (tanks.get(name) == null)
            return;

        tanks.get(name).setAllowDrain(from, allow);
    }

    public void allowTankFill(String name, ForgeDirection from, boolean allow) {

        if (tanks.get(name) == null)
            return;

        tanks.get(name).setAllowFill(from, allow);
    }

    /**
     * Wrapped IFluidHandler calls
     */
    public int fill(String name, ForgeDirection from, FluidStack resource, boolean doFill) {

        TankConfig cfg = tanks.get(name);
        if (cfg == null || resource == null)
            return 0;

        if (cfg.isOnWhitelist(resource.getFluid()) )
            return tanks.get(name).tank.fill(resource, doFill);
        else
            return 0;
    }

    public FluidStack drain(String name, ForgeDirection from, FluidStack resource, boolean doDrain) {

        FluidTank t = tanks.get(name).tank;
        if (t == null || resource == null || !resource.isFluidEqual(t.getFluid()))
            return null;

        return t.drain(resource.amount, doDrain);
    }

    public FluidStack drain(String name, ForgeDirection from, int maxDrain, boolean doDrain) {

        if (tanks.get(name) == null)
            return null;

        return tanks.get(name).tank.drain(maxDrain, doDrain);
    }

    public boolean canFill(String name, ForgeDirection from, Fluid fluid) {

        TankConfig cfg = tanks.get(name);
        if (cfg == null || fluid == null)
            return false;

        if (cfg.isOnWhitelist(fluid))
            return tanks.get(name).getAllowFill(from);
        else
            return false;
    }

    public boolean canDrain(String name, ForgeDirection from, Fluid fluid) {

        TankConfig cfg = tanks.get(name);
        if (tanks.get(name) == null || fluid == null)
            return false;

        if (cfg.isOnWhitelist(fluid))
            return tanks.get(name).getAllowDrain(from);
        else
            return false;
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {

        FluidTankInfo[] info = new FluidTankInfo[this.tanks.size()];

        int x = 0;
        for (TankConfig t : tanks.values()) {

            if (t.getAllowDrain(from) == true || t.getAllowFill(from) == true) {
                info[x] = t.tank.getInfo();
                LogHelper.info(t.tank.getInfo());
                x++;
            }
        }

        return info;
    }
}
