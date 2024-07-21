package org.shaza4061.bigpay.manager.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaza4061.bigpay.manager.PackageHandler;
import org.shaza4061.bigpay.model.Node;
import org.shaza4061.bigpay.model.Package;
import org.shaza4061.bigpay.model.Train;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PackageHandlerImplTest {
    private PackageHandler testObject;

    @BeforeEach
    void init() {
        testObject = new PackageHandlerImpl();
    }

    @Test
    void testAddPackage() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Package package1 = new Package("Package1", 1, nodeA, nodeB);
        Package package2 = new Package("Package2", 1, nodeA, nodeC);
        Package package3 = new Package("Package3", 1, nodeB, nodeC);
        Package package4 = new Package("Package3", 1, nodeB, nodeA);
        Package package5 = new Package("Package3", 1, nodeC, nodeA);
        Package package6 = new Package("Package3", 1, nodeC, nodeB);
        testObject.addPackage(package1);
        testObject.addPackage(package2);
        testObject.addPackage(package3);
        testObject.addPackage(package4);
        testObject.addPackage(package5);
        testObject.addPackage(package6);
        List<Package> packages = testObject.getAllPackages();
        assertEquals(6, packages.size());
    }

    @Test
    void getPackagesFrom() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Package package1 = new Package("Package1", 1, nodeA, nodeB);
        Package package2 = new Package("Package2", 1, nodeA, nodeC);
        Package package3 = new Package("Package3", 1, nodeB, nodeC);
        Package package4 = new Package("Package4", 1, nodeB, nodeA);
        Package package5 = new Package("Package5", 1, nodeC, nodeA);
        Package package6 = new Package("Package6", 1, nodeC, nodeB);
        testObject.addPackage(package1);
        testObject.addPackage(package2);
        testObject.addPackage(package3);
        testObject.addPackage(package4);
        testObject.addPackage(package5);
        testObject.addPackage(package6);
        List<Package> packageA = testObject.getPackagesFrom(nodeA);
        assertEquals(packageA.size(), 2);
        List<Package> packageB = testObject.getPackagesFrom(nodeB);
        assertEquals(packageB.size(), 2);
        List<Package> packageC = testObject.getPackagesFrom(nodeC);
        assertEquals(packageC.size(), 2);
    }

    @Test
    void getPackagesTo() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Package package1 = new Package("Package1", 1, nodeA, nodeB);
        Package package2 = new Package("Package2", 1, nodeA, nodeC);
        Package package3 = new Package("Package3", 1, nodeB, nodeC);
        Package package4 = new Package("Package4", 1, nodeB, nodeA);
        Package package5 = new Package("Package5", 1, nodeC, nodeA);
        Package package6 = new Package("Package6", 1, nodeC, nodeB);
        testObject.addPackage(package1);
        testObject.addPackage(package2);
        testObject.addPackage(package3);
        testObject.addPackage(package4);
        testObject.addPackage(package5);
        testObject.addPackage(package6);
        List<Package> packageA = testObject.getPackagesTo(nodeA);
        assertEquals(packageA.size(), 2);
        List<Package> packageB = testObject.getPackagesTo(nodeB);
        assertEquals(packageB.size(), 2);
        List<Package> packageC = testObject.getPackagesTo(nodeC);
        assertEquals(packageC.size(), 2);
    }

    @Test
    void onLoadPackages() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Package package1 = new Package("Package1", 1, nodeA, nodeB);
        Package package2 = new Package("Package2", 1, nodeA, nodeC);
        Package package3 = new Package("Package3", 1, nodeB, nodeC);
        Package package4 = new Package("Package4", 1, nodeB, nodeA);
        Package package5 = new Package("Package5", 1, nodeC, nodeA);
        Package package6 = new Package("Package6", 1, nodeC, nodeB);
        testObject.addPackage(package1);
        testObject.addPackage(package2);
        testObject.addPackage(package3);
        testObject.addPackage(package4);
        testObject.addPackage(package5);
        testObject.addPackage(package6);
        assertEquals(testObject.getPackageUndelivered().size(), 6);
        Train train1 = new Train("Train1", 1, nodeA);
        List<Package> numPackagesLoaded = testObject.onLoadPackages(train1, train1.getStartNode());
        assertEquals(numPackagesLoaded.size(), 1);
        assertEquals(testObject.getPackageUndelivered().size(), 5);
        assertEquals(testObject.getPackageInTransit().size(), 1);

    }

    @Test
    void testGetPackagesCountGroupByNode() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Package package1 = new Package("Package1", 1, nodeA, nodeB);
        Package package2 = new Package("Package2", 1, nodeA, nodeC);
        Package package3 = new Package("Package3", 1, nodeA, nodeC);
        Package package4 = new Package("Package4", 1, nodeB, nodeA);
        Package package5 = new Package("Package5", 1, nodeC, nodeA);
        Package package6 = new Package("Package6", 1, nodeC, nodeB);
        testObject.addPackage(package1);
        testObject.addPackage(package2);
        testObject.addPackage(package3);
        testObject.addPackage(package4);
        testObject.addPackage(package5);
        testObject.addPackage(package6);
        Map<Node, Long> result = testObject.getPackagesCountGroupByNode();
        assertEquals(result.size(), 3);
        assertEquals(result.get(nodeA).longValue(), 3L);
    }

    @Test
    void testGetUndeliveredPackagesCountGroupByNode() {
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Package package1 = new Package("Package1", 1, nodeA, nodeB);
        Package package2 = new Package("Package2", 1, nodeA, nodeC);
        Package package3 = new Package("Package3", 1, nodeA, nodeC);
        Package package4 = new Package("Package4", 1, nodeB, nodeA);
        Package package5 = new Package("Package5", 1, nodeC, nodeA);
        Package package6 = new Package("Package6", 1, nodeC, nodeB);
        testObject.addPackage(package1);
        testObject.addPackage(package2);
        testObject.addPackage(package3);
        testObject.addPackage(package4);
        testObject.addPackage(package5);
        testObject.addPackage(package6);
        Map<Node, Long> result = testObject.getPackagesCountGroupByNode();
        assertEquals(result.size(), 3);
        assertEquals(result.get(nodeA).longValue(), 3L);
    }
}