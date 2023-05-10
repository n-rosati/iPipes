package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.blocks.IPipeConnectable;
import lol.hydranoid620.ipipes.blocks.entities.NetworkControllerBlockEntity;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

public class Graph {
    @Getter
    private Set<Node> nodes = new HashSet<>();

    private Graph() {}

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
            var blockState = world.getBlockState(curr.getBlockPos());
            if (blockState.getBlock() instanceof IPipeConnectable) {
                for (var direction : IPipeConnectable.getConnectedDirections(blockState)) {
                    var newCandidateNode = new Node(curr.getBlockPos().add(direction.getVector()), world);
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
        for (int i = 0; i < nodes.size(); i++) {
            for (int j = 0; j < nodes.size(); j++) {
                if (i == j) continue;

                Node a = nodesArray[i];
                Node b = nodesArray[j];

                if (a.getBlockPos().getManhattanDistance(b.getBlockPos()) == 1) {
                    a.addDestination(b, 1);
                    b.addDestination(a, 1);
                }
            }
        }
    }

    private void addNode(Node node) {
        nodes.add(node);
    }

    public void clearAllPaths() {
        for (var node : getNodes()) {
            node.setDistance(Integer.MAX_VALUE);
            node.setShortestPath(new LinkedList<>());
        }
    }
}
