package com.ipsis.dyetopia.plugins.waila;


import com.ipsis.dyetopia.tileentity.*;
import com.ipsis.dyetopia.util.LogHelper;
import com.ipsis.dyetopia.util.TankType;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.List;

public class DYTWailaProvider implements IWailaDataProvider{
    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {


        if (accessor.getTileEntity() instanceof TileEntitySqueezer) {
            currenttip = handleTileEntitySqueezer((TileEntitySqueezer)accessor.getTileEntity(), currenttip);
        } else if (accessor.getTileEntity() instanceof TileEntityMultiBlockBase) {

            TileEntityMultiBlockBase base = (TileEntityMultiBlockBase)accessor.getTileEntity();
            if (base.hasMaster() && accessor.getWorld() != null) {
                TileEntity te = accessor.getWorld().getTileEntity(base.getMasterX(), base.getMasterY(), base.getMasterZ());
                if (te instanceof TileEntitySqueezer)
                    currenttip = handleTileEntitySqueezer((TileEntitySqueezer)te, currenttip);
            }
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    public static void callbackRegister(IWailaRegistrar registrar){

        registrar.registerBodyProvider(new DYTWailaProvider(), TileEntitySqueezer.class);
        registrar.registerBodyProvider(new DYTWailaProvider(), TileEntityValve.class);
        registrar.registerBodyProvider(new DYTWailaProvider(), TileEntityCasing.class);
        registrar.registerBodyProvider(new DYTWailaProvider(), TileEntityController.class);
    }

    /**
     * Tile Info Providers
     */
    private List<String> handleTileEntitySqueezer(TileEntitySqueezer te, List<String> currenttip) {

        currenttip.add(String.format("Energy : %d / %d",
                te.getEnergyStored(ForgeDirection.DOWN),
                te.getMaxEnergyStored(ForgeDirection.DOWN)));

        /* TODO make this walk the generic TankInfo return info */
        currenttip.add(String.format("Red : %d / %d",
                te.getTankMgr().getTank(TankType.RED.getName()).getFluidAmount(),
                te.getTankMgr().getTank(TankType.RED.getName()).getCapacity()));
        currenttip.add(String.format("Yellow : %d / %d",
                te.getTankMgr().getTank(TankType.YELLOW.getName()).getFluidAmount(),
                te.getTankMgr().getTank(TankType.YELLOW.getName()).getCapacity()));
        currenttip.add(String.format("Blue : %d / %d",
                te.getTankMgr().getTank(TankType.BLUE.getName()).getFluidAmount(),
                te.getTankMgr().getTank(TankType.BLUE.getName()).getCapacity()));
        currenttip.add(String.format("White : %d / %d",
                te.getTankMgr().getTank(TankType.WHITE.getName()).getFluidAmount(),
                te.getTankMgr().getTank(TankType.WHITE.getName()).getCapacity()));

        return currenttip;

    }
}
