package org.shaza4061.bigpay.manager;

import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;

import java.util.List;
import java.util.Optional;

public interface TripPlanner {
    List<Node> getItinerary(Node currentNode, Node destinationNode);
    Optional<Edge> getEdge(Node node1, Node node2);
}
