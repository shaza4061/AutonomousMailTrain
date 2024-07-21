package org.shaza4061.bigpay.model;

import org.shaza4061.bigpay.manager.TrainConductor;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class Train {
    private String name;
    private int capacityInKg;
    private int totalTravelTime;
    private TrainConductor trainConductor;
    private final Map<String, List<Package>> packages = new HashMap<>();
    private Node startNode;
    private Node endNode;
    private Node nextNode;
    private Node previousNode;
    private Edge edge;

    public Train(String name, Integer capacityInKg, Node startNode) {
        this.name = name;
        this.capacityInKg = capacityInKg;
        this.startNode = startNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacityInKg() {
        return capacityInKg;
    }

    public Node getStartNode() {
        return startNode;
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public Node getNextNode() {
        return nextNode;
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(Node previousNode) {
        this.previousNode = previousNode;
    }

    public Map<String, List<Package>> getPackages() {
        return packages;
    }

    public List<Package> getPackageList() {
        List<Package> packageList = new ArrayList<>();
        packages.values().forEach(packageList::addAll);
        return packageList;
    }

    public Node getDestinationWithMaxPackages() {
        Map<Node, Long> sortedHistogram = this.getPackageList().stream().collect(groupingBy(Package::getDestinationNode, Collectors.counting()));
        return sortedHistogram.isEmpty()? null: sortedHistogram.entrySet().iterator().next().getKey();
    }

    public TrainConductor getTrainConductor() {
        if (trainConductor == null) {
            throw new RuntimeException("No TrainConductor found");
        }
        return trainConductor;
    }

    public void setTrainConductor(TrainConductor trainConductor) {
        this.trainConductor = trainConductor;
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge(Edge edge) {
        this.edge = edge;
    }

    public Node getEndNode() {
        return endNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public void setCapacityInKg(int capacityInKg) {
        this.capacityInKg = capacityInKg;
    }

    public int getTotalTravelTime() {
        return totalTravelTime;
    }

    public void setTotalTravelTime(int totalTravelTime) {
        this.totalTravelTime = totalTravelTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(name, train.name) && Objects.equals(capacityInKg, train.capacityInKg) && Objects.equals(trainConductor, train.trainConductor) && Objects.equals(packages, train.packages) && Objects.equals(startNode, train.startNode) && Objects.equals(endNode, train.endNode) && Objects.equals(nextNode, train.nextNode) && Objects.equals(previousNode, train.previousNode) && Objects.equals(edge, train.edge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacityInKg, trainConductor, packages, startNode, endNode, nextNode, previousNode, edge);
    }

    @Override
    public String toString() {
        return "Train{" +
                "name='" + name + '\'' +
                ", capacityInKg=" + capacityInKg +
                ", packages=" + packages +
                ", startNode=" + startNode +
                ", endNode=" + endNode +
                ", nextNode=" + nextNode +
                ", previousNode=" + previousNode +
                ", edge=" + edge +
                '}';
    }
}
