package ipsis.dyetopia.gui.element;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.manager.TankManager;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.util.TankType;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class TabTanks extends TabBase {

    TankManager tankMgr;
    TankType[] tankNames;

    public TabTanks(GuiBase gui, int side, TankManager tankMgr, TankType... names) {

        super(gui, side);

        this.tankMgr = tankMgr;
        if (names != null)
            maxHeight = 18 + (names.length * 24);
        else
            maxHeight = 92;

        maxWidth = 100;
        backgroundColor = Textures.Gui.TANKS_TAB_BACKGROUND;

        if (side == LEFT)
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.TAB_LEFT, 256, 256);
        else
            this.setTexture(Textures.RESOURCE_PREFIX + Textures.Gui.TAB_RIGHT, 256, 256);

        this.tankNames = names.clone();
    }

    @Override
    public void draw() {

        drawBackground();
        drawTabIcon("Icon_Tank");
        if (!isFullyOpened()) {
            return;
        }

        getFontRenderer().drawStringWithShadow(getTitle(), posXOffset() + 20, posY + 6, headerColor);

        for (int idx = 0; idx < tankNames.length; idx++) {

            TankType tankType = tankNames[idx];
            FluidTank tank = tankMgr.getTank(tankType.getName());
            if (tank == null)
                continue;

            getFontRenderer().drawStringWithShadow(StringHelper.localize(tankType.getLocalisation()) + ":", posXOffset() + 6, posY + 18 + (idx * 24), subheaderColor);
            String txt = String.format("%d/%d mB", tank.getFluidAmount(), tank.getCapacity());

            getFontRenderer().drawString(txt, posXOffset() + 14, posY + 30 + (idx * 24), textColor);
        }

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public String getTitle() {

        return StringHelper.localize(Lang.Gui.TAB_TANKS);
    }

    @Override
    public void addTooltip(List<String> list) {

        if (!isFullyOpened())
            list.add(getTitle());
    }
}
