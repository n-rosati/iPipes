package lol.hydranoid620.ipipes.datagen.recipes;

import lol.hydranoid620.ipipes.iPipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancement.criterion.InventoryChangedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataGenerator generator) {
        super(generator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {
        ShapedRecipeJsonBuilder.create(iPipes.PIPE_BLOCK_ITEM, 8)
                               .pattern(" I ")
                               .pattern("IGI")
                               .pattern(" I ")
                               .input('I', Items.IRON_INGOT)
                               .input('G', Items.GLASS_PANE)
                               .criterion("has_iron_ingot", InventoryChangedCriterion.Conditions.items(Items.IRON_INGOT))
                               .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(iPipes.REQUESTER_PIPE_BLOCK_ITEM)
                                  .input(iPipes.PIPE_BLOCK_ITEM, 4)
                                  .input(Items.PURPLE_DYE)
                                  .criterion("has_pipe", InventoryChangedCriterion.Conditions.items(iPipes.PIPE_BLOCK_ITEM))
                                  .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ITEM)
                                  .input(iPipes.PIPE_BLOCK_ITEM, 4)
                                  .input(Items.RED_DYE)
                                  .criterion("has_pipe", InventoryChangedCriterion.Conditions.items(iPipes.PIPE_BLOCK_ITEM))
                                  .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(iPipes.PASSIVE_SUPPLIER_PIPE_BLOCK_ITEM)
                                  .input(iPipes.PIPE_BLOCK_ITEM, 4)
                                  .input(Items.ORANGE_DYE)
                                  .criterion("has_pipe", InventoryChangedCriterion.Conditions.items(iPipes.PIPE_BLOCK_ITEM))
                                  .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(iPipes.STORAGE_PIPE_BLOCK_ITEM)
                                  .input(iPipes.PIPE_BLOCK_ITEM, 4)
                                  .input(Items.CYAN_DYE)
                                  .criterion("has_pipe", InventoryChangedCriterion.Conditions.items(iPipes.PIPE_BLOCK_ITEM))
                                  .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(iPipes.NETWORK_CONTROLLER_BLOCK_ITEM)
                               .pattern("IRI")
                               .pattern("PDP")
                               .pattern("IRI")
                               .input('I', Items.IRON_INGOT)
                               .input('R', Items.REDSTONE)
                               .input('P', iPipes.PIPE_BLOCK_ITEM)
                               .input('D', Items.DIAMOND)
                               .criterion("has_active_supplier_pipe", InventoryChangedCriterion.Conditions.items(iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ITEM))
                               .criterion("has_passive_supplier_pipe", InventoryChangedCriterion.Conditions.items(iPipes.PASSIVE_SUPPLIER_PIPE_BLOCK_ITEM))
                               .criterion("has_requester_pipe", InventoryChangedCriterion.Conditions.items(iPipes.REQUESTER_PIPE_BLOCK_ITEM))
                               .criterion("has_storage_pipe", InventoryChangedCriterion.Conditions.items(iPipes.STORAGE_PIPE_BLOCK_ITEM))
                               .offerTo(exporter);
    }
}
