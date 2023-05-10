package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.routing.Node;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;

public class ActiveSupplierPipeBlockEntity extends BlockEntity implements IPipeNetworkEndpoint {
    public ActiveSupplierPipeBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY, pos, state);
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, ActiveSupplierPipeBlockEntity be) {
    }

    @Override
    public ArrayList<LinkedList<Node>> getDestinations() {
        return this.destinations;
    }
}
