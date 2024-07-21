package org.shaza4061.bigpay;

import org.shaza4061.bigpay.manager.NetworkManager;
import org.shaza4061.bigpay.manager.NodeManager;
import org.shaza4061.bigpay.manager.PackageHandler;
import org.shaza4061.bigpay.manager.TripPlanner;
import org.shaza4061.bigpay.manager.impl.*;
import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.model.Package;
import org.shaza4061.bigpay.model.Train;
import org.shaza4061.bigpay.strategy.BackTrackingStrategy;

import java.util.ArrayList;
import java.util.List;

public class DeliveryNetworkCommandProcessor implements CommandProcessor {
    private final List<Node> nodes = new ArrayList<>();
    private final List<NodeManager> nodeManagers = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private final List<Train> trains = new ArrayList<>();
    private final PackageHandler packageHandler = new PackageHandlerImpl();

    @Override
    public NetworkManager process(List<String> commands) {
        int lineNo = 0;
        int nodeCount = Integer.parseInt(commands.get(lineNo++));
        for (int i = 0; i < nodeCount; i++) {
            nodes.add(new Node(commands.get(lineNo)));
            lineNo++;
        }
        nodes.forEach(n -> nodeManagers.add(new NodeManagerImpl(n, packageHandler)));
        int edgeCount = Integer.parseInt(commands.get(lineNo++));
        for (int i = 0; i < edgeCount; i++) {
            String[] edgeStr = commands.get(lineNo).split(",");
            Node node1 = nodes.stream().filter(n -> n.getName().equals(edgeStr[1])).findFirst().orElseThrow(() -> new RuntimeException("Invalid node " + edgeStr[1]));
            Node node2 = nodes.stream().filter(n -> n.getName().equals(edgeStr[2])).findFirst().orElseThrow(() -> new RuntimeException("Invalid node " + edgeStr[1]));
            int journeyTime = Integer.parseInt(edgeStr[3]);
            edges.add(new Edge(edgeStr[0], node1, node2, journeyTime));
            lineNo++;
        }
        int deliveries = Integer.parseInt(commands.get(lineNo++));
        for (int i = 0; i < deliveries; i++) {
            String[] deliveriesStr = commands.get(lineNo).split(",");
            Node node1 = nodes.stream().filter(n -> n.getName().equals(deliveriesStr[2])).findFirst().orElseThrow(() -> new RuntimeException("Invalid node " + deliveriesStr[1]));
            Node node2 = nodes.stream().filter(n -> n.getName().equals(deliveriesStr[3])).findFirst().orElseThrow(() -> new RuntimeException("Invalid node " + deliveriesStr[1]));
            int weight = Integer.parseInt(deliveriesStr[1]);
            packageHandler.addPackage(new Package(deliveriesStr[0], weight, node1, node2));
            lineNo++;
        }
        int trainCount = Integer.parseInt(commands.get(lineNo++));
        for (int i = 0; i < trainCount; i++) {
            String[] trainStr = commands.get(lineNo).split(",");
            Node node = nodes.stream().filter(n -> n.getName().equals(trainStr[2])).findFirst().orElseThrow(() -> new RuntimeException("Invalid node " + trainStr[1]));
            TripPlanner tripPlanner = new TripPlannerImpl(edges, new BackTrackingStrategy());
            Train train = new Train(trainStr[0], Integer.parseInt(trainStr[1]), node);
            train.setTrainConductor(new TrainConductorImpl(train, packageHandler, tripPlanner));
            trains.add(train);
            lineNo++;
        }
        return new NetworkManagerImpl(edges, nodeManagers, trains, packageHandler);
    }
}
