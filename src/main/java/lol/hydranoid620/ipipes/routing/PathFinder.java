package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.iPipes;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class PathFinder {
    @Getter
    private final World world;
    private final Map<iPipes.Types, Integer> weights = new Hashtable<>(); // weights of pipe types
    private final Set<Node> nodes = new HashSet<>();
    private final Set<Edge> edges = new HashSet<>();
    private final Map<Node, Integer> gScore = new HashMap<>(); // cost from start to node
    private final Map<Node, Integer> fScore = new HashMap<>(); // estimated cost from start to end through node
    private final Map<Node, Node> cameFrom = new HashMap<>(); // previous node in optimal path

    public PathFinder(World world) {
        this.world = world;
        weights.put(iPipes.Types.PIPE, 1);
        weights.put(iPipes.Types.REQUESTER_PIPE, 5);
        weights.put(iPipes.Types.ACTIVE_SUPPLIER_PIPE, 10);
        weights.put(iPipes.Types.PASSIVE_PROVIDER_PIPE, 5);
        weights.put(iPipes.Types.STORAGE_PIPE, 1);
    }

    public void addNode(BlockPos pos, iPipes.Types pipeType) {
        nodes.add(new Node(pos, pipeType));
    }

    public void addEdge(Node first, Node second) {
        edges.add(new Edge(first, second, second.getPipeType()));
    }

    private int getCostStartToNode(Node node) {
        return gScore.getOrDefault(node, Integer.MAX_VALUE);
    }

    private int getEstCostStartToEndThroughNode(Node node) {
        return fScore.getOrDefault(node, Integer.MAX_VALUE);
    }

    private Set<Edge> getEdges(Node node) {
        Set<Edge> result = new HashSet<>();

        for (Edge edge : edges) {
            if (edge.getFrom().equals(node)) {
                result.add(edge);
            } else if (edge.getTo().equals(node)) {
                result.add(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
            }
        }

        return result;
    }

    private Node getNeighbour(Node node, Edge edge) {
        if (edge.getFrom().equals(node)) {
            return edge.getTo();
        } else if(edge.getTo().equals(node )) {
            return edge.getFrom();
        } else {
            throw new IllegalArgumentException("Edge {" + edge + "} does not connect to node {" + node + "}");
        }
    }

    private int heuristic(Node first, Node second) {
        return first.getPos().getManhattanDistance(second.getPos());
    }

    private List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        path.add(node);

        while (cameFrom.containsKey(node)) {
            node = cameFrom.get(node);
            path.add(0, node);
        }

        return path;
    }

    public List<Node> findPath(Node start, Node target) {
        gScore.put(start, 0);
        fScore.put(start, heuristic(start, target));

        Set<Node> openSet = new HashSet<>();
        openSet.add(start);

        while (!openSet.isEmpty()){
            Node curr = null;
            int lowestFScore = Integer.MAX_VALUE;
            for (Node node : openSet) {
                int fScore = getEstCostStartToEndThroughNode(node);
                if (fScore < lowestFScore) {
                    curr = node;
                    lowestFScore = fScore;
                }
            }

            if (curr.equals(target)) {
                return reconstructPath(curr);
            }

            openSet.remove(curr);
            for (Edge edge : getEdges(curr)) {
                Node neighbour = getNeighbour(curr, edge);
                if (neighbour.getPipeType() == iPipes.Types.STORAGE_PIPE) continue;
                int tentativeGScore = getCostStartToNode(curr) + weights.get(edge.getWeight());

                if (tentativeGScore < getCostStartToNode(neighbour)) {
                    cameFrom.put(neighbour, curr);
                    gScore.put(neighbour, tentativeGScore);
                    fScore.put(neighbour, tentativeGScore + heuristic(neighbour, target));
                    if (!openSet.contains(neighbour)) {
                        openSet.add(neighbour);
                    }
                }
            }
        }

        return null;
    }
}
