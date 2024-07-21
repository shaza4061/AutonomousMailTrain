package org.shaza4061.bigpay.manager.impl;

import org.shaza4061.bigpay.manager.NodeManager;
import org.shaza4061.bigpay.manager.PackageHandler;
import org.shaza4061.bigpay.manager.TripPlanner;
import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Train;
import org.shaza4061.bigpay.strategy.BackTrackingStrategy;
import org.shaza4061.bigpay.strategy.DeliveryStrategy;

import java.util.List;

public class NetworkManagerImpl implements org.shaza4061.bigpay.manager.NetworkManager {
    private List<Edge> edges;
    private List<NodeManager> nodes;
    private List<Train> trains;
    private PackageHandler packageHandler;
    private TripPlanner tripPlanner;
    private final DeliveryStrategy deliveryStrategy = new BackTrackingStrategy();

    public NetworkManagerImpl(List<Edge> edges, List<NodeManager> nodes, List<Train> trains, PackageHandler packageHandler) {
        this.edges = edges;
        this.nodes = nodes;
        this.trains = trains;
        this.packageHandler = packageHandler;
        this.tripPlanner = new TripPlannerImpl(edges, deliveryStrategy);
    }

    @Override
    public List<Edge> getEdges() {
        return edges;
    }

    @Override
    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    @Override
    public List<NodeManager> getNodes() {
        return nodes;
    }

    @Override
    public void setNodes(List<NodeManager> nodes) {
        this.nodes = nodes;
    }

    @Override
    public List<Train> getTrains() {
        return trains;
    }

    @Override
    public void setTrains(List<Train> trains) {
        this.trains = trains;
    }

    @Override
    public PackageHandler getPackageHandler() {
        return packageHandler;
    }

    @Override
    public void setPackageHandler(PackageHandler packageHandler) {
        this.packageHandler = packageHandler;
    }

    @Override
    public TripPlanner getTripPlanner() {
        return tripPlanner;
    }

    @Override
    public void setTripPlanner(TripPlanner tripPlanner) {
        this.tripPlanner = tripPlanner;
    }
}
