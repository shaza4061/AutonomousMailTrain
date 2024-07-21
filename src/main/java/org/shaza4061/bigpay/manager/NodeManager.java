package org.shaza4061.bigpay.manager;

import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.model.Train;

public interface NodeManager {
    Node getNode();
    void setNode(Node node);
    PackageHandler getPackageHandler();
    //    void setNetworkManager(NetworkManager networkManager);
    void arrived(Train train);
}
