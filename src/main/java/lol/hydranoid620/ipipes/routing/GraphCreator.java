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
    /**
     * Traverse a pipe network to fina all connected pipes
     * @param origin Starting point to search from. Typically a {@link lol.hydranoid620.ipipes.blocks.entities.NetworkControllerBlockEntity}
     * @param world The {@link World} to search in
     * @return A Set of unique {@link Node}s that are all connected in a pipe network
     */
    public static Set<Node> findAllNodesInNetwork(BlockPos origin, World world) {
        HashSet<Node> pipeNodes = new HashSet<>();

        Stack<Node> nodesToTraverse = new Stack<>();
        nodesToTraverse.push(new Node(origin, 1));

        while (!nodesToTraverse.isEmpty()) {
            Node curr = nodesToTraverse.pop();
            pipeNodes.add(curr);
            for (Direction direction : Direction.values()) {
                BlockPos neighbour = curr.getPos().add(direction.getVector());
                if (world.getBlockState(neighbour).getBlock() instanceof PipeBlock) { //TODO: pipe networks connected through the network controller should be one network
                    Node newCandidateNode = new Node(neighbour, 1);
                    if (!pipeNodes.contains(newCandidateNode)) nodesToTraverse.push(newCandidateNode);
                }
            }
        }

        return pipeNodes;
    }

    /**
     * Finds all adjacent pipes represented as a set of {@link Node}s
     * @param nodes Set of {@link Node}s to sort through
     * @return A Set of {@link Edge}s
     */
    public static Set<Edge> findEdges(Set<Node> nodes) {
        //TODO: Improve
        Set<Edge> edges = new HashSet<>();

        if (nodes.size() < 2) return edges;

        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (i == j) continue;

                Node a = ((Node) nodes.toArray()[i]);
                Node b = ((Node) nodes.toArray()[j]);

                if (a.getPos().getManhattanDistance(b.getPos()) == 1) {
                    edges.add(new Edge(a, b));
                }
            }
        }

        return edges;
    }

    /**
     * Finds the type of pipe at a given position in a world
     * @param pos {@link BlockPos} in the world
     * @param world {@link World} to check in
     * @return Type of pipe represented as an {@link iPipes.Types} enum. Will return null if the given position is not a valid pipe type
     */
    private static iPipes.Types getPipeTypeFromBlockPos(BlockPos pos, World world) {
        Block block = world.getBlockState(pos).getBlock();
        if (!(block instanceof PipeBlock)) return null;
        return ((PipeBlock) block).getTypeEnum();
    }
}
