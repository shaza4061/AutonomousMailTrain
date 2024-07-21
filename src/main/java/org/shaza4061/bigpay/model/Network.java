package org.shaza4061.bigpay.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Network {
    private final LinkedList<Node> route = new LinkedList();

    public void addNode(Node node) {
        route.add(node);
    }

    public List<Node> getNodes() {
        return Collections.unmodifiableList(route);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Network network = (Network) o;
        return Objects.equals(route, network.route);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(route);
    }
}
