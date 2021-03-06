package ipsis.dyetopia.manager;


import ipsis.dyetopia.reference.Settings;
import ipsis.dyetopia.util.DyeHelper;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class StamperManager {

    private static ArrayList<StamperRecipe> recipes = new ArrayList<StamperRecipe>();

    public static void initialise() {

        addRecipe(DyeHelper.DyeType.WHITE);
        addRecipe(DyeHelper.DyeType.ORANGE);
        addRecipe(DyeHelper.DyeType.MAGENTA);
        addRecipe(DyeHelper.DyeType.LIGHTBLUE);
        addRecipe(DyeHelper.DyeType.YELLOW);
        addRecipe(DyeHelper.DyeType.LIME);
        addRecipe(DyeHelper.DyeType.PINK);
        addRecipe(DyeHelper.DyeType.GRAY);
        addRecipe(DyeHelper.DyeType.LIGHTGRAY);
        addRecipe(DyeHelper.DyeType.CYAN);
        addRecipe(DyeHelper.DyeType.PURPLE);
        addRecipe(DyeHelper.DyeType.BLUE);
        addRecipe(DyeHelper.DyeType.BROWN);
        addRecipe(DyeHelper.DyeType.GREEN);
        addRecipe(DyeHelper.DyeType.RED);
        addRecipe(DyeHelper.DyeType.BLACK);
    }

    private static void addRecipe(DyeHelper.DyeType type) {

        StamperRecipe r = new StamperRecipe(type, type.getStack());
        recipes.add(r);
    }

    public static List<StamperRecipe> getRecipes() {
        return recipes;
    }

    public static StamperRecipe getRecipe(DyeHelper.DyeType type) {

        for (StamperRecipe r : recipes)
            if (r.input == type)
                return r;

        return null;
    }

    public static DyeHelper.DyeType getFirst() {
        return recipes.get(0).input;
    }

    public static DyeHelper.DyeType getNext(DyeHelper.DyeType curr) {

        int i;
        boolean found = false;
        for (i = 0; i < recipes.size(); i++)
            if (recipes.get(i).input == curr) {
                found = true;
                break;
            }

        if (!found)
            return recipes.get(0).input;

        i++;
        if (i >= recipes.size())
            return getFirst();

        return recipes.get(i).input;
    }

    public static DyeHelper.DyeType getPrev(DyeHelper.DyeType curr) {

        int i;
        boolean found = false;
        for (i = 0; i < recipes.size(); i++)
            if (recipes.get(i).input == curr) {
                found = true;
                break;
            }

        if (!found)
            return recipes.get(0).input;

        i--;
        if (i <= 0)
            return recipes.get(recipes.size() - 1).input;

        return recipes.get(i).input;
    }



    public static class StamperRecipe implements IFactoryRecipe {

        private int pureAmount;
        private ItemStack output;
        private DyeHelper.DyeType input;
        private int energy;

        private StamperRecipe() { }

        public StamperRecipe(DyeHelper.DyeType type, ItemStack output, int pureAmount) {

            this.pureAmount = pureAmount;
            this.output = output;
            this.input = type;
            this.energy = Settings.Machines.stamperRfRecipe;
        }

        public StamperRecipe(DyeHelper.DyeType type, ItemStack output) {

            this(type, output, DyeHelper.getLCM());
        }

        public ItemStack getOutput() { return output.copy(); }
        public  int getPureAmount() { return pureAmount; }

        /**
         * IFactoryRecipe
         */
        @Override
        public int getEnergy() {
            return this.energy;
        }

    }
}
