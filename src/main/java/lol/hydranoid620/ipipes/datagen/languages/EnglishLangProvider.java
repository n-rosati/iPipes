package lol.hydranoid620.ipipes.datagen.languages;

import lol.hydranoid620.ipipes.iPipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class EnglishLangProvider extends FabricLanguageProvider {
    public EnglishLangProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator, "en_us");
    }

    /**
     * Implement this method to register languages.
     *
     * <p>Call {@link TranslationBuilder#add(String, String)} to add a translation.
     *
     * @param translationBuilder
     */
    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(iPipes.ITEM_GROUP, "iPipes");
        translationBuilder.add(iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK, "Active Supplier Pipe");
        translationBuilder.add(iPipes.PASSIVE_SUPPLIER_PIPE_BLOCK, "Passive Supplier Pipe");
        translationBuilder.add(iPipes.STORAGE_PIPE_BLOCK, "Storage Pipe");
        translationBuilder.add(iPipes.REQUESTER_PIPE_BLOCK, "Requester Pipe");
        translationBuilder.add(iPipes.PIPE_BLOCK, "Pipe");
        translationBuilder.add(iPipes.NETWORK_CONTROLLER_BLOCK, "Network Controller");
    }
}
