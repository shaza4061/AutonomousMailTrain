package org.shaza4061.bigpay.manager;

import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.model.Package;
import org.shaza4061.bigpay.model.Train;

import java.util.List;
import java.util.Locale;
import java.util.Map;

public interface PackageHandler {
    void addPackage(Package pkg);
    List<Package> getPackagesFrom(Node node);
    List<Package> getPackagesTo(Node node);
    List<Package> getAllPackages();
    List<Package> onLoadPackages(Train train, Node node);
    List<Package> offLoadPackages(Train train, Node node);
    List<Package> getPackageUndelivered();
    List<Package> getPackageInTransit();
    List<Package> getPackageDelivered();
    Map<Node, Long> getPackagesCountGroupByNode();
    Map<Node, Long> getUndeliveredPackagesCountGroupByNode();
}
