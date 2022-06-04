package ca.hydranoid620.ipipes.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class SupplierPipeBlock extends PipeBlock {
    @Override
    protected boolean isConnectable(WorldAccess world, BlockPos pos) {
        return super.isConnectable(world, pos) ||
                world.getBlockEntity(pos) instanceof Inventory;
    }

    /**
     * Only allow {@link SupplierPipeBlock} to connect to {@link Inventory}
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
}
