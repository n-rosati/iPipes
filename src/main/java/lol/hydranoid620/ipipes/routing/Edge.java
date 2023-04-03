package lol.hydranoid620.ipipes.routing;

import lol.hydranoid620.ipipes.iPipes;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = {"weight"})
public class Edge {
    @Getter
    private Node from, to;
    @Getter
    @Setter
    private iPipes.Types weight;

    public Edge(Node from, Node to, iPipes.Types weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }
}
