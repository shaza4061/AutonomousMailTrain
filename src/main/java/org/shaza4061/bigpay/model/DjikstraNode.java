package org.shaza4061.bigpay.model;

public class DjikstraNode extends Node{
    private int cost;

    public DjikstraNode(String name) {
        super(name);
    }

    public DjikstraNode(String name, int cost) {
        super(name);
        this.cost = cost;

    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
