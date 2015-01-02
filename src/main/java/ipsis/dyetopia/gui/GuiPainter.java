package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.*;
import ipsis.dyetopia.gui.container.ContainerPainter;
import ipsis.dyetopia.gui.element.*;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityPainter;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiPainter  extends GuiBaseDYT {

    private static final String TEXTURE_STR = new String(Textures.Gui.GUI_PAINTER);
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);


    private TileEntityPainter painter;
    private ElementProgressBar progress;
    private ElementIcon selected;

    public static final int BUTTON_DN_ID = 0;
    public static final int BUTTON_UP_ID = 1;

    private static final String BTN_UP_STR = "Up";
    private static final String BTN_DN_STR  = "Down";
    private ElementButton up;
    private ElementButton down;

    public GuiPainter(TileEntityPainter painter, EntityPlayer player) {

        super(new ContainerPainter(painter, player), TEXTURE);
        this.painter = painter;

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTankDYT(this, GuiLayout.Painter.PURE_TANK_X, GuiLayout.Painter.PURE_TANK_Y, this.painter.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, GuiLayout.Painter.ENERGY_X, GuiLayout.Painter.ENERGY_Y, this.painter.getEnergyMgr().getEnergyStorage()));

        /* buttons */
        addElement(new ElementButton(this, 60, 62, BTN_DN_STR, 176, 0, 176, 16, 176, 32, 16, 16, TEXTURE_STR));
        addElement(new ElementButton(this, 96, 62, BTN_UP_STR, 192, 0, 192, 16, 192, 32, 16, 16, TEXTURE_STR));

        this.progress = ((ElementProgressBar)addElement(new ElementProgressBar(this, GuiLayout.Painter.PROGRESS_X, GuiLayout.Painter.PROGRESS_Y, ElementProgressBar.ProgressType.LEFT_TO_RIGHT)));
        addElement(progress);

        this.selected = ((ElementIcon)addElement(new ElementIcon(this, 78, 62).setIcon(DyeHelper.DyeType.BLACK.getIcon())));
        addElement(selected);

        addTab(new TabEnergy(this, TabBase.RIGHT, this.painter));
        addTab(new TabInfo(this, TabBase.LEFT, buildInfoString(Lang.PAINTER_INFO, Lang.PAINTER_INFO_LEN)));
    }

    @Override
    protected void updateElementInformation() {

        this.selected.setIcon(this.painter.getCurrSelected().getIcon());
        this.progress.setQuantity(this.painter.getFactoryMgr().getScaledProgress(24));
    }

    @Override
    public void handleElementButtonClick(String s, int i) {

        if (s.equals(BTN_UP_STR)) {
            painter.incSelected();
        } else if (s.equals(BTN_DN_STR)) {
            painter.decSelected();
        }
    }
}
