package org.shaza4061.bigpay.manager.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaza4061.bigpay.manager.NodeManager;
import org.shaza4061.bigpay.manager.PackageHandler;
import org.shaza4061.bigpay.manager.TrainConductor;
import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.model.Package;
import org.shaza4061.bigpay.model.Train;
import org.shaza4061.bigpay.strategy.BackTrackingStrategy;

import java.util.Arrays;
import java.util.List;


class NetworkManagerImplTest {
    private NetworkManagerImpl testObject;

    @BeforeEach
    void setUp() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Package package1 = new Package("K1", 5, nodeA, nodeC);
        Package package2 = new Package("K2", 2, nodeE, nodeB);
        Package package3 = new Package("K3", 1, nodeB, nodeD);
        PackageHandler packageHandler = new PackageHandlerImpl();
        packageHandler.addPackage(package1);
        packageHandler.addPackage(package2);
        packageHandler.addPackage(package3);
        NodeManager nodeManagerA = new NodeManagerImpl(nodeA, packageHandler);
        NodeManager nodeManagerB = new NodeManagerImpl(nodeB, packageHandler);
        NodeManager nodeManagerC = new NodeManagerImpl(nodeC, packageHandler);
        NodeManager nodeManagerD = new NodeManagerImpl(nodeD, packageHandler);
        NodeManager nodeManagerE = new NodeManagerImpl(nodeE, packageHandler);
        List<NodeManager> nodeManagers = Arrays.asList(nodeManagerA, nodeManagerB, nodeManagerC, nodeManagerD, nodeManagerE);
        Edge edge1 = new Edge("E1", nodeA, nodeB, 30);
        Edge edge2 = new Edge("E2", nodeB, nodeC, 10);
        Edge edge3 = new Edge("E3", nodeC, nodeD, 15);
        Edge edge4 = new Edge("E4", nodeD, nodeE, 20);
        List<Edge> edges = Arrays.asList(edge1, edge2, edge3, edge4);

        Train train1 = new Train("Q1", 6, nodeB);
        TrainConductor conductor = new TrainConductorImpl(train1, packageHandler, new TripPlannerImpl(edges, new BackTrackingStrategy()));

        testObject = new NetworkManagerImpl(edges, nodeManagers, List.of(train1), packageHandler);
    }

    @Test
    void integrationTest() {
        for (Train train : testObject.getTrains()) {
            TrainConductor conductor = train.getTrainConductor();
            conductor.start();
        }
    }

}