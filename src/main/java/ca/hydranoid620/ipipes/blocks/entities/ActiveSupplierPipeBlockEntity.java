package ca.hydranoid620.ipipes.blocks.entities;

import ca.hydranoid620.ipipes.blocks.PipeBlock;
import ca.hydranoid620.ipipes.iPipes;
import ca.hydranoid620.ipipes.routing.Node;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActiveSupplierPipeBlockEntity extends BlockEntity {
    @Getter
    private final List<Node> networkConnections = new ArrayList<>();
    @Setter @Getter
    private boolean shouldRebuildPaths = true;

    public ActiveSupplierPipeBlockEntity(BlockPos pos, BlockState state) {
        super(iPipes.ACTIVE_SUPPLIER_PIPE_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, ActiveSupplierPipeBlockEntity be) {
        if (world.isClient) return;

        if (be.isShouldRebuildPaths()) {
            be.clearNetworkConnections();
            be.rebuildPaths();
        }
    }

    private void clearNetworkConnections() {
        this.networkConnections.clear();
    }

    public void rebuildPaths() {
        World world = Objects.requireNonNull(this.getWorld());

        Node node = new Node(world, this.getPos(), null);
        Node newNode;
        do {
            BlockState bs = world.getBlockState(node.getPos());
            if (!(bs.getBlock() instanceof PipeBlock)) break;

            newNode = new Node(world, node.getPos().add(((PipeBlock) bs.getBlock()).getConnectedDirections(bs).get(0).getOpposite().getVector()), node);

            if (world.getBlockState(newNode.getPos()).getBlock() instanceof ChestBlock) break;

            node.setChild(newNode);
            if (node.getParent() == null) networkConnections.add(node);

            node = node.getChild();
        } while (node != null);

        setShouldRebuildPaths(false);
    }

    public String getPathAsText() {
        if (getNetworkConnections().size() == 0) return "[]";

        StringBuilder sb = new StringBuilder("[");

        Node node = getNetworkConnections().get(0);
        do {
            sb.append(node.getPos().toString());
            sb.append(", ");
            node = node.getChild();
        } while (node != null);

        return sb.append("]").toString();
    }
}
