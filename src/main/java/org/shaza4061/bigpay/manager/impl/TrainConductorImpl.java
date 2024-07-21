package org.shaza4061.bigpay.manager.impl;

import org.shaza4061.bigpay.PackageStatus;
import org.shaza4061.bigpay.manager.NodeManager;
import org.shaza4061.bigpay.manager.PackageHandler;
import org.shaza4061.bigpay.manager.TrainConductor;
import org.shaza4061.bigpay.manager.TripPlanner;
import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.model.Package;
import org.shaza4061.bigpay.model.Train;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TrainConductorImpl implements TrainConductor {
    private Train train;
    private PackageHandler packageHandler;
    private TripPlanner tripPlanner;

    public TrainConductorImpl(Train train, PackageHandler packageHandler, TripPlanner tripPlanner) {
        this.train = train;
        this.packageHandler = packageHandler;
        this.tripPlanner = tripPlanner;
        this.train.setTrainConductor(this);
    }

    @Override
    public void continueTrip(Node currentNode) {
        if (this.train.getPackages().isEmpty()) {
            System.out.println("No package left on the train");
            //Check if there's other packages need to be delivered
            List<Package> undelivered = this.getPackageHandler().getPackageUndelivered();
            if (undelivered != null && !undelivered.isEmpty()) {
                System.out.println("Undelivered: " + undelivered);
                Map<Node, Long> undeliveredHistogram = this.getPackageHandler().getUndeliveredPackagesCountGroupByNode();
                if (undeliveredHistogram.entrySet().iterator().hasNext()) {
                    Node nextNode = undeliveredHistogram.entrySet().iterator().next().getKey();
                    List<Node> plannedPaths = this.tripPlanner.getItinerary(currentNode, nextNode);
                    this.train.setStartNode(currentNode);
                    this.train.setEndNode(nextNode);
                    this.move(plannedPaths, currentNode);
                }
            }
            return;//break the flow
        }
        this.getTrain().setStartNode(currentNode);// reset position to current node
        Node nextNode = this.getTrain().getDestinationWithMaxPackages();
        this.getTrain().setEndNode(nextNode);
        List<Node> plannedPaths = tripPlanner.getItinerary(currentNode, nextNode);
        this.move(plannedPaths, currentNode);

    }

    @Override
    public void start() {
        Node startingNode = this.getTrain().getStartNode();
        Map<Node, Long> orderedPackageMap = this.packageHandler.getUndeliveredPackagesCountGroupByNode();
        //we will pick up the packages based on which station has the most packages
        if (orderedPackageMap.entrySet().isEmpty()) {
            System.out.println("No packages to be delivered.");
            this.stop();
            return;
        }
        Map.Entry<Node, Long> entry = orderedPackageMap.entrySet().iterator().next();
        Node node = entry.getKey();

        this.getTrain().setEndNode(node);
        List<Node> plannedPaths = tripPlanner.getItinerary(startingNode, node);
        this.move(plannedPaths, startingNode);
        this.getTrain().getTrainConductor().stop();
    }

    @Override
    public void stop() {
        //train stop
        System.out.printf("Train stopped. Undelivered packages = %s%n", this.getPackageHandler().getPackageUndelivered().toString());
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public PackageHandler getPackageHandler() {
        return packageHandler;
    }

    public void setPackageHandler(PackageHandler packageHandler) {
        this.packageHandler = packageHandler;
    }

    public TripPlanner getTripPlanner() {
        return tripPlanner;
    }

    public void setTripPlanner(TripPlanner tripPlanner) {
        this.tripPlanner = tripPlanner;
    }

    @Override
    public String toString() {
        return "TrainConductorImpl{" +
                "train=" + train +
                ", packageHandler=" + packageHandler +
                ", tripPlanner=" + tripPlanner +
                '}';
    }

    private void move(List<Node> paths, Node start) {
        paths.forEach(nextNode -> {
            NodeManager nodeManager = nextNode.getNodeManager();
            train.setPreviousNode(train.getNextNode() == null ? start : train.getNextNode());
            train.setNextNode(nextNode);
            Optional<Edge> currentEdge = this.tripPlanner.getEdge(train.getPreviousNode(), train.getNextNode());
            train.setEdge(currentEdge.orElse(null));
            nodeManager.arrived(this.train);
        });
        this.continueTrip(this.getTrain().getEndNode());
    }
}
