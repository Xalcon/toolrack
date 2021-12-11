package xalcon.toolrack.data.server;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import xalcon.toolrack.ToolRackMod;
import xalcon.toolrack.common.init.ModBlocks;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    public ModRecipeProvider(DataGenerator gen)
    {
        super(gen);
    }
    
    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder
            .shaped(ModBlocks.blockToolRack, 1)
            .pattern("xxx")
            .pattern("x#x")
            .pattern("xxx")
            .define('#', Tags.Items.INGOTS_IRON)
            .define('x', ItemTags.PLANKS)
            .unlockedBy("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
            .save(consumer, ToolRackMod.MOD_ID + ":toolrack_1");
    }
}
