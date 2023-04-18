package lol.hydranoid620.ipipes.routing;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class Graph {
    @Getter
    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node node) {
        nodes.add(node);
    }
}
