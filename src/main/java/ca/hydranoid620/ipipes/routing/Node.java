package ca.hydranoid620.ipipes.routing;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Node {
    private World world;
    @Getter
    private BlockPos pos;
    @Nullable @Setter @Getter
    private Node parent, child;

    public Node(World world, BlockPos pos, @Nullable Node parent) {
        this.world = world;
        this.pos = pos;
        this.parent = parent;
    }

    public boolean checkIfVisited(BlockPos source) {
        Node node = this;
        while (node != null) {
            if (node.getPos() == source) return true; //unsure if this is the right way to compare blockpos
            node = node.getParent();
        }

        return false;
    }

    @Override
    public String toString() {
        return "NYI";
    }
}
