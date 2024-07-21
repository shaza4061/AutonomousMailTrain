package org.shaza4061.bigpay.manager;

import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Train;

import java.util.List;

public interface NetworkManager {
    List<Edge> getEdges();

    void setEdges(List<Edge> edges);

    List<NodeManager> getNodes();

    void setNodes(List<NodeManager> nodes);

    List<Train> getTrains();

    void setTrains(List<Train> trains);

    PackageHandler getPackageHandler();

    void setPackageHandler(PackageHandler packageHandler);

    TripPlanner getTripPlanner();

    void setTripPlanner(TripPlanner tripPlanner);
}
