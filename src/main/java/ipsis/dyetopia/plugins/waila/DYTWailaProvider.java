package ipsis.dyetopia.plugins.waila;


import cofh.api.energy.EnergyStorage;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.manager.TankManager;
import ipsis.dyetopia.tileentity.*;
import ipsis.dyetopia.util.TankType;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.FluidTank;

import java.util.List;

/**
 * Power is already handled by Waila with IEnergyHandler support
 */
public class DYTWailaProvider implements IWailaDataProvider{

    /* Waila API formatting values from waila/api/SpecialChars.java */
    private static String WailaStyle     = "\u00A4";
    private static String TAB         = WailaStyle + WailaStyle +"a";
    private static String ALIGNRIGHT  = WailaStyle + WailaStyle +"b";
    private static String ALIGNCENTER = WailaStyle + WailaStyle +"c";


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

        /**
         * Waila will autodisplay the RF via an IEnergyHandler hooks
         * Anything else (tanks for now) only shows on sneak
         */
        if (!accessor.getPlayer().isSneaking())
            return currenttip;

        if (accessor.getTileEntity() instanceof TileEntitySqueezer) {
            TileEntitySqueezer te = (TileEntitySqueezer)accessor.getTileEntity();
            currenttip = handleTileEntitySqueezer((TileEntitySqueezer)accessor.getTileEntity(), currenttip);
        } else if (accessor.getTileEntity() instanceof TileEntityMixer) {
            TileEntityMixer te = (TileEntityMixer)accessor.getTileEntity();
            currenttip = handleTileEntityMixer((TileEntityMixer) accessor.getTileEntity(), currenttip);
        } else if (accessor.getTileEntity() instanceof TileEntityPainter) {
            TileEntityPainter te = (TileEntityPainter)accessor.getTileEntity();
            currenttip = handleTileEntityPainter((TileEntityPainter) accessor.getTileEntity(), currenttip);
        }  else if (accessor.getTileEntity() instanceof TileEntityStamper) {
            TileEntityStamper te = (TileEntityStamper)accessor.getTileEntity();
            currenttip = handleTileEntityStamper((TileEntityStamper) accessor.getTileEntity(), currenttip);
        }  else if (accessor.getTileEntity() instanceof TileEntityFiller) {
            TileEntityFiller te = (TileEntityFiller)accessor.getTileEntity();
            currenttip = handleTileEntityFiller((TileEntityFiller) accessor.getTileEntity(), currenttip);
        } else if (accessor.getTileEntity() instanceof TileEntityMultiBlockBase) {

            TileEntityMultiBlockBase base = (TileEntityMultiBlockBase)accessor.getTileEntity();
            if (base.hasMaster() && accessor.getWorld() != null) {
                TileEntity te = accessor.getWorld().getTileEntity(base.getMasterX(), base.getMasterY(), base.getMasterZ());
                if (te instanceof TileEntitySqueezer)
                    currenttip = handleTileEntitySqueezer((TileEntitySqueezer) te, currenttip);
                else if (te instanceof TileEntityMixer)
                    currenttip = handleTileEntityMixer((TileEntityMixer) te, currenttip);
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
        registrar.registerBodyProvider(new DYTWailaProvider(), TileEntityMixer.class);
        registrar.registerBodyProvider(new DYTWailaProvider(), TileEntityPainter.class);
        registrar.registerBodyProvider(new DYTWailaProvider(), TileEntityStamper.class);
        registrar.registerBodyProvider(new DYTWailaProvider(), TileEntityFiller.class);

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

        displayTankManager(te.getTankMgr(), currenttip, TankType.RED, TankType.YELLOW, TankType.BLUE, TankType.WHITE);
        return currenttip;
    }

    private List<String> handleTileEntityMixer(TileEntityMixer te, List<String> currenttip) {

        if (!te.isStructureValid())
            return currenttip;

        displayTankManager(te.getTankMgr(), currenttip, TankType.RED, TankType.YELLOW, TankType.BLUE, TankType.WHITE, TankType.PURE);
        return currenttip;
    }

    private List<String> handleTileEntityPainter(TileEntityPainter te, List<String> currenttip) {

        displayTankManager(te.getTankMgr(), currenttip, TankType.PURE);
        return currenttip;
    }

    private List<String> handleTileEntityFiller(TileEntityFiller te, List<String> currenttip) {

        displayTankManager(te.getTankMgr(), currenttip, TankType.PURE);
        return currenttip;
    }

    private List<String> handleTileEntityStamper(TileEntityStamper te, List<String> currenttip) {

        displayTankManager(te.getTankMgr(), currenttip, TankType.PURE);
        return currenttip;
    }

    private List<String> displayTankManager(TankManager tm, List<String> currenttip, TankType... names) {

        if (tm == null || names.length == 0)
            return currenttip;

        currenttip.add(EnumChatFormatting.YELLOW + "Tanks");
        for (TankType name : names) {

            FluidTank tank = tm.getTank(name.getName());
            if (tank == null)
                continue;

            currenttip.add(EnumChatFormatting.ITALIC + String.format("%s : %s%d/%d%smB",
                    StatCollector.translateToLocal("hud.msg.dyetopia:tank." + name.getName()),
                    TAB + ALIGNRIGHT,
                    tank.getFluidAmount(),
                    tank.getCapacity(),
                    TAB + ALIGNRIGHT));
        }

        return currenttip;
    }
}
