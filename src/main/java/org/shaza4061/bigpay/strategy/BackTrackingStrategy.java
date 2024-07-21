package org.shaza4061.bigpay.strategy;

import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This strategy is useful for a straight rail track
 */
public class BackTrackingStrategy implements DeliveryStrategy {
    private Integer bestTime = Integer.MAX_VALUE;
    private List<Node> bestPath;

    @Override
    public List<Node> execute(Node startNode, Node endNode, Map<String, List<Edge>> edges) {
        backtrack(new ArrayList<>(), startNode, endNode, 0, edges);
        bestPath.remove(0);// Remove the current node to backtrack
        return bestPath;
    }

    private void backtrack(List<Node> currentPath, Node currentNode, Node goal, int currentTime, Map<String, List<Edge>> edges) {
        // Add the current node to the path
        currentPath.add(currentNode);
        // If the goal is reached, check if the current path is shorter than the best path found so far
        if (currentNode.getName().equals(goal.getName())) {
            bestPath = new ArrayList<>(currentPath);
        } else { // Explore neighbors
            for (Edge edge : edges.getOrDefault(currentNode.getName(), new ArrayList<>())) {
                if (!currentPath.contains(edge.getNode2())) { // Avoid cycles
                    backtrack(currentPath, edge.getNode2(), goal, currentTime + edge.getJourneyTimeInMinutes(), edges);
                }
            }
        }

    }
}
