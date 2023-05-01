package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.blocks.IPipeConnectable;
import lol.hydranoid620.ipipes.blocks.entities.NetworkControllerBlockEntity;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.*;

public class Graph {
    @Getter
    private Set<Node> nodes = new HashSet<>();

    private Graph() {}

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void clearPath() {
        for (var node : getNodes()) {
            node.setDistance(Integer.MAX_VALUE);
            node.setShortestPath(new LinkedList<>());
        }
    }

    /**
     * Traverse a pipe network to fina all connected pipes
     * @param origin Starting point to search from. Typically a {@link NetworkControllerBlockEntity}
     * @param world The {@link World} to search in
     * @return A Set of unique {@link Node}s that are all connected in a pipe network
     */
    public static Graph create(BlockPos origin, World world) {
        Graph graph = new Graph();

        Stack<Node> nodesToTraverse = new Stack<>();
        nodesToTraverse.push(new Node(origin, world));

        while (!nodesToTraverse.isEmpty()) {
            Node curr = nodesToTraverse.pop();
            graph.addNode(curr);
            for (Direction direction : Direction.values()) {
                var neighbourPos = curr.getPos().add(direction.getVector());
                var block = world.getBlockState(neighbourPos).getBlock();
                if (block instanceof IPipeConnectable) {
                    var newCandidateNode = new Node(neighbourPos, world);
                    if (!graph.getNodes().contains(newCandidateNode)) nodesToTraverse.push(newCandidateNode);
                }
            }
        }

        connectNodes(graph.getNodes());
        return graph;
    }

    /**
     * Finds all adjacent pipes represented as a set of {@link Node}s
     * @param nodes Set of {@link Node}s to sort through
     */
    private static void connectNodes(Set<Node> nodes) {
        Node[] nodesArray = nodes.toArray(Node[]::new);
        for (int i = 0; i < nodes.size() / 2; i++) {
            for (int j = nodes.size() - 1; j > nodes.size() / 2; j--) {
                if (i == j) continue;

                Node a = nodesArray[i];
                Node b = nodesArray[j];

                if (a.getPos().getManhattanDistance(b.getPos()) == 1) {
                    a.addDestination(b, 1);
                    b.addDestination(a, 1);
                }
            }
        }
    }
}
