package lol.hydranoid620.ipipes.datagen;

import lol.hydranoid620.ipipes.datagen.droptables.BlockLootTables;
import lol.hydranoid620.ipipes.datagen.languages.EnglishLangProvider;
import lol.hydranoid620.ipipes.datagen.recipes.RecipeGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;

public class DataGeneration implements DataGeneratorEntrypoint {
    /**
     * Register {@link DataProvider} with the {@link FabricDataGenerator} during this entrypoint.
     *
     * @param fabricDataGenerator The {@link FabricDataGenerator} instance
     */
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(EnglishLangProvider::new);
        fabricDataGenerator.addProvider(BlockLootTables::new);
        fabricDataGenerator.addProvider(RecipeGenerator::new);
    }
}
