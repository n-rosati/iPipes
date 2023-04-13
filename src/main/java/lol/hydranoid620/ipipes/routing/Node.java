package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.blocks.*;
import lol.hydranoid620.ipipes.iPipes;
import lombok.Getter;
import lombok.ToString;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.Objects;

/**
 * A pipe piece represented as a Node for use in a graph
 */
@ToString
public class Node implements Comparator<Node> {
    @Getter
    private BlockPos pos;
    @Getter
    private int cost;

    public Node(BlockPos pos, int cost) {
        this.pos = pos;
        this.cost = cost;
    }

    public iPipes.Types getTypeIn(World world) {
        //TODO: Make not ugly (O/C Principle)
        Block block = world.getBlockState(this.getPos()).getBlock();

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

    /**
     * @param first First {@link Node} to compare
     * @param second Second {@link Node} to compare
     * @return +1 if first is more costly than second, -1 if second more costly than first, 0 if same cost
     */
    @Override
    public int compare(Node first, Node second) {
        return 0;
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
