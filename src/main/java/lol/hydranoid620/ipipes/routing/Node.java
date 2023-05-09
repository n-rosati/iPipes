package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.blocks.*;
import lol.hydranoid620.ipipes.iPipes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

/**
 * A pipe piece represented as a Node for use in a graph
 */
@ToString
public class Node {
    @Getter
    private World world;
    @Getter
    private BlockPos blockPos;
    @Getter @Setter @ToString.Exclude
    private LinkedList<Node> shortestPath = new LinkedList<>();
    @Getter @Setter
    private int distance = Integer.MAX_VALUE;
    @Getter @ToString.Exclude
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(BlockPos blockPos, World world) {
        this.blockPos = blockPos;
        this.world = world;
    }

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public iPipes.Types getTypeInWorld() {
        //TODO: Make not ugly (O/C Principle)
        Block block = this.getWorld().getBlockState(this.getBlockPos()).getBlock();

        if (block instanceof ActiveSupplierPipeBlock) {
            return iPipes.Types.ACTIVE_SUPPLIER_PIPE;
        } else if (block instanceof PassiveSupplierPipeBlock) {
            return iPipes.Types.PASSIVE_SUPPLIER_PIPE;
        } else if (block instanceof RequesterPipeBlock) {
            return iPipes.Types.REQUESTER_PIPE;
        } else if (block instanceof StoragePipeBlock) {
            return iPipes.Types.STORAGE_PIPE;
        } else if (block instanceof PipeBlock) {
            return iPipes.Types.PIPE;
        }

        return null;
    }

    public LinkedList<Node> copyShortestPath() {
        var copyLL = new LinkedList<Node>();

        this.getShortestPath().forEach(node -> {
            var newNode = new Node(node.getBlockPos(), node.getWorld());
            newNode.setDistance(node.getDistance());
            copyLL.add(newNode);
        });

        return copyLL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (obj.getClass() != this.getClass()) return false;

        Node other = (Node) obj;
        return blockPos.getX() == other.getBlockPos().getX() &&
                blockPos.getY() == other.getBlockPos().getY() &&
                blockPos.getZ() == other.getBlockPos().getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }
}
