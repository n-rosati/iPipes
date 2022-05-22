package ca.hydranoid620.ipipes.blocks;

import ca.hydranoid620.ipipes.iPipes;
import net.minecraft.block.AbstractChestBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

@SuppressWarnings("deprecation")
public class SupplierPipeBlock extends PipeBlock {
    public SupplierPipeBlock() {
        super();
    }

    @Override
    protected boolean isConnectable(WorldAccess world, BlockPos pos) {
        Block block = world.getBlockState(pos).getBlock();
        return (super.isConnectable(world, pos) && !(block instanceof SupplierPipeBlock)) ||
                block instanceof AbstractChestBlock ||
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
            if (world.getBlockState(pos.add(direction.getVector())).getBlock() instanceof AbstractChestBlock ||
                world.getBlockEntity(pos.add(direction.getVector())) instanceof Inventory)
                return true;

        return false;
    }
}
