package org.shaza4061.bigpay.strategy;

import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;

import java.util.List;
import java.util.Map;

public interface DeliveryStrategy {
    List<Node> execute(Node startNode, Node endNode, Map<String, List<Edge>> edges);
}
