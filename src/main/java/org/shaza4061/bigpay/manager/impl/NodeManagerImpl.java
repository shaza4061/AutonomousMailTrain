package org.shaza4061.bigpay.manager.impl;

import org.shaza4061.bigpay.manager.NodeManager;
import org.shaza4061.bigpay.manager.PackageHandler;
import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.model.Package;
import org.shaza4061.bigpay.model.Train;

import java.util.ArrayList;
import java.util.List;

public class NodeManagerImpl implements NodeManager {
    private Node node;
    private PackageHandler packageHandler;


    public NodeManagerImpl(Node node, PackageHandler packageHandler) {
        this.node = node;
        this.node.setNodeManager(this);
        this.packageHandler = packageHandler;
    }

    @Override
    public Node getNode() {
        return this.node;
    }

    @Override
    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public void arrived(Train train) {
        List<Package> deliveredPackages = new ArrayList<>();
        List<Package> pickedUpPackages = train.getTrainConductor().getPackageHandler().getPackageInTransit();
        if (train.getEndNode().getName().equals(this.node.getName())) {
            //train reached its destination. offload/onload packages
            deliveredPackages = train.getTrainConductor().getPackageHandler().offLoadPackages(train, this.node);
            pickedUpPackages = train.getTrainConductor().getPackageHandler().onLoadPackages(train, this.node);
        }
        int totalTravelTime = train.getEdge() == null ? 0 : train.getEdge().getJourneyTimeInMinutes();
        train.setTotalTravelTime(train.getTotalTravelTime() + totalTravelTime);

        String message = String.format("W=%d, T=%s, N1=%s, P1=%s, N2=%s, P2=%s",
                train.getTotalTravelTime(), train.getName(),
                train.getPreviousNode().getName(), pickedUpPackages,
                train.getNextNode().getName(), deliveredPackages);
        System.out.println(message);
    }

    public PackageHandler getPackageHandler() {
        return packageHandler;
    }

    public void setPackageHandler(PackageHandler packageHandler) {
        this.packageHandler = packageHandler;
    }

    @Override
    public String toString() {
        return "NodeManagerImpl{" +
                "node=" + node +
                ", packageHandler=" + packageHandler +
                '}';
    }
}
