package lol.hydranoid620.ipipes.blocks;

import lol.hydranoid620.ipipes.blocks.entities.ActiveSupplierPipeBlockEntity;
import lol.hydranoid620.ipipes.iPipes;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
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
        var be = world.getBlockEntity(pos, iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY);

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (hand == Hand.OFF_HAND || world.isClient) return super.onUse(state, world, pos, player, hand, hit);

        return super.onUse(state, world, pos, player, hand, hit);
    }
}
