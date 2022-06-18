package ca.hydranoid620.ipipes.blocks.entities;

import ca.hydranoid620.ipipes.iPipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PassiveSupplierPipeBlockEntity extends BlockEntity {
    public PassiveSupplierPipeBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.PASSIVE_SUPPLIER_PIPE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PassiveSupplierPipeBlockEntity be) {

    }
}
