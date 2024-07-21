package org.shaza4061.bigpay.strategy;

import org.shaza4061.bigpay.model.DjikstraNode;
import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;

import java.util.*;

/**
 * When I code this strategy, I thought I can use Djikstra algorithm to find the shortest path
 * I later then realize that this strategy can only be apply is the rail track is a complete circle
 * This strategy will not work if the train track is a straight line
 */
public class DjikstraShortestPathStrategy implements DeliveryStrategy {
    @Override
    public List<Node> execute(Node startNode, Node endNode, Map<String, List<Edge>> edges) {
        PriorityQueue<DjikstraNode> pq = new PriorityQueue<>(Comparator.comparingInt(DjikstraNode::getCost));
        DjikstraNode initialNode = this.createDjikstraNode(startNode, 0);
        pq.add(initialNode);
        Set<Node> visited = new HashSet<>();
        List<Node> paths = new ArrayList<>();
        while (!pq.isEmpty()) {
            DjikstraNode currentNode = pq.poll();
            if (!visited.add(currentNode)) {
                continue;
            }
            paths.add(currentNode);
            if (currentNode.getName().equals(endNode.getName())) {
                return paths;
            }
            edges.get(currentNode.getName()).stream().filter(e -> !visited.contains(e.getNode2())).forEach(e -> {
                DjikstraNode dNode = this.createDjikstraNode(e.getNode2(), currentNode.getCost() + e.getJourneyTimeInMinutes());
                pq.add(dNode);
            });
        }
        return null;
    }

    private DjikstraNode createDjikstraNode(Node node, int cost) {
        DjikstraNode dn = new DjikstraNode(node.getName(), cost);
        dn.setNodeManager(node.getNodeManager());
        return dn;
    }
}
