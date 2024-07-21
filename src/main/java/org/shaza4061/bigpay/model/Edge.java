package org.shaza4061.bigpay.model;

import java.util.Objects;

public class Edge {
    private String name;
    private Node node1;//source node
    private Node node2;//destination node
    private int journeyTimeInMinutes;

    public Edge(String name, Node node1, Node node2, Integer journeyTimeInMinutes) {
        this.name = name;
        this.node1 = node1;
        this.node2 = node2;
        this.journeyTimeInMinutes = journeyTimeInMinutes;
    }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public int getJourneyTimeInMinutes() {
        return journeyTimeInMinutes;
    }

    public void setJourneyTimeInMinutes(Integer journeyTimeInMinutes) {
        this.journeyTimeInMinutes = journeyTimeInMinutes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(name, edge.name) && Objects.equals(node1, edge.node1) && Objects.equals(node2, edge.node2) && Objects.equals(journeyTimeInMinutes, edge.journeyTimeInMinutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, node1, node2, journeyTimeInMinutes);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "name='" + name + '\'' +
                ", node1=" + node1 +
                ", node2=" + node2 +
                ", journeyTimeInMinutes=" + journeyTimeInMinutes +
                '}';
    }
}
