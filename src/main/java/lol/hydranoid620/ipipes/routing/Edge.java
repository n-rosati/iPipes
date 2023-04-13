package lol.hydranoid620.ipipes.routing;

import lombok.Getter;
import lombok.ToString;

@ToString
public class Edge {
    @Getter
    private Node from, to;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
    }
}
