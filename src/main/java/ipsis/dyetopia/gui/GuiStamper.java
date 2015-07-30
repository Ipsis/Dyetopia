package ipsis.dyetopia.gui;

import cofh.lib.gui.element.*;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.gui.container.ContainerStamper;
import ipsis.dyetopia.gui.element.*;
import ipsis.dyetopia.reference.GuiLayout;
import ipsis.dyetopia.reference.Lang;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.tileentity.TileEntityStamper;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiStamper extends GuiBaseDYT {

    private static final String TEXTURE_STR = new String(Textures.Gui.GUI_STAMPER);
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);

    private TileEntityStamper stamper;
    private ElementProgressBar progress;
    private ElementIcon selected;

    public static final int BUTTON_DN_ID = 0;
    public static final int BUTTON_UP_ID = 1;

    private static final String BTN_UP_STR = "Up";
    private static final String BTN_DN_STR  = "Down";
    private ElementButton up;
    private ElementButton down;

    public GuiStamper(TileEntityStamper stamper, EntityPlayer player) {

        super(new ContainerStamper(stamper, player), TEXTURE);
        this.stamper = stamper;
        this.name = StringHelper.localize(Lang.Gui.TITLE_STAMPER);

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTankDYT(this, GuiLayout.Stamper.PURE_TANK_X, GuiLayout.Stamper.PURE_TANK_Y, this.stamper.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStoredDYT(this, GuiLayout.Stamper.ENERGY_X, GuiLayout.Stamper.ENERGY_Y, this.stamper.getEnergyMgr().getEnergyStorage()));

        /* buttons */
        addElement(new ElementButton(this, 59, 64, BTN_DN_STR, 176, 0, 176, 16, 176, 32, 16, 16, TEXTURE_STR));
        addElement(new ElementButton(this, 96, 64, BTN_UP_STR, 192, 0, 192, 16, 192, 32, 16, 16, TEXTURE_STR));

        this.progress = ((ElementProgressBar)addElement(new ElementProgressBar(this, GuiLayout.Stamper.PROGRESS_X, GuiLayout.Stamper.PROGRESS_Y, ElementProgressBar.ProgressType.LEFT_TO_RIGHT)));
        this.selected = (ElementIcon)addElement(new ElementIcon(this, 78, 64, DyeHelper.DyeType.BLACK.getIcon(), 1));

        addTab(new TabEnergy(this, TabBase.RIGHT, this.stamper));
        addTab(new TabInfo(this, TabBase.LEFT, buildInfoString(Lang.Gui.INFO_STAMPER)));
        addTab(new TabTanks(this, TabBase.RIGHT, this.stamper.getTankMgr(), TankType.PURE));
    }

    @Override
    protected void updateElementInformation() {

        this.selected.setIcon(this.stamper.getCurrSelected().getIcon());
        this.progress.setQuantity(this.stamper.getFactoryMgr().getScaledProgress(24));
    }

    @Override
    public void handleElementButtonClick(String s, int i) {

        if (s.equals(BTN_UP_STR)) {
            stamper.incSelected();
        } else if (s.equals(BTN_DN_STR)) {
            stamper.decSelected();
        }
    }
}
