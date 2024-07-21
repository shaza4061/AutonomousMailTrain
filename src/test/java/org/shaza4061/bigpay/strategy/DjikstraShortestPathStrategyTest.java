package org.shaza4061.bigpay.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.shaza4061.bigpay.model.Edge;
import org.shaza4061.bigpay.model.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DjikstraShortestPathStrategyTest {
    private DjikstraShortestPathStrategy testObject;

    @BeforeEach
    void setUp() {
        testObject = new DjikstraShortestPathStrategy();

    }
    @Test
    void testExecute() {
        Map<String, List<Edge>> edges = new HashMap<>();
        Node nodeA = new Node("A");
        Node nodeB = new Node("B");
        Node nodeC = new Node("C");
        Node nodeD = new Node("D");
        Node nodeE = new Node("E");
        Edge edge1 = new Edge("Edge1", nodeA, nodeB, 3);
        edges.computeIfAbsent(nodeA.getName(), k -> new ArrayList<>()).add(edge1);
        edges.computeIfAbsent(nodeB.getName(), k -> new ArrayList<>()).add(edge1);
        Edge edge2 = new Edge("Edge2", nodeB, nodeC, 3);
        edges.computeIfAbsent(nodeB.getName(), k -> new ArrayList<>()).add(edge2);
        edges.computeIfAbsent(nodeC.getName(), k -> new ArrayList<>()).add(edge2);
        Edge edge3 = new Edge("Edge3", nodeC, nodeD, 3);
        edges.computeIfAbsent(nodeC.getName(), k -> new ArrayList<>()).add(edge3);
        edges.computeIfAbsent(nodeD.getName(), k -> new ArrayList<>()).add(edge3);
        Edge edge4 = new Edge("Edge4", nodeD, nodeE, 3);
        edges.computeIfAbsent(nodeD.getName(), k -> new ArrayList<>()).add(edge4);
        edges.computeIfAbsent(nodeE.getName(), k -> new ArrayList<>()).add(edge4);
        List<Node> result = testObject.execute(nodeB, nodeD, edges);
        assertEquals(3, result.size());
        assertEquals(nodeB.getName(), result.get(0).getName());
        assertEquals(nodeC.getName(), result.get(1).getName());
        assertEquals(nodeD.getName(), result.get(2).getName());
    }

//    @Test
//    void testExecuteWithDifferentTiming() {
//        Map<String, List<Edge>> edges = new HashMap<>();
//        Node nodeA = new Node("A");
//        Node nodeB = new Node("B");
//        Node nodeC = new Node("C");
//
//        Edge edge1 = new Edge("E1", nodeA, nodeB, 30);
//        edges.computeIfAbsent(nodeA.getName(), k -> new ArrayList<>()).add(edge1);
//        edges.computeIfAbsent(nodeB.getName(), k -> new ArrayList<>()).add(edge1);
//        Edge edge2 = new Edge("Edge2", nodeB, nodeC, 3);
//        edges.computeIfAbsent(nodeB.getName(), k -> new ArrayList<>()).add(edge2);
//        edges.computeIfAbsent(nodeC.getName(), k -> new ArrayList<>()).add(edge2);
//        Edge edge3 = new Edge("Edge3", nodeC, nodeD, 3);
//        edges.computeIfAbsent(nodeC.getName(), k -> new ArrayList<>()).add(edge3);
//        edges.computeIfAbsent(nodeD.getName(), k -> new ArrayList<>()).add(edge3);
//        Edge edge4 = new Edge("Edge4", nodeD, nodeE, 3);
//        edges.computeIfAbsent(nodeD.getName(), k -> new ArrayList<>()).add(edge4);
//        edges.computeIfAbsent(nodeE.getName(), k -> new ArrayList<>()).add(edge4);
//        List<Node> result = testObject.execute(nodeB, nodeD, edges);
//    }
}