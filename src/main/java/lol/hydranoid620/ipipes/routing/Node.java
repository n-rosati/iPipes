package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.blocks.*;
import lol.hydranoid620.ipipes.iPipes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

/**
 * A pipe piece represented as a Node for use in a graph
 */
@ToString
public class Node {
    @Getter
    private World world;
    @Getter
    private BlockPos pos;
    @Getter @Setter @ToString.Exclude
    private LinkedList<Node> shortestPath = new LinkedList<>();
    @Getter @Setter
    private int distance = Integer.MAX_VALUE;
    @Getter @ToString.Exclude
    Map<Node, Integer> adjacentNodes = new HashMap<>();

    public Node(BlockPos pos, World world) {
        this.pos = pos;
        this.world = world;
    }

    public void addDestination(Node destination, int distance) {
        adjacentNodes.put(destination, distance);
    }

    public iPipes.Types getTypeInWorld() {
        //TODO: Make not ugly (O/C Principle)
        Block block = this.getWorld().getBlockState(this.getPos()).getBlock();

        if (block instanceof ActiveSupplierPipeBlock) {
            return iPipes.Types.ACTIVE_SUPPLIER_PIPE;
        } else if (block instanceof PassiveSupplierPipeBlock) {
            return iPipes.Types.PASSIVE_PROVIDER_PIPE;
        } else if (block instanceof RequesterPipeBlock) {
            return iPipes.Types.REQUESTER_PIPE;
        } else if (block instanceof StoragePipeBlock) {
            return iPipes.Types.STORAGE_PIPE;
        } else if (block instanceof PipeBlock) {
            return iPipes.Types.PIPE;
        }

        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (this == obj) return true;
        if (obj.getClass() != this.getClass()) return false;

        Node other = (Node) obj;
        return pos.getX() == other.getPos().getX() &&
                pos.getY() == other.getPos().getY() &&
                pos.getZ() == other.getPos().getZ();
    }

    @Override
    public int hashCode() {
        return Objects.hash(pos.getX(), pos.getY(), pos.getZ());
    }
}
