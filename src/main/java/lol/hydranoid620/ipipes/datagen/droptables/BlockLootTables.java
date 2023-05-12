package lol.hydranoid620.ipipes.datagen.droptables;

import lol.hydranoid620.ipipes.iPipes;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class BlockLootTables extends FabricBlockLootTableProvider {

    public BlockLootTables(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateBlockLootTables() {
        addDrop(iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK);
        addDrop(iPipes.PASSIVE_SUPPLIER_PIPE_BLOCK);
        addDrop(iPipes.STORAGE_PIPE_BLOCK);
        addDrop(iPipes.REQUESTER_PIPE_BLOCK);
        addDrop(iPipes.PIPE_BLOCK);
        addDrop(iPipes.NETWORK_CONTROLLER_BLOCK);
    }
}
