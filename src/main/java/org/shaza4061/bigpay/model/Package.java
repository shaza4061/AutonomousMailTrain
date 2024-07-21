package org.shaza4061.bigpay.model;

import org.shaza4061.bigpay.PackageStatus;

import java.util.Objects;

public class Package {

    private String name;
    private Integer weightInKg;
    private Node startingNode;
    private Node destinationNode;
    private PackageStatus status;

    public Package(String name, Integer weightInKg, Node startingNode, Node destinationNode) {
        this.name = name;
        this.weightInKg = weightInKg;
        this.startingNode = startingNode;
        this.destinationNode = destinationNode;
        this.status = PackageStatus.WAITING;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(Integer weightInKg) {
        this.weightInKg = weightInKg;
    }

    public Node getStartingNode() {
        return startingNode;
    }

    public void setStartingNode(Node startingNode) {
        this.startingNode = startingNode;
    }

    public Node getDestinationNode() {
        return destinationNode;
    }

    public void setDestinationNode(Node destinationNode) {
        this.destinationNode = destinationNode;
    }

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Package aPackage = (Package) o;
        return Objects.equals(name, aPackage.name) && Objects.equals(weightInKg, aPackage.weightInKg) && Objects.equals(startingNode, aPackage.startingNode) && Objects.equals(destinationNode, aPackage.destinationNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weightInKg, startingNode, destinationNode);
    }

    @Override
    public String toString() {
        return name;
    }
}
