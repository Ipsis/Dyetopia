package com.ipsis.dyetopia.gui.container;

import cofh.lib.gui.slot.SlotAcceptValid;
import com.ipsis.dyetopia.tileentity.TileEntityStamper;
import com.ipsis.dyetopia.util.TankType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerStamper extends Container {

    private TileEntityStamper stamper;

    public ContainerStamper(TileEntityStamper stamper, EntityPlayer player) {

        this.stamper = stamper;

        this.addSlotToContainer(new SlotAcceptValid(this.stamper, this.stamper.INPUT_SLOT, 33, 35));
        this.addSlotToContainer(new SlotAcceptValid(this.stamper, this.stamper.OUTPUT_SLOT, 119, 35));

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

        return this.stamper.isUseableByPlayer(player);
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

        this.stamper.getTankMgr().initGuiTracking(icrafting, this, TankType.PURE.getName());
        this.stamper.getEnergyMgr().initGuiTracking(icrafting, this);
        this.stamper.getFactoryMgr().initGuiTracking(icrafting, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);

        /* The id will determine if anything happens in each manager */
        this.stamper.getTankMgr().processGuiTracking(id, data);
        this.stamper.getEnergyMgr().processGuiTracking(id, data);
        this.stamper.getFactoryMgr().processGuiTracking(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        this.stamper.getTankMgr().updateGuiTracking(this.crafters, this, TankType.PURE.getName());
        this.stamper.getEnergyMgr().updateGuiTracking(this.crafters, this);
        this.stamper.getFactoryMgr().updateGuiTracking(this.crafters, this);
    }
}
