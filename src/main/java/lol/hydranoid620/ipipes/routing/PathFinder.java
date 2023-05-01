package lol.hydranoid620.ipipes.routing;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class PathFinder {
    public static void calculatePathsFromNode(Node source) {
        source.setDistance(0);

        Set<Node> visitedNodes = new HashSet<>();
        Set<Node> unvisitedNodes = new HashSet<>();
        unvisitedNodes.add(source);

        while (!unvisitedNodes.isEmpty()) {
            Node currentNode = getClosestNode(unvisitedNodes);
            unvisitedNodes.remove(currentNode);
            for (var adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                int edgeWeight = adjacencyPair.getValue();

                if (!visitedNodes.contains(adjacentNode)) {
                    updateTargetNodePathAndDistance(currentNode, edgeWeight, adjacentNode);
                    unvisitedNodes.add(adjacentNode);
                }
            }

            visitedNodes.add(currentNode);
        }
    }

    private static Node getClosestNode(Set<Node> unsettledNodes) {
        Node closestNode = null;
        int shortestDistance = Integer.MAX_VALUE;

        for (Node node : unsettledNodes) {
            int distance = node.getDistance();
            if (distance < shortestDistance) {
                shortestDistance = distance;
                closestNode = node;
            }
        }

        return closestNode;
    }

    private static void updateTargetNodePathAndDistance(Node sourceNode, int edgeWeight, Node targetNode) {
        if (sourceNode.getDistance() + edgeWeight >= targetNode.getDistance()) return;

        targetNode.setDistance(sourceNode.getDistance() + edgeWeight);

        LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
        shortestPath.add(sourceNode);
        targetNode.setShortestPath(shortestPath);
    }
}
