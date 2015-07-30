package ipsis.dyetopia.gui.container;

import cofh.lib.gui.slot.SlotAcceptValid;
import cofh.lib.util.helpers.InventoryHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ipsis.dyetopia.gui.IGuiFluidSyncHandler;
import ipsis.dyetopia.network.message.MessageGuiFluidSync;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.tileentity.TileEntityFiller;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFiller extends Container implements IGuiFluidSyncHandler {

    private TileEntityFiller filler;

    public ContainerFiller(TileEntityFiller filler, EntityPlayer player) {

        this.filler = filler;

        this.addSlotToContainer(new SlotAcceptValid(this.filler, this.filler.INPUT_SLOT, GuiLayout.Filler.INPUT_SLOT_X, GuiLayout.Filler.INPUT_SLOT_Y));

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

        return this.filler.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotNum) {

        Slot slot = getSlot(slotNum);
        if (slot == null || !slot.getHasStack())
            return null;

        ItemStack stack = slot.getStack();
        ItemStack result = stack.copy();

        if (slotNum < 1) {
            /* machine to player */
            if (!InventoryHelper.mergeItemStack(this.inventorySlots, stack, 1, 1 + 27 + 9, false))
                return null;
        } else {
            /* player to machine */
            if (!InventoryHelper.mergeItemStack(this.inventorySlots, stack, 0, 1, false))
                return null;
        }

        if (stack.stackSize == 0)
            slot.putStack(null);
        else
            slot.onSlotChanged();

        slot.onPickupFromSlot(player, stack);

        return result;
    }

    /**
     * Updating
     */

    @Override
    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);

        this.filler.getTankMgr().initGuiTracking(icrafting, this, TankType.PURE.getName());
        this.filler.getEnergyMgr().initGuiTracking(icrafting, this);
        this.filler.getFactoryMgr().initGuiTracking(icrafting, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);

        /* The id will determine if anything happens in each manager */
        this.filler.getEnergyMgr().processGuiTracking(id, data);
        this.filler.getFactoryMgr().processGuiTracking(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        this.filler.getTankMgr().updateGuiTracking(this.crafters, this, TankType.PURE.getName());
        this.filler.getEnergyMgr().updateGuiTracking(this.crafters, this);
        this.filler.getFactoryMgr().updateGuiTracking(this.crafters, this);
    }

    /**
     * IGuiFluidSyncHandler
     */
    @Override
    public void handleGuiFluidSync(MessageGuiFluidSync message) {
        this.filler.getTankMgr().processGuiTracking(message.tankId, message.fluidStack);
    }
}
