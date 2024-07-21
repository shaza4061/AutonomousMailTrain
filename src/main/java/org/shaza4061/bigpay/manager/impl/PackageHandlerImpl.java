package org.shaza4061.bigpay.manager.impl;

import org.shaza4061.bigpay.PackageStatus;
import org.shaza4061.bigpay.manager.PackageHandler;
import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.model.Package;
import org.shaza4061.bigpay.model.Train;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

public class PackageHandlerImpl implements PackageHandler {
    private final Map<Node, List<Package>> sourcePackagesMap = new HashMap<>();
    private final Map<Node, List<Package>> destinationPackagesMap = new HashMap<>();

    @Override
    public void addPackage(Package pkg) {
        List<Package> sourcePackages = this.sourcePackagesMap.computeIfAbsent(pkg.getStartingNode(), k -> new ArrayList<>());
        sourcePackages.add(pkg);

        List<Package> destinationPackages = this.destinationPackagesMap.computeIfAbsent(pkg.getDestinationNode(), k -> new ArrayList<>());
        destinationPackages.add(pkg);
    }

    @Override
    public List<Package> getPackagesFrom(Node node) {
        return this.sourcePackagesMap.get(node);
    }

    @Override
    public List<Package> getPackagesTo(Node node) {
        return this.destinationPackagesMap.get(node);
    }

    @Override
    public List<Package> getAllPackages() {
        LinkedHashSet<Package> packages = new LinkedHashSet<>();
        for (List<Package> sourcePackages : this.sourcePackagesMap.values()) {
            packages.addAll(sourcePackages);
        }

        for (List<Package> destinationPackages : this.destinationPackagesMap.values()) {
            packages.addAll(destinationPackages);
        }

        return List.copyOf(packages);
    }

    @Override
    public List<Package> onLoadPackages(Train train, Node node) {
        int currentCapacity = train.getPackageList().stream().map(Package::getWeightInKg).reduce(0, Integer::sum);
        List<Package> loadedPackage = new ArrayList<>();
        List<Package> packages = this.sourcePackagesMap.get(node);
        if (packages != null) {
            Map<String, List<Package>> cargo = train.getPackages();
            for (Package pkg : packages) {
                if (pkg.getStartingNode().getName().equals(node.getName()) && pkg.getStatus().equals(PackageStatus.WAITING)) {
                    if (currentCapacity <= train.getCapacityInKg()) {
                        pkg.setStatus(PackageStatus.IN_PROGRESS);
                        cargo.computeIfAbsent(pkg.getDestinationNode().getName(), k -> new ArrayList<>()).add(pkg);
                        loadedPackage.add(pkg);
                        currentCapacity += pkg.getWeightInKg();
                    }
                }
            }
        }
        return loadedPackage;
    }

    @Override
    public List<Package> offLoadPackages(Train train, Node node) {
        List<Package> delivered = new ArrayList<>();
        List<Package> packages = train.getPackages().get(node.getName());
        if (packages != null) {
            for (Package pkg : packages) {
                pkg.setStatus(PackageStatus.DELIVERED);
                delivered.add(pkg);
            }
            train.getPackages().remove(node.getName());
        }

        return delivered;
    }

    @Override
    public List<Package> getPackageUndelivered() {
        List<Package> packages = new ArrayList<>();
        this.sourcePackagesMap.values().forEach(packages::addAll);
        return packages.stream().filter(p -> p.getStatus().equals(PackageStatus.WAITING)).collect(Collectors.toList());
    }

    @Override
    public List<Package> getPackageInTransit() {
        List<Package> packages = new ArrayList<>();
        this.sourcePackagesMap.values().forEach(packages::addAll);
        return packages.stream().filter(p -> p.getStatus().equals(PackageStatus.IN_PROGRESS)).collect(Collectors.toList());
    }

    @Override
    public List<Package> getPackageDelivered() {
        List<Package> packages = new ArrayList<>();
        this.sourcePackagesMap.values().forEach(packages::addAll);
        return packages.stream().filter(p -> p.getStatus().equals(PackageStatus.DELIVERED)).collect(Collectors.toList());
    }

    /**
     * This method generate an ordered histogram of the packages in descending order
     *
     * @return ordered Map of nodes based on package count in descending order
     */
    @Override
    public Map<Node, Long> getPackagesCountGroupByNode() {
        Map<Node, Long> unsortedHistogram = this.getAllPackages().stream().collect(groupingBy(Package::getStartingNode, Collectors.counting()));

        return unsortedHistogram.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @Override
    public Map<Node, Long> getUndeliveredPackagesCountGroupByNode() {
        Map<Node, Long> unsortedUndeliveredPackagesHistogram = this.getAllPackages().stream().filter(p -> p.getStatus().equals(PackageStatus.WAITING)).collect(groupingBy(Package::getStartingNode, Collectors.counting()));

        return unsortedUndeliveredPackagesHistogram.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    @Override
    public String toString() {
        return "PackageHandlerImpl{" +
                "sourcePackagesMap=" + sourcePackagesMap +
                ", destinationPackagesMap=" + destinationPackagesMap +
                '}';
    }
}
