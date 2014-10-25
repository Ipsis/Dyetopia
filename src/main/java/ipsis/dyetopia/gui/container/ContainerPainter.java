package ipsis.dyetopia.gui.container;

import ipsis.dyetopia.tileentity.TileEntityPainter;
import ipsis.dyetopia.util.TankType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPainter extends Container {

    private TileEntityPainter painter;

    public ContainerPainter(TileEntityPainter painter, EntityPlayer player) {

        this.painter = painter;

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

        return this.painter.isUseableByPlayer(player);
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

        this.painter.getTankMgr().initGuiTracking(icrafting, this, TankType.PURE.getName());
        this.painter.getEnergyMgr().initGuiTracking(icrafting, this);
        //this.mixer.getFactoryMgr().initGuiTracking(icrafting, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);

        /* The id will determine if anything happens in each manager */
        this.painter.getTankMgr().processGuiTracking(id, data);
        this.painter.getEnergyMgr().processGuiTracking(id, data);
        //this.painter.getFactoryMgr().processGuiTracking(id, data);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        this.painter.getTankMgr().updateGuiTracking(this.crafters, this, TankType.PURE.getName());
        this.painter.getEnergyMgr().updateGuiTracking(this.crafters, this);
        //this.painter.getFactoryMgr().updateGuiTracking(this.crafters, this);
    }
}
