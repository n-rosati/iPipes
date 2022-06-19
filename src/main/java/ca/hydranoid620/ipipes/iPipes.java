package ca.hydranoid620.ipipes;

import ca.hydranoid620.ipipes.blocks.*;
import ca.hydranoid620.ipipes.blocks.entities.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.MessageFactory;
import org.apache.logging.log4j.message.ParameterizedMessageFactory;

public class iPipes implements ModInitializer {
    public static final String MOD_ID = "ipipes";
    public static final String MOD_NAME = "iPipes";
    public static final Logger LOGGER = LogManager.getLogger(iPipes.MOD_ID, new MessageFactory() {
        private final String PREFIX = "[" + iPipes.MOD_NAME + "] ";
        @Override
        public Message newMessage(Object message) {
            return ParameterizedMessageFactory.INSTANCE.newMessage(PREFIX + "{}", message);
        }

        @Override
        public Message newMessage(String message) {
            return ParameterizedMessageFactory.INSTANCE.newMessage(PREFIX + message);
        }

        @Override
        public Message newMessage(String message, Object... params) {
            return ParameterizedMessageFactory.INSTANCE.newMessage(PREFIX + message, params);
        }
    });
    public static final ItemGroup IPIPES_GROUP = FabricItemGroupBuilder.build(
            new Identifier(MOD_ID, "general"),
            () -> new ItemStack(iPipes.PIPE_BLOCK));

    public static final String PIPE_BLOCK_ID = "pipe";
    public static final PipeBlock PIPE_BLOCK = new PipeBlock();
    public static final BlockItem PIPE_BLOCK_ITEM = new BlockItem(PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static BlockEntityType<PipeBlockEntity> PIPE_BLOCK_ENTITY;

    public static final String REQUESTER_PIPE_ID = "requester_pipe";
    public static final RequesterPipeBlock REQUESTER_PIPE_BLOCK = new RequesterPipeBlock();
    public static final BlockItem REQUESTER_PIPE_BLOCK_ITEM = new BlockItem(REQUESTER_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static BlockEntityType<PassiveSupplierPipeBlockEntity> PASSIVE_SUPPLIER_PIPE_BLOCK_ENTITY;

    public static final String ACTIVE_SUPPLIER_PIPE_ID = "active_supplier_pipe";
    public static final ActiveSupplierPipeBlock ACTIVE_SUPPLIER_PIPE_BLOCK = new ActiveSupplierPipeBlock();
    public static final BlockItem ACTIVE_SUPPLIER_PIPE_BLOCK_ITEM = new BlockItem(ACTIVE_SUPPLIER_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static BlockEntityType<ActiveSupplierPipeBlockEntity> ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY;

    public static final String PASSIVE_SUPPLIER_ID = "passive_supplier_pipe";
    public static final PassiveSupplierPipeBlock PASSIVE_SUPPLIER_PIPE_BLOCK = new PassiveSupplierPipeBlock();
    public static final BlockItem PASSIVE_SUPPLIER_PIPE_BLOCK_ITEM = new BlockItem(PASSIVE_SUPPLIER_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static BlockEntityType<RequesterPipeBlockEntity> REQUESTER_PIPE_BLOCK_ENTITY;

    public static final String STORAGE_PIPE_ID = "storage_pipe";
    public static final StoragePipeBlock STORAGE_PIPE_BLOCK = new StoragePipeBlock();
    public static final BlockItem STORAGE_PIPE_BLOCK_ITEM = new BlockItem(STORAGE_PIPE_BLOCK, new Item.Settings().group(IPIPES_GROUP));
    public static BlockEntityType<StoragePipeBlockEntity> STORAGE_PIPE_BLOCK_ENTITY;


    @SuppressWarnings("DuplicatedCode")
    @Override
    public void onInitialize() {
        LOGGER.info("Init started");

        LOGGER.info("Registering " + PIPE_BLOCK_ID);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, PIPE_BLOCK_ID), PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, PIPE_BLOCK_ID), PIPE_BLOCK_ITEM);
        PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                              MOD_ID + ":" + PIPE_BLOCK_ID + "_block_entity",
                                              FabricBlockEntityTypeBuilder.create(PipeBlockEntity::new, PIPE_BLOCK).build(null));

        LOGGER.info("Registering " + REQUESTER_PIPE_ID);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, REQUESTER_PIPE_ID), REQUESTER_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, REQUESTER_PIPE_ID), REQUESTER_PIPE_BLOCK_ITEM);
        REQUESTER_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                              MOD_ID + ":" + REQUESTER_PIPE_ID + "_block_entity",
                                              FabricBlockEntityTypeBuilder.create(RequesterPipeBlockEntity::new, REQUESTER_PIPE_BLOCK).build(null));

        LOGGER.info("Registering " + ACTIVE_SUPPLIER_PIPE_ID);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, ACTIVE_SUPPLIER_PIPE_ID), ACTIVE_SUPPLIER_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, ACTIVE_SUPPLIER_PIPE_ID), ACTIVE_SUPPLIER_PIPE_BLOCK_ITEM);
        ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                                              MOD_ID + ":" + ACTIVE_SUPPLIER_PIPE_ID + "_block_entity",
                                                              FabricBlockEntityTypeBuilder.create(ActiveSupplierPipeBlockEntity::new, ACTIVE_SUPPLIER_PIPE_BLOCK).build(null));

        LOGGER.info("Registering " + PASSIVE_SUPPLIER_ID);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, PASSIVE_SUPPLIER_ID), PASSIVE_SUPPLIER_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, PASSIVE_SUPPLIER_ID), PASSIVE_SUPPLIER_PIPE_BLOCK_ITEM);
        PASSIVE_SUPPLIER_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                              MOD_ID + ":" + PASSIVE_SUPPLIER_ID + "block_entity",
                                              FabricBlockEntityTypeBuilder.create(PassiveSupplierPipeBlockEntity::new, PASSIVE_SUPPLIER_PIPE_BLOCK).build(null));

        LOGGER.info("Registering " + STORAGE_PIPE_ID);
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, STORAGE_PIPE_ID), STORAGE_PIPE_BLOCK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, STORAGE_PIPE_ID), STORAGE_PIPE_BLOCK_ITEM);
        STORAGE_PIPE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                                                               MOD_ID + ":" + STORAGE_PIPE_ID + "_block_entity",
                                                               FabricBlockEntityTypeBuilder.create(StoragePipeBlockEntity::new, STORAGE_PIPE_BLOCK).build(null));

        LOGGER.info("Init finished");
    }
}
