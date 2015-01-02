package ipsis.dyetopia.gui.container;

import cofh.lib.gui.slot.SlotAcceptValid;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.tileentity.TileEntitySqueezer;
import ipsis.dyetopia.util.TankType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSqueezer extends Container {

    private TileEntitySqueezer squeezer;

    public ContainerSqueezer(TileEntitySqueezer squeezer, EntityPlayer player) {
        this.squeezer = squeezer;

        this.addSlotToContainer(new SlotAcceptValid(this.squeezer, this.squeezer.INPUT_SLOT, GuiLayout.Squeezer.INPUT_SLOT_X, GuiLayout.Squeezer.INPUT_SLOT_Y));

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

        return this.squeezer.isUseableByPlayer(player);
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

        this.squeezer.getTankMgr().initGuiTracking(icrafting, this, TankType.RED.getName());
        this.squeezer.getTankMgr().initGuiTracking(icrafting, this, TankType.YELLOW.getName());
        this.squeezer.getTankMgr().initGuiTracking(icrafting, this, TankType.BLUE.getName());
        this.squeezer.getTankMgr().initGuiTracking(icrafting, this, TankType.WHITE.getName());

        this.squeezer.getEnergyMgr().initGuiTracking(icrafting, this);

        this.squeezer.getFactoryMgr().initGuiTracking(icrafting, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);

        /* The id will determine if anything happens in each manager */
        this.squeezer.getTankMgr().processGuiTracking(id, data);
        this.squeezer.getEnergyMgr().processGuiTracking(id, data);
        this.squeezer.getFactoryMgr().processGuiTracking(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        this.squeezer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.RED.getName());
        this.squeezer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.YELLOW.getName());
        this.squeezer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.BLUE.getName());
        this.squeezer.getTankMgr().updateGuiTracking(this.crafters, this, TankType.WHITE.getName());

        this.squeezer.getEnergyMgr().updateGuiTracking(this.crafters, this);

        this.squeezer.getFactoryMgr().updateGuiTracking(this.crafters, this);
    }

}
