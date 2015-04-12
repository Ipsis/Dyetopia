package ipsis.dyetopia.gui.container;

import ipsis.dyetopia.gui.IGuiFluidSyncHandler;
import ipsis.dyetopia.network.message.MessageGuiFluidSync;
import ipsis.dyetopia.tileentity.TileEntityMixer;
import ipsis.dyetopia.util.TankType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMixer extends Container implements IGuiFluidSyncHandler {

    private TileEntityMixer mixer;

    public ContainerMixer(TileEntityMixer mixer, EntityPlayer player) {
        this.mixer = mixer;

         /* Player inventory */
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++)
                this.addSlotToContainer(new Slot(player.inventory, x + y * 9 + 9, 6 + x * 18, 95 + y * 18));
        }

		 /* Player hotbar */
        for (int x = 0; x < 9; x++)
            this.addSlotToContainer(new Slot(player.inventory, x, 6 + x * 18, 153));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {

        return this.mixer.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {

        return null;
    }

    /**
     * Updating
     */

    @Override
    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);

        this.mixer.getTankMgr().initGuiTracking(icrafting, this, TankType.RED.getName());
        this.mixer.getTankMgr().initGuiTracking(icrafting, this, TankType.YELLOW.getName());
        this.mixer.getTankMgr().initGuiTracking(icrafting, this, TankType.BLUE.getName());
        this.mixer.getTankMgr().initGuiTracking(icrafting, this, TankType.WHITE.getName());
        this.mixer.getTankMgr().initGuiTracking(icrafting, this, TankType.PURE.getName());

        this.mixer.getEnergyMgr().initGuiTracking(icrafting, this);

        this.mixer.getFactoryMgr().initGuiTracking(icrafting, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);

        /* The id will determine if anything happens in each manager */
        this.mixer.getEnergyMgr().processGuiTracking(id, data);
        this.mixer.getFactoryMgr().processGuiTracking(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        this.mixer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.RED.getName());
        this.mixer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.YELLOW.getName());
        this.mixer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.BLUE.getName());
        this.mixer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.WHITE.getName());
        this.mixer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.PURE.getName());

        this.mixer.getEnergyMgr().updateGuiTracking(this.crafters, this);

        this.mixer.getFactoryMgr().updateGuiTracking(this.crafters, this);
    }

    /**
     * IGuiFluidSyncHandler
     */
    @Override
    public void handleGuiFluidSync(MessageGuiFluidSync message) {
        this.mixer.getTankMgr().processGuiTracking(message.tankId, message.fluidStack);
    }
 }
