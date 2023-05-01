package lol.hydranoid620.ipipes.blocks.entities;

import lol.hydranoid620.ipipes.routing.Node;

import java.util.ArrayList;
import java.util.LinkedList;

public interface IPipeNetworkEndpoint {
    ArrayList<LinkedList<Node>> destinations = new ArrayList<>();
}
