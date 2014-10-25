package ipsis.dyetopia.gui.container;

import ipsis.dyetopia.tileentity.TileEntityFiller;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFiller extends Container {

    private TileEntityFiller filler;

    public ContainerFiller(TileEntityFiller filler, EntityPlayer player) {

        this.filler = filler;

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
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {

        return null;
    }
}
