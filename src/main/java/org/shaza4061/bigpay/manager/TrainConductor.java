package org.shaza4061.bigpay.manager;

import org.shaza4061.bigpay.model.Node;

public interface TrainConductor {
    void continueTrip(Node currentNode);
    void start();
    void stop();
    PackageHandler getPackageHandler();
}
