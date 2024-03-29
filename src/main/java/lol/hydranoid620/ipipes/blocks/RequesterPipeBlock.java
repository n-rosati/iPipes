package lol.hydranoid620.ipipes.blocks;

import lol.hydranoid620.ipipes.blocks.entities.RequesterPipeBlockEntity;
import lol.hydranoid620.ipipes.iPipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class RequesterPipeBlock extends PipeBlock {
    @Override
    protected boolean isConnectable(WorldAccess world, BlockPos pos) {
        return super.isConnectable(world, pos) ||
                world.getBlockEntity(pos) instanceof Inventory;
    }

    /**
     * Only allow {@link RequesterPipeBlock} to connect to {@link Inventory}
     * @param state block state
     * @param world the world
     * @param pos position trying to place at
     * @return if allowed to place at pos
     */
    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        for (Direction direction: DIRECTIONS)
            if (world.getBlockEntity(pos.add(direction.getVector())) instanceof Inventory)
                return true;

        return false;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RequesterPipeBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, iPipes.REQUESTER_PIPE_BLOCK_ENTITY, RequesterPipeBlockEntity::tick);
    }

}
