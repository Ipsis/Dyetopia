package ipsis.dyetopia.plugins.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import ipsis.dyetopia.util.DyeHelper;

public abstract class TemplateRecipeHandlerDYT extends TemplateRecipeHandler {

    public abstract class CachedRecipeDYT extends TemplateRecipeHandler.CachedRecipe {

        PositionedStack ingredient;
        PositionedStack result;
        int power;
        DyeHelper.DyeType dye;
    }

    String guiTexture;
    String recipeName;

    public TemplateRecipeHandlerDYT(String guiTexture, String recipeName) {
        this.guiTexture = guiTexture;
        this.recipeName = recipeName;
    }

    @Override
    public String getGuiTexture() {
        return guiTexture;
    }

    @Override
    public String getRecipeName() {
        return recipeName;
    }
}
