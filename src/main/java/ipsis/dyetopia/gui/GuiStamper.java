package ipsis.dyetopia.gui;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementButton;
import cofh.lib.gui.element.ElementDualScaled;
import cofh.lib.gui.element.ElementEnergyStored;
import cofh.lib.gui.element.ElementFluidTank;
import ipsis.dyetopia.gui.container.ContainerStamper;
import ipsis.dyetopia.gui.element.ElementIcon;
import ipsis.dyetopia.reference.Reference;
import ipsis.dyetopia.tileentity.TileEntityStamper;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.TankType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiStamper extends GuiBase {

    private static final String TEXTURE_STR = new String(Reference.GUI_TEXTURE_BASE + "stamper.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation(TEXTURE_STR);

    private TileEntityStamper stamper;
    private ElementDualScaled progress;
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

        xSize = 174;
        ySize = 177;
    }

    @Override
    public void initGui() {
        super.initGui();

        /* tanks */
        addElement(new ElementFluidTank(this, 150, 12, this.stamper.getTankMgr().getTank(TankType.PURE.getName())));

        /* energy */
        addElement(new ElementEnergyStored(this, 7, 22, this.stamper.getEnergyMgr().getEnergyStorage()));

        /* buttons */
        addElement(new ElementButton(this, 60, 62, BTN_DN_STR, 176, 0, 176, 16, 176, 32, 16, 16, TEXTURE_STR));
        addElement(new ElementButton(this, 96, 62, BTN_UP_STR, 192, 0, 192, 16, 192, 32, 16, 16, TEXTURE_STR));

        this.progress = ((ElementDualScaled)addElement(new ElementDualScaled(this, 71, 35).setMode(1).setBackground(false).setSize(24, 16).setTexture(Reference.GUI_PROGRESS_TEXTURE, 64, 64)));
        addElement(progress);

        this.selected = ((ElementIcon)addElement(new ElementIcon(this, 78, 62).setIcon(DyeHelper.DyeType.BLACK.getIcon())));
        addElement(selected);
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
