package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.iPipes;
import lombok.Getter;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class PathFinder {
    @Getter
    private final World world;
    private final Set<Node> nodes;
    private final Set<Edge> edges;
    private final PriorityQueue<Node> priorityQueue = new PriorityQueue<>();


    public PathFinder(World world, Set<Node> nodes, Set<Edge> edges) {
        this.world = world;

        this.nodes = nodes;
        this.edges = edges;
    }

    public List<Node> dijkstra(Set<Node> nodes, Set<Edge> edges, ) {

    }
}
