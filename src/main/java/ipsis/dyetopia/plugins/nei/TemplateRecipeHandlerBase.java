package ipsis.dyetopia.plugins.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cofh.lib.render.RenderHelper;
import cofh.lib.util.helpers.MathHelper;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class TemplateRecipeHandlerBase extends TemplateRecipeHandler {

    /* NEI GUI coords are slightly offset from the normal ones used in GuiXXX */
    private static final int X_OFFSET = 5;
    private static final int Y_OFFSET = 11;

    /** NEI Background */
    private static final int NEI_GUI_WIDTH = 176;
    private static final int NEI_GUI_HEIGHT = 166;

    /**
     * The below are all the texture sheet locations 
     */

    /* Energy background */
    private static final int GUI_ENERGY_X = 176;
    private static final int GUI_ENERGY_Y = 16;
    private static final int GUI_ENERGY_W = 16;
    private static final int GUI_ENERGY_H = 42;

    /* Energy bar */
    private static final int GUI_ENERGY_BAR_X = 192;
    private static final int GUI_ENERGY_BAR_Y = 16;

    /* Tank background and gauge */
    private static final int GUI_TANK_X = 176;
    private static final int GUI_TANK_Y = 58;
    private static final int GUI_TANK_W = 18;
    private static final int GUI_TANK_H = 62;
    private static final int GUI_TANK_GAUGE_X = 194;
    private static final int GUI_TANK_GAUGE_Y = 58;
    private static final int GUI_TANK_GAUGE_W = 16;
    private static final int GUI_TANK_GAUGE_H = 62;

    /* Progress background */
    private static final int GUI_PROGRESS_X = 176;
    private static final int GUI_PROGRESS_Y = 0;
    private static final int GUI_PROGRESS_W = 24;
    private static final int GUI_PROGRESS_H = 16;

    /* Progress bar */
    private static final int GUI_PROGRESS_BAR_X = 200; /* The moving one! */
    private static final int GUI_PROGRESS_BAR_Y = 0;
    private static final int GUI_PROGRESS_BAR_W = 24;
    private static final int GUI_PROGRESS_BAR_H = 16;

    /* Slot */
    private static final int GUI_SLOT_X = 176;
    private static final int GUI_SLOT_Y = 120;
    private static final int GUI_SLOT_W = 18;
    private static final int GUI_SLOT_H = 18;

    private static int scale(int max, int curr, int size) {
        if (max <= 0)
            return size;

        return MathHelper.round(((double)curr / max) * size);
    }

    public abstract class Widget {
        int x;
        int y;
        int w;
        int h;
        String name;

        public abstract void drawBackground();
        public abstract void drawTooltip(List<String> currenttip);

        public Widget(int x, int y, int w, int h, String name) {

            /* We offset the x,y here, so we only do it once */
            this.x = x - X_OFFSET;
            this.y = y - Y_OFFSET;
            this.w = w;
            this.h = h;
            this.name = name;
        }

        /**
         * Point must be in NEI area coords, not real mouse point
         */
        public boolean pointInWidget(Point p) {

            if (p.x < x || p.x > x + w)
                return false;

            if (p.y < y || p.y > y + h)
                return false;

            return true;
        }
    }

    public class Energy extends Widget {
        int max;
        int curr;

        public Energy(int x, int y, int max, String name) {
            super(x, y, GUI_ENERGY_W, GUI_ENERGY_H, name);
            this.curr = 0;
            this.max = max;
        }

        public void setCurr(int curr) {
            this.curr = curr;
        }

        public void drawBackground() {
            GuiDraw.drawTexturedModalRect(x, y, GUI_ENERGY_X, GUI_ENERGY_Y, GUI_ENERGY_W, GUI_ENERGY_H);
            int scaled = scale(this.max, this.curr, GUI_ENERGY_H);
            GuiDraw.drawTexturedModalRect(x, y + GUI_ENERGY_H - scaled, GUI_ENERGY_BAR_X, GUI_ENERGY_BAR_Y + GUI_ENERGY_H - scaled, GUI_ENERGY_W, scaled);
        }

        public void drawTooltip(List<String> currenttip) {

            currenttip.add(curr + " RF");
        }


    }

    public class Slot extends Widget {

        public Slot(int x, int y, String name) {
            super(x, y, GUI_SLOT_W, GUI_SLOT_H, name);
        }

        public void drawBackground() {
            GuiDraw.drawTexturedModalRect(x, y, GUI_SLOT_X, GUI_SLOT_Y, GUI_SLOT_W, GUI_SLOT_H);
        }

        public void drawTooltip(List<String> currenttip) { }
    }

    public class Progress extends Widget {

        public Progress(int x, int y, String name) {
            super(x, y, GUI_PROGRESS_W, GUI_PROGRESS_H, name);
        }

        public void drawBackground() {
            GuiDraw.drawTexturedModalRect(x, y, GUI_PROGRESS_X, GUI_PROGRESS_Y, GUI_PROGRESS_W, GUI_PROGRESS_H);
            drawProgressBar(x, y, GUI_PROGRESS_BAR_X, GUI_PROGRESS_BAR_Y, GUI_PROGRESS_BAR_W, GUI_PROGRESS_BAR_H, 20, 0);
        }

        public void drawTooltip(List<String> currenttip) { }
    }

    public class Tank extends  Widget {
        int max;
        int curr;
        FluidStack f;

        public Tank(int x, int y, FluidStack f, int max, String name) {
            super(x, y, GUI_TANK_W, GUI_TANK_H, name);
            this.curr = 0;
            this.max = max;
            this.f = f;
        }

        public void setCurr(int curr) {
            this.curr = curr;
        }

        public void drawBackground() {
            /* Gauge starts in from the tank texture */
            GuiDraw.drawTexturedModalRect(x, y, GUI_TANK_X, GUI_TANK_Y, GUI_TANK_W, GUI_TANK_H);
            GuiDraw.drawTexturedModalRect(x + 1, y, GUI_TANK_GAUGE_X, GUI_TANK_GAUGE_Y, GUI_TANK_GAUGE_W, GUI_TANK_GAUGE_H);

            /**
             * The fluid is offset from the tank by a border of 1 pixel
             * Therefore the fluid start 1 in from the right and is 2 smaller than the tank height
             */
            if (f != null) {
                int scaled = scale(max, curr, GUI_TANK_H - 2);
                drawFluid(x + 1, y + 1 + GUI_TANK_H - 2 - scaled, f, GUI_TANK_W - 2, scaled);
            }
        }

        public void drawTooltip(List<String> currenttip) {

            if (f != null) {
                currenttip.add(curr + " mB");
                currenttip.add(f.getLocalizedName());
            }
        }
    }

    private List<Widget> widgets;
    protected String recipeName;

    public void addWidget(Widget w) {
        widgets.add(w);
    }

    public void updateEnergyWidget(String name, int curr) {
        for (Widget w : widgets) {
            if (w instanceof Energy && w.name.equals(name)) {
                ((Energy) w).setCurr(curr);
                break;
            }
        }
    }

    public void updateTankWidget(String name, int curr) {
        for (Widget w : widgets) {
            if (w instanceof Tank && w.name.equals(name)) {
                ((Tank) w).setCurr(curr);
                break;
            }
        }
    }

    @Override
    public String getGuiTexture() {
        return Textures.Gui.GUI_NEI;
    }

    @Override
    public String getRecipeName() {
        return recipeName;
    }

    @Override
    public void drawBackground(int recipe) {
        super.drawBackground(recipe);

        /* Setup the widget values before drawing them */
        updateWidgets(recipe);

        for (Widget widget : widgets)
            widget.drawBackground();
    }

    @Override
    public void drawExtras(int recipe) {

        /* NB This is called from drawForeground */
    }


    @Override
    public List<String> handleTooltip(GuiRecipe gui, List<String> currenttip, int recipe) {

        /**
         * Gui width size of the play area
         * NEI_GUI_WIDTH size of the nei gui area
         * Offset x is how far in the recipe is from the nei gui area
         *
         * Gui width/height are the full Minecraft play area
         * NEI_GUI width/height is the NEI gui, which defaults to
         * The NEI area for one recipe defaults to 166x65
         * The full NEI area defaults to 176x 166
         *
         * Tonius/NEI-Integration
         * https://github.com/Tonius/NEI-Integration/src/main/java/tonius/neiintegration/RecipeHandlerBase.java
          */
        Point mouse = GuiDraw.getMousePosition();
        Point offset = gui.getRecipePosition(recipe);
        Point relMouse = new Point(
                mouse.x - ((gui.width - NEI_GUI_WIDTH) / 2) - offset.x,
                mouse.y - ((gui.height - NEI_GUI_HEIGHT) / 2) - offset.y);

        if (relMouse.x >= 0 && relMouse.y >= 0) {

            /* Setup the widget values before drawing them */
            updateWidgets(recipe);

            for (Widget widget : widgets) {
                if (widget.pointInWidget(relMouse)) {
                    widget.drawTooltip(currenttip);
                    break;
                }
            }
        }

        return super.handleTooltip(gui, currenttip, recipe);
    }

    public abstract void updateWidgets(int recipe);

    @Override
    public boolean hasOverlay(GuiContainer gui, Container container, int recipe) {
        return false;
    }


    /** Initialisation */
    @Override
    public void loadTransferRects() {
        super.loadTransferRects();

        widgets = new ArrayList<Widget>();
        initialiseHandler();
    }

    public void addProgressTransferRect(int x, int y, String name) {
        transferRects.add(new RecipeTransferRect(new Rectangle(x - X_OFFSET, y - Y_OFFSET, GUI_PROGRESS_BAR_W, GUI_PROGRESS_BAR_H), name));
    }


    public abstract void initialiseHandler();

    public PositionedStack createPositionedStack(int x, int y, ItemStack stack) {

        /* itemstack sits inside the slot, which has a 1 pixel border */
        return new PositionedStack(stack, x - X_OFFSET + 1, y - Y_OFFSET + 1);
    }

    /**
     * From CoFHLib to render the fluids
     * In other words, work back from CoFHLib ElementFluidTank!
     * CoFH/CoFHLib//src/main/java/cofh/lib/gui/GuiBase.java
     */

    private void drawFluid(int x, int y, FluidStack fluid, int width, int height) {

        if (fluid == null || fluid.getFluid() == null) {
            return;
        }
        RenderHelper.setBlockTextureSheet();
        RenderHelper.setColor3ub(fluid.getFluid().getColor(fluid));

        drawTiledTexture(x, y, fluid.getFluid().getIcon(fluid), width, height);

        /* Reset the texture sheet back to the gui */
        GuiDraw.changeTexture(this.getGuiTexture());
    }

    private void drawTiledTexture(int x, int y, IIcon icon, int width, int height) {

        int i = 0;
        int j = 0;

        int drawHeight = 0;
        int drawWidth = 0;

        for (i = 0; i < width; i += 16) {
            for (j = 0; j < height; j += 16) {
                drawWidth = Math.min(width - i, 16);
                drawHeight = Math.min(height - j, 16);
                drawScaledTexturedModelRectFromIcon(x + i, y + j, icon, drawWidth, drawHeight);
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0F);
    }

    private void drawScaledTexturedModelRectFromIcon(int x, int y, IIcon icon, int width, int height) {

        if (icon == null) {
            return;
        }
        double minU = icon.getMinU();
        double maxU = icon.getMaxU();
        double minV = icon.getMinV();
        double maxV = icon.getMaxV();

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x + 0, y + height, GuiDraw.gui.getZLevel(), minU, minV + (maxV - minV) * height / 16F);
        tessellator.addVertexWithUV(x + width, y + height, GuiDraw.gui.getZLevel(), minU + (maxU - minU) * width / 16F, minV + (maxV - minV) * height / 16F);
        tessellator.addVertexWithUV(x + width, y + 0, GuiDraw.gui.getZLevel(), minU + (maxU - minU) * width / 16F, minV);
        tessellator.addVertexWithUV(x + 0, y + 0, GuiDraw.gui.getZLevel(), minU, minV);
        tessellator.draw();
    }
}
