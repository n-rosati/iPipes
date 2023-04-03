package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.iPipes;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Objects;

public class Node {
    @Getter
    private BlockPos pos;
    @Getter
    private iPipes.Types pipeType;

    public Node(BlockPos pos, iPipes.Types pipeType) {
        this.pos = pos;
        this.pipeType = pipeType;
    }

    @Override
    public String toString() {
        return pos.toString();
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
