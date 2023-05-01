package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.routing.Node;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActiveSupplierPipeBlockEntity extends BlockEntity implements IPipeNetworkEndpoint {
    @Getter
    private final List<Node> networkConnections = new ArrayList<>();
    @Setter @Getter
    private boolean shouldRebuildPaths = true;
    @Getter
    private ArrayList<LinkedList<Node>> destinations = new ArrayList<>();

    public ActiveSupplierPipeBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY, pos, state);
    }

    @SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, ActiveSupplierPipeBlockEntity be) {
        if (world.isClient) return;
    }
}
