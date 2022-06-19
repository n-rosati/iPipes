package ca.hydranoid620.ipipes.blocks.entities;

import ca.hydranoid620.ipipes.iPipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StoragePipeBlockEntity extends BlockEntity {
    public StoragePipeBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.STORAGE_PIPE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, StoragePipeBlockEntity be) {

    }
}
