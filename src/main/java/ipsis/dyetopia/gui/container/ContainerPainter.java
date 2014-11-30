package ipsis.dyetopia.gui.container;

import cofh.lib.gui.slot.SlotAcceptValid;
import ipsis.dyetopia.gui.GuiPainter;
import ipsis.dyetopia.gui.GuiStamper;
import ipsis.dyetopia.gui.IGuiMessageHandler;
import ipsis.dyetopia.network.message.MessageGuiWidget;
import ipsis.dyetopia.reference.GuiIds;
import ipsis.dyetopia.tileentity.TileEntityPainter;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.TankType;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerPainter extends Container implements IGuiMessageHandler {

    private TileEntityPainter painter;

    public ContainerPainter(TileEntityPainter painter, EntityPlayer player) {

        this.painter = painter;

        this.addSlotToContainer(new SlotAcceptValid(this.painter, this.painter.INPUT_SLOT, 53, 35));
        this.addSlotToContainer(new SlotAcceptValid(this.painter, this.painter.OUTPUT_SLOT, 114, 35));

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

    private DyeHelper.DyeType lastDye;

    @Override
    public void addCraftingToCrafters(ICrafting icrafting) {
        super.addCraftingToCrafters(icrafting);

        this.painter.getTankMgr().initGuiTracking(icrafting, this, TankType.PURE.getName());
        this.painter.getEnergyMgr().initGuiTracking(icrafting, this);
        this.painter.getFactoryMgr().initGuiTracking(icrafting, this);

        this.lastDye = this.painter.getCurrSelected();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);

        /* The id will determine if anything happens in each manager */
        this.painter.getTankMgr().processGuiTracking(id, data);
        this.painter.getEnergyMgr().processGuiTracking(id, data);
        this.painter.getFactoryMgr().processGuiTracking(id, data);

        if (ProgressBar.getIDType(id) == ProgressBar.ID_TYPE.ID_GENERIC) {

            if (ProgressBar.getIDValue(id) == 0)
                this.painter.setCurrSelected(DyeHelper.DyeType.getDye(data));
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        this.painter.getTankMgr().updateGuiTracking(this.crafters, this, TankType.PURE.getName());
        this.painter.getEnergyMgr().updateGuiTracking(this.crafters, this);
        this.painter.getFactoryMgr().updateGuiTracking(this.crafters, this);

        for (Object crafter : crafters) {

            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastDye != this.painter.getCurrSelected()) {
                int progId = ProgressBar.createIDGeneric(0);
                icrafting.sendProgressBarUpdate(this, progId, this.painter.getCurrSelected().ordinal());
            }
        }

        this.lastDye = this.painter.getCurrSelected();
    }

    /**
     * IGuiMessageHandler
     */
    @Override
    public void handleGuiWidget(MessageGuiWidget message) {

        if (message.guiId != GuiIds.GUI_PAINTER || message.ctrlType != GuiIds.GUI_CTRL_BUTTON)
            return;

        if (message.ctrlId == GuiPainter.BUTTON_DN_ID)
            this.painter.setPrevCurrSelected();
        else if (message.ctrlId == GuiPainter.BUTTON_UP_ID)
            this.painter.setNextCurrSelected();
    }
}
