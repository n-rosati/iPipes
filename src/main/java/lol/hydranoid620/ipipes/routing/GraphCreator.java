package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.blocks.PipeBlock;
import lol.hydranoid620.ipipes.iPipes;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class GraphCreator {
    public static Set<Node> generateGraph(BlockPos origin, World world) {
        HashSet<Node> nodes = new HashSet<>();

        Stack<Node> nodesToTraverse = new Stack<>();
        nodesToTraverse.push(new Node(origin, getPipeTypeFromBlockPos(origin, world)));

        while (!nodesToTraverse.isEmpty()) {
            Node curr = nodesToTraverse.pop();
            nodes.add(curr);
            for (Direction direction : Direction.values()) {
                BlockPos neighbour = curr.getPos().add(direction.getVector());
                if (world.getBlockState(neighbour).getBlock() instanceof PipeBlock) {
                    Node newCandidateNode = new Node(neighbour, getPipeTypeFromBlockPos(neighbour, world));
                    if (!nodes.contains(newCandidateNode)) nodesToTraverse.push(newCandidateNode);
                }
            }
        }

        return nodes;
    }

    private static iPipes.Types getPipeTypeFromBlockPos(BlockPos pos, World world) {
        Block block = world.getBlockState(pos).getBlock();
        if (!(block instanceof PipeBlock)) return null;
        return ((PipeBlock) block).getTypeEnum();
    }
}
