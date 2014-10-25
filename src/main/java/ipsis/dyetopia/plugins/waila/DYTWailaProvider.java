package ipsis.dyetopia.plugins.waila;


import cofh.api.energy.EnergyStorage;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.tileentity.*;
import ipsis.dyetopia.util.TankType;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;

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

            TileEntitySqueezer te = (TileEntitySqueezer)accessor.getTileEntity();
            currenttip = handleTileEntitySqueezer((TileEntitySqueezer)accessor.getTileEntity(), currenttip);

        } else if (accessor.getTileEntity() instanceof TileEntityMultiBlockBase) {

            TileEntityMultiBlockBase base = (TileEntityMultiBlockBase)accessor.getTileEntity();
            if (base.hasMaster() && accessor.getWorld() != null) {
                TileEntity te = accessor.getWorld().getTileEntity(base.getMasterX(), base.getMasterY(), base.getMasterZ());
                if (te instanceof TileEntitySqueezer)
                    currenttip = handleTileEntitySqueezer((TileEntitySqueezer) te, currenttip);
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

        if (!te.isStructureValid())
            return currenttip;

        displayEnergy(currenttip, te.getEnergyMgr().getEnergyStorage());

        displayNamedTankInfo(currenttip, "Red", te.getTankMgr().getTank(TankType.RED.getName()));
        displayNamedTankInfo(currenttip, "Yellow", te.getTankMgr().getTank(TankType.YELLOW.getName()));
        displayNamedTankInfo(currenttip, "Blue", te.getTankMgr().getTank(TankType.BLUE.getName()));
        displayNamedTankInfo(currenttip, "White", te.getTankMgr().getTank(TankType.WHITE.getName()));

        return currenttip;
    }

    private void displayEnergy(List<String> currenttip, EnergyStorage storage) {

        currenttip.add(String.format("Energy : %d / %d RF",
                storage.getEnergyStored(),
                storage.getMaxEnergyStored()));
    }

    /**
     * Display name, not the fluid name
     */
    private void displayNamedTankInfo(List<String> currenttip, String name, FluidTank tank) {
        if (tank != null)
            currenttip.add(String.format("%s : %d / %d mB", name, tank.getFluidAmount(), tank.getCapacity()));
    }

    /**
     * Display the fluid name if the tank is not empty
     */
    private void displayTankInfo(List<String> currenttip, FluidTank tank) {

        if (tank != null) {
            if (tank.getFluid() != null)
                currenttip.add(String.format("%s : %d / %d mB",
                        StringHelper.getFluidName(tank.getFluid()),
                        tank.getFluid().fluidID, tank.getFluidAmount(), tank.getCapacity()));
        }
    }
}
