package org.shaza4061.bigpay.manager.impl;

import org.shaza4061.bigpay.manager.TripPlanner;
import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.strategy.DeliveryStrategy;

import java.util.*;

public class TripPlannerImpl implements TripPlanner {
    private Map<String, List<Edge>> edges = new HashMap<>();
    private DeliveryStrategy deliveryStrategy;

    public TripPlannerImpl(List<Edge> edges, DeliveryStrategy deliveryStrategy) {
        this.initializeEdges(edges);
        this.deliveryStrategy = deliveryStrategy;
    }

    @Override
    public List<Node> getItinerary(Node currentNode, Node destinationNode) {
        return Collections.unmodifiableList(deliveryStrategy.execute(currentNode, destinationNode, edges));
    }

    @Override
    public Optional<Edge> getEdge(Node node1, Node node2) {
        return this.edges.get(node1.getName()).stream().filter(e -> e.getNode2().getName().equals(node2.getName())).findFirst();
    }

    public Map<String, List<Edge>> getEdges() {
        return Collections.unmodifiableMap(edges);
    }

    public void setEdges(Map<String, List<Edge>> edges) {
        this.edges = edges;
    }

    public DeliveryStrategy getDeliveryStrategy() {
        return deliveryStrategy;
    }

    public void setDeliveryStrategy(DeliveryStrategy deliveryStrategy) {
        this.deliveryStrategy = deliveryStrategy;
    }

    private void initializeEdges(List<Edge> edges) {
        Map<String, List<Edge>> edgesMap = new HashMap<>();
        for (Edge edge : edges) {
            edgesMap.computeIfAbsent(edge.getNode1().getName(), k -> new ArrayList<>()).add(new Edge(edge.getName(), edge.getNode1(), edge.getNode2(), edge.getJourneyTimeInMinutes()));
            edgesMap.computeIfAbsent(edge.getNode2().getName(), k -> new ArrayList<>()).add(new Edge(edge.getName(), edge.getNode2(), edge.getNode1(), edge.getJourneyTimeInMinutes()));
        }
        this.edges = edgesMap;
    }
}
