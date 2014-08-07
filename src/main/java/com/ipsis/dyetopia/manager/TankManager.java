package com.ipsis.dyetopia.manager;

import com.ipsis.dyetopia.gui.container.ProgressBar;
import com.ipsis.dyetopia.network.PacketHandler;
import com.ipsis.dyetopia.network.message.MessageGuiFixedProgressBar;
import com.ipsis.dyetopia.util.LogHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.*;

public class TankManager {

    private static class TankConfig {

        public FluidTank tank;
        private boolean[] allowDrain;
        private boolean[] allowFill;
        private List<Integer> fluidWhitelist;
        private int id;

        public TankConfig(int id, int capacity) {

            tank = new FluidTank(capacity);
            allowDrain = new boolean[] { true, true, true, true, true, true };
            allowFill = new boolean[] { true, true, true, true, true, true };
            fluidWhitelist = new ArrayList<Integer>();
            this.id = id;
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
    private HashMap<String, FluidStack> guiTanks;
    private ArrayList<TankConfig> ids;
    private int count;

    private static int EMPTY_FLUID_ID = -1;
    private static int EMPTY_FLUID_AMOUNT = 0;

    public TankManager() {

        tanks = new HashMap<String, TankConfig>();
        ids = new ArrayList<TankConfig>();
        guiTanks = new HashMap<String, FluidStack>();
        count = 0;
    }


    /**
     * By default you can fill/drain from any direction
     */
    public boolean registerTank(String name, int capacity) {

        if (tanks.get(name) != null)
            return false;


        TankConfig cfg = new TankConfig(this.count, capacity);
        tanks.put(name, cfg);
        ids.add(this.count, cfg);
        this.count++;
        guiTanks.put(name, null);
        return true;
    }

    private TankConfig getTankFromId(int id) {

        if (id < tanks.size())
            return ids.get(id);
        return null;
    }

    public void addToWhitelist(String name, Fluid f) {

        if (tanks.get(name) == null)
            return;

        tanks.get(name).addToWhiteList(f);
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
     * Dont modify the tank!
     */
    public FluidTank getTank(String name) {

        if (tanks.get(name) == null)
            return null;

        return tanks.get(name).tank;
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

    /**
     * NBT
     */
    public void readFromNBT(NBTTagCompound nbttagcompound) {

        NBTTagList nbttaglist = nbttagcompound.getTagList("Tanks", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); i++) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);

            String name = nbttagcompound1.getString("Name");
            if (tanks.get(name) != null) {
                FluidTank f = tanks.get(name).tank;
                if (f != null)
                    f.readFromNBT(nbttagcompound1);
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {

        NBTTagList nbttaglist = new NBTTagList();

        Iterator it = tanks.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry)it.next();

            NBTTagCompound nbttagcompound1 = new NBTTagCompound();
            nbttagcompound1.setString("Name", (String)pairs.getKey());
            ((TankConfig)pairs.getValue()).tank.writeToNBT(nbttagcompound1);
            nbttaglist.appendTag(nbttagcompound1);
        }

        nbttagcompound.setTag("Tanks", nbttaglist);
    }

    /**
     * GUI updating
     * Based off Railcraft handling of the TankManager
     */
    public void initGuiTracking(ICrafting icrafting, Container container, String name) {

        if (tanks.get(name) == null)
            return;

        TankConfig tankCfg = tanks.get(name);
        if (tankCfg.tank.getFluid() == null)
            guiTanks.put(name, null);
        else
            guiTanks.put(name, tankCfg.tank.getFluid().copy());

        if (tankCfg.tank.getFluid() == null) {
            sendTankFluidId(icrafting, container, tankCfg.id, EMPTY_FLUID_ID);
            sendTankFluidAmount((EntityPlayerMP)icrafting, container, tankCfg.id, EMPTY_FLUID_AMOUNT);
        } else {
            sendTankFluidId(icrafting, container, tankCfg.id, tankCfg.tank.getFluid().fluidID);
            sendTankFluidAmount((EntityPlayerMP)icrafting, container, tankCfg.id, tankCfg.tank.getFluidAmount());
        }
    }

    private void sendTankFluidId(ICrafting icrafting, Container container, int tankId, int fluidId) {

        int progId = ProgressBar.createIDFluidId(tankId);
        icrafting.sendProgressBarUpdate(container, progId, fluidId);
    }

    private void sendTankFluidAmount(EntityPlayerMP player, Container container, int tankId, int amount) {

        int progId = ProgressBar.createIDFluidAmount(tankId);
        PacketHandler.INSTANCE.sendTo(
                new MessageGuiFixedProgressBar(container.windowId, progId, amount), player);
    }

    public void updateGuiTracking(List crafters, Container container, String name) {

        if (tanks.get(name) == null || container == null || crafters == null || crafters.isEmpty())
            return;

        TankConfig tankCfg = tanks.get(name);
        FluidStack oldFluid = guiTanks.get(name);

        for (Object crafter : crafters) {
            ICrafting icrafting = (ICrafting)crafter;
            EntityPlayerMP player = (EntityPlayerMP)crafter;

            if (oldFluid == null && tankCfg.tank.getFluid() == null) {
                //LogHelper.info(name + " empty->empty");
                /* was empty and still is */
                return;
            }

            if (oldFluid == null && tankCfg.tank.getFluid() != null) {
                /* was empty, now isn't */
                //LogHelper.info(name + " empty->" + tankCfg.tank.getFluid());
                sendTankFluidId(icrafting, container, tankCfg.id, tankCfg.tank.getFluid().fluidID);
                sendTankFluidAmount(player, container, tankCfg.id, tankCfg.tank.getFluidAmount());

            } else if (oldFluid != null && tankCfg.tank.getFluid() == null) {
                /* wasn't empty now is */
                //LogHelper.info(name + " " + oldFluid + "->empty");
                sendTankFluidId(icrafting, container, tankCfg.id, EMPTY_FLUID_ID);
                sendTankFluidAmount(player, container, tankCfg.id, EMPTY_FLUID_AMOUNT);
            } else {

                if (oldFluid.fluidID != tankCfg.tank.getFluid().fluidID) {
                    /* fluid id changed */
                    //LogHelper.info(name + " " + oldFluid.fluidID + "->" + tankCfg.tank.getFluid().fluidID);
                    sendTankFluidId(icrafting, container, tankCfg.id, tankCfg.tank.getFluid().fluidID);
                }

                if (oldFluid.amount != tankCfg.tank.getFluidAmount()) {
                    /* fluid amount changed */
                    //LogHelper.info(name + " " + oldFluid.amount + "->" + tankCfg.tank.getFluid().amount);
                    sendTankFluidAmount(player, container, tankCfg.id, tankCfg.tank.getFluidAmount());
                }
            }
        }

        /* Update the values */
        guiTanks.put(name, tankCfg.tank.getFluid() == null ? null : tankCfg.tank.getFluid().copy());
    }

    public void processGuiTracking(int id, int data) {

        if (ProgressBar.getIDType(id) == ProgressBar.ID_TYPE.ID_FLUID_ID) {

            int tankId = ProgressBar.getIDValue(id);
            TankConfig cfg = getTankFromId(tankId);
            if (cfg != null) {
                if (cfg.tank.getFluid() == null)
                    cfg.tank.setFluid(new FluidStack(data, EMPTY_FLUID_AMOUNT));
                else
                    cfg.tank.getFluid().fluidID = data;
            }

        } else if (ProgressBar.getIDType(id) == ProgressBar.ID_TYPE.ID_FLUID_AMOUNT) {

            int tankId = ProgressBar.getIDValue(id);
            TankConfig cfg = getTankFromId(tankId);
            if (cfg != null) {
                if (cfg.tank.getFluid() == null)
                    cfg.tank.setFluid(new FluidStack(EMPTY_FLUID_ID, data));
                else
                    cfg.tank.getFluid().amount = data;
            }
        }

    }
}
