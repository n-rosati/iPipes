package lol.hydranoid620.ipipes.routing;

import java.util.HashSet;
import java.util.Set;

public class GraphCreator {
    //TODO: given a node, build the pipe network graph using DFS
    public static Set<Node> generateGraph(Node node) {
        HashSet<Node> nodes = new HashSet<>();

        nodes.add(node);

        return nodes;
    }
}
