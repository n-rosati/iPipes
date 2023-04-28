package lol.hydranoid620.ipipes.routing;

import lombok.Getter;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Graph {
    @Getter
    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node node) {
        nodes.add(node);
    }

    public void clearPath() {
        for (var node : getNodes()) {
            node.setDistance(Integer.MAX_VALUE);
            node.setShortestPath(new LinkedList<>());
        }
    }
}
