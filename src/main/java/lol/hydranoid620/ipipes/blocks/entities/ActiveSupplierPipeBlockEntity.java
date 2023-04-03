package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.blocks.PipeBlock;
import lol.hydranoid620.ipipes.iPipes;
import lol.hydranoid620.ipipes.routing.Node;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

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
            be.buildPaths();
        }
    }

    private void clearNetworkConnections() {
        this.networkConnections.clear();
    }

    public void buildPaths() {
//        World world = this.getWorld();
//        if (world == null) return;
//
//        Node head = new Node(world, this.getPos(), null);
//        Node currentNode = head;
//        Node prevNode = null;
//        do {
//            BlockEntity be = world.getBlockEntity(currentNode.getPos());
//            if (!(be instanceof PipeBlockEntity)) break;
//
//            List<Direction> connections = PipeBlock.getConnectedDirections(world.getBlockState(be.getPos()));
//            for (var connection : connections) {
//                if (world.getBlockState(be.getPos().add(connection.getVector())).isOf(iPipes.PIPE_BLOCK) && (prevNode != null ? prevNode.getPos() : true) != be.getPos()){
//                    prevNode = currentNode;
//                    currentNode = new Node(world, currentNode.getPos().add(connection.getVector()), prevNode);
//
//                    if (world.getBlockState(be.getPos()).isOf(iPipes.STORAGE_PIPE_BLOCK)) break;
//                }
//            }
//            if (world.getBlockState(be.getPos()).isOf(iPipes.STORAGE_PIPE_BLOCK)) break;
//        } while (true);
//
//        networkConnections.clear();
//        networkConnections.add(head);
//
//        setShouldRebuildPaths(false);
    }

    public String getPathAsText() {
//        if (getNetworkConnections().size() == 0) return "[]";
//
//        StringBuilder sb = new StringBuilder("[");
//
//        Node node = getNetworkConnections().get(0);
//        do {
//            sb.append(node.getPos().toString());
//            sb.append(", ");
//            node = node.getChild();
//        } while (node != null);
//
//        return sb.append("]").toString();
        return "[NYI]";
    }
}
