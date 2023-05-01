package lol.hydranoid620.ipipes.blocks;

import lol.hydranoid620.ipipes.blocks.entities.ActiveSupplierPipeBlockEntity;
import lol.hydranoid620.ipipes.iPipes;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class ActiveSupplierPipeBlock extends SupplierPipeBlock implements BlockEntityProvider {
    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ActiveSupplierPipeBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY, ActiveSupplierPipeBlockEntity::tick);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof ActiveSupplierPipeBlockEntity) ((ActiveSupplierPipeBlockEntity) be).setShouldRebuildPaths(true);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public iPipes.Types getTypeEnum() {
        return iPipes.Types.ACTIVE_SUPPLIER_PIPE;
    }
}
