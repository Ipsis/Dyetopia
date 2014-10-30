package ipsis.dyetopia.plugins.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.api.IOverlayHandler;
import codechicken.nei.api.IRecipeOverlayRenderer;
import codechicken.nei.recipe.GuiRecipe;
import codechicken.nei.recipe.IUsageHandler;
import ipsis.dyetopia.init.ModBlocks;
import ipsis.dyetopia.util.LogHelper;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import java.util.List;

public class RecipeHandlerSqueezer implements IUsageHandler {

    private UsageManager.UsageInfo info;
    public RecipeHandlerSqueezer(UsageManager.UsageInfo info) {
        this.info = info;
    }

    public RecipeHandlerSqueezer() {
        this.info = null;
    }

    /****************
     * IUsageHandler/ICraftingHandler
     */
    @Override
    public IUsageHandler getUsageHandler(String s, Object... objects) {

        LogHelper.info("getUsageHandler: " + s);
        if (s != "item")
            return this;

        for (int i = 0; i < objects.length; i++) {
            if ((objects[i] instanceof ItemStack)) {
                UsageManager.UsageInfo usageinfo = UsageManager.getUsage((ItemStack)objects[i]);
                if (usageinfo != null) {
                    LogHelper.info("Found usage " + usageinfo);
                    return new RecipeHandlerSqueezer(usageinfo);
                }
            }
        }
        return this;
    }

    @Override
    public String getRecipeName() {
        return "Squeezer";
    }

    @Override
    public int numRecipes() {
        if (this.info != null)
            this.info.getNumRecipes();

        return 0;
    }

    @Override
    public void drawBackground(int i) {

        LogHelper.info("drawBackground: " + i);
    }

    @Override
    public void drawForeground(int i) {

        LogHelper.info("drawForeground: " + i);
    }

    @Override
    public List<PositionedStack> getIngredientStacks(int i) {
        return null;
    }

    @Override
    public List<PositionedStack> getOtherStacks(int i) {
        return null;
    }

    @Override
    public PositionedStack getResultStack(int i) {

        return new PositionedStack(new ItemStack(ModBlocks.blockSqueezer), 166 / 2 - 9, 0, false);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public boolean hasOverlay(GuiContainer guiContainer, Container container, int i) {
        return false;
    }

    @Override
    public IRecipeOverlayRenderer getOverlayRenderer(GuiContainer guiContainer, int i) {
        return null;
    }

    @Override
    public IOverlayHandler getOverlayHandler(GuiContainer guiContainer, int i) {
        return null;
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public List<String> handleTooltip(GuiRecipe guiRecipe, List<String> strings, int i) {
        return null;
    }

    @Override
    public List<String> handleItemTooltip(GuiRecipe guiRecipe, ItemStack itemStack, List<String> strings, int i) {
        return null;
    }

    @Override
    public boolean keyTyped(GuiRecipe guiRecipe, char c, int i, int i2) {
        return false;
    }

    @Override
    public boolean mouseClicked(GuiRecipe guiRecipe, int i, int i2) {
        return false;
    }

}
