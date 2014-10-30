package ipsis.dyetopia.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class Recipes {

    public static void init() {

        initItemRecipes();
        initBlockRecipes();
    }

    private static void initItemRecipes() {

        GameRegistry.addShapedRecipe(new ItemStack(ModItems.itemDyeBlank, 16), "   ", " r ", "r r", 'r', Items.reeds);
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemDyeGun), " g ", " i ", "ipi", 'g', "nuggetGold", 'i', "ingotIron", 'p', "paneGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemDyemeal), " r ", "ymb", "   ", 'r', "dyeRed", 'y', "dyeYellow", 'b', "dyeBlue", 'm', Items.bone));
        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.itemEraser), Items.clay_ball, ModItems.itemDyeBlank);
    }

    private static void initBlockRecipes() {

        /* Plants */
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockSaplingDye, 1, 0), "dyeRed", "treeSapling"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockSaplingDye, 1, 1), "dyeYellow", "treeSapling"));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ModBlocks.blockSaplingDye, 1, 2), "dyeBlue", "treeSapling"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockSaplingDye, 1, 3), " r ", "ysb", " w ", 'r', "dyeRed", 'y', "dyeYellow", 'b', "dyeBlue", 'w', "dyeWhite", 's', "treeSapling"));

        /* Machines parts */
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockCasing), "lll", "i i", "lll", 'l', Blocks.stone_slab, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockController), " t ", "ici", "rrr", 't', Blocks.crafting_table, 'i', "ingotIron", 'c', ModBlocks.blockCasing, 'r', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockValve), " b ", "ici", "rrr", 'b', Items.glass_bottle, 'i', "ingotIron", 'c', ModBlocks.blockCasing, 'r', Items.redstone));

        /* Machines */
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockMixer), " s ", "ici", "r r", 's', Items.cauldron, 'i', "ingotIron", 'c', ModBlocks.blockCasing, 'r', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockPainter), " s ", "ici", "r r", 's', ModItems.itemDyeGun, 'i', "ingotIron", 'c', ModBlocks.blockCasing, 'r', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockSqueezer), " s ", "ici", "r r", 's', Blocks.piston, 'i', "ingotIron", 'c', ModBlocks.blockCasing, 'r', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockStamper), " s ", "ici", "r r", 's', Blocks.dropper, 'i', "ingotIron", 'c', ModBlocks.blockCasing, 'r', Items.redstone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModBlocks.blockFiller), " s ", "ici", "r r", 's', Items.bucket, 'i', "ingotIron", 'c', ModBlocks.blockCasing, 'r', Items.redstone));
    }
}
