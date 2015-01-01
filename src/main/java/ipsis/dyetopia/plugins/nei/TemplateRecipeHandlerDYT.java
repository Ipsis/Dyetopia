package ipsis.dyetopia.plugins.nei;

import codechicken.lib.gui.GuiDraw;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import cofh.lib.render.RenderHelper;
import cofh.lib.util.helpers.ColorHelper;
import cofh.lib.util.helpers.MathHelper;
import cofh.lib.util.helpers.StringHelper;
import ipsis.dyetopia.init.ModFluids;
import ipsis.dyetopia.reference.Textures;
import ipsis.dyetopia.util.DyeHelper;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public abstract class TemplateRecipeHandlerDYT extends TemplateRecipeHandler {

    /**
     * The machines need power, liquid dye of some sort.
     */
    public abstract class CachedRecipeDYT extends TemplateRecipeHandler.CachedRecipe {

        PositionedStack ingredient;
        PositionedStack result;
        int energyCurr;
        int energyMax;
        DyeHelper.DyeType dye;

        int redMax, redCurr;    /* id 0 */
        int yellowMax, yellowCurr; /* id 1 */
        int blueMax, blueCurr; /* id 2 */
        int whiteMax, whiteCurr; /* id 3 */
        int pureMax, pureCurr; /* id 4 */
    }

    String recipeName;
    int x;
    int y;
    int w;
    int h;
    String trName;

    private static final int GUI_ENERGY_X = 176;
    private static final int GUI_ENERGY_Y = 16;
    private static final int GUI_ENERGY_BAR_X = 192;
    private static final int GUI_ENERGY_BAR_Y = 16;
    private static final int GUI_TANK_X = 176;
    private static final int GUI_TANK_Y = 58;
    private static final int GUI_TANK_GAUGE_X = 194; /* Only one gauge, it is only NEI! */
    private static final int GUI_TANK_GAUGE_Y = 58;
    private static final int GUI_PROGRESS_X = 176; /* The background */
    private static final int GUI_PROGRESS_Y = 0;
    private static final int GUI_PROGRESS_BAR_X = 200; /* The moving one! */
    private static final int GUI_PROGRESS_BAR_Y = 0;
    private static final int GUI_SLOT_X = 176;
    private static final int GUI_SLOT_Y = 120;

    public void setTransferRect(int x, int y, int w, int h, String trName) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.trName = trName;
    }

    @Override
    public String getGuiTexture() {
        return Textures.Gui.GUI_NEI;
    }

    @Override
    public String getRecipeName() {
        return recipeName;
    }

    public abstract void initialiseHandler();

    @Override
    public void loadTransferRects() {
        initialiseHandler();
        transferRects.add(new RecipeTransferRect(new Rectangle(x, y, w, h), trName));
    }

    @Override
    public void drawBackground(int recipe) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuiDraw.changeTexture(this.getGuiTexture());
        GuiDraw.drawTexturedModalRect(0, 0, 5, 11, 166, 65);

        drawBackgroundWidgets(recipe);
    }

    public void drawBackgroundWidgets(int recipe) { }

    public void drawExtras(int recipe) {

    }

    public void drawSlot(int x, int y) {
        GuiDraw.drawTexturedModalRect(x, y, GUI_SLOT_X, GUI_SLOT_Y, 18, 18);
    }

    public void drawEnergy(int x, int y) {
        GuiDraw.drawTexturedModalRect(x, y, GUI_ENERGY_X, GUI_ENERGY_Y, 16, 42);
    }

    private int scale(int max, int curr, int size) {

        if (max <= 0)
            return size;

        return MathHelper.round(((double)curr / max) * size);
    }

    public void drawEnergyLevel(int x, int y, int recipe) {

        CachedRecipeDYT currRecipe = (CachedRecipeDYT)arecipes.get(recipe);
        if (currRecipe == null) /* Shouldn't really happen! */
            return;

        int scaled = scale(currRecipe.energyMax, currRecipe.energyCurr, 42);
        GuiDraw.drawTexturedModalRect(x, y + 42 - scaled, GUI_ENERGY_BAR_X, GUI_ENERGY_BAR_Y + 42 - scaled, 16, scaled);
    }

    public void drawTank(int x, int y) {
        GuiDraw.drawTexturedModalRect(x, y, GUI_TANK_X, GUI_TANK_Y, 18, 62);
        GuiDraw.drawTexturedModalRect(x + 1, y, GUI_TANK_GAUGE_X, GUI_TANK_GAUGE_Y, 16, 62);
    }

    public void drawTankLevel(int x, int y, int recipe, int id) {

        /* red, yellow, blue, white, pure only */
        if (id < 0 || id > 4)
            return;

        CachedRecipeDYT currRecipe = (CachedRecipeDYT)arecipes.get(recipe);
        if (currRecipe == null) /* Shouldn't really happen! */
            return;

        FluidStack fluidStack = null;
        int curr  = 0;
        int max = 0;

        if (id == 0) {
            fluidStack = new FluidStack(ModFluids.fluidDyeRed.getID(), currRecipe.redCurr);
            curr = currRecipe.redCurr;
            max = currRecipe.redMax;
        } else if (id == 1) {
            fluidStack = new FluidStack(ModFluids.fluidDyeYellow.getID(), currRecipe.yellowCurr);
            curr = currRecipe.yellowCurr;
            max = currRecipe.yellowMax;
        } else if (id == 2) {
            fluidStack = new FluidStack(ModFluids.fluidDyeBlue.getID(), currRecipe.blueCurr);
            curr = currRecipe.blueCurr;
            max = currRecipe.blueMax;
        } else if (id == 3) {
            fluidStack = new FluidStack(ModFluids.fluidDyeWhite.getID(), currRecipe.whiteCurr);
            curr = currRecipe.whiteCurr;
            max = currRecipe.whiteMax;
        } else if (id == 4) {
            fluidStack = new FluidStack(ModFluids.fluidDyePure.getID(), currRecipe.pureCurr);
            curr = currRecipe.pureCurr;
            max = currRecipe.pureMax;
        }

        if (fluidStack == null)
            return;

        int scaled = scale(max, curr, 60);
        drawFluid(x + 1, y + 60 - scaled, fluidStack, 16, scaled);

    }

    public void drawProgress(int x, int y) {
        GuiDraw.drawTexturedModalRect(x, y, GUI_PROGRESS_X, GUI_PROGRESS_Y, 24, 16);
    }

    public void drawProgressBar(int x, int y) {
        drawProgressBar(x, y, GUI_PROGRESS_BAR_X, GUI_PROGRESS_BAR_Y, 24, 16, 20, 0);
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
