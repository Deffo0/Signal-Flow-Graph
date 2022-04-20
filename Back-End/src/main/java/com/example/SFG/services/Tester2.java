package com.example.SFG.services;

import com.example.SFG.model.Node;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Tester2 {
    private ForwardPathsAndLoopsGetter pathsGetter;

    @Autowired
    public Tester2(ForwardPathsAndLoopsGetter pathsGetter) {
        this.pathsGetter = pathsGetter;
    }

    public void test() {
        Map<Node, String> toNeighbours1 = new HashMap<>();
        Map<Node, String> toNeighbours2 = new HashMap<>();
        Map<Node, String> toNeighbours3 = new HashMap<>();
        Map<Node, String> toNeighbours4 = new HashMap<>();
        Map<Node, String> toNeighbours5 = new HashMap<>();
        Map<Node, String> toNeighbours6 = new HashMap<>();
        Map<Node, String> toNeighbours7 = new HashMap<>();

        Node node1 = new Node("R");
        Node node2 = new Node("V5");
        Node node3 = new Node("V4");
        Node node4 = new Node("V3");
        Node node5 = new Node("V2");
        Node node6 = new Node("V1");
        Node node7 = new Node("C");

        toNeighbours1.put(node2, "G1");
        toNeighbours2.put(node3, "G2");
        toNeighbours3.put(node2, "H1");
        toNeighbours3.put(node4, "G3");
        toNeighbours4.put(node5, "G4");
        toNeighbours5.put(node4, "H3");
        toNeighbours5.put(node4, "H2");
        toNeighbours5.put(node6, "G5");
        toNeighbours5.put(node6, "G6");
        toNeighbours6.put(node7, "G7");

        node1.setToNeighbours(toNeighbours1);
        node2.setToNeighbours(toNeighbours2);
        node3.setToNeighbours(toNeighbours3);
        node4.setToNeighbours(toNeighbours4);
        node5.setToNeighbours(toNeighbours5);
        node6.setToNeighbours(toNeighbours6);
        node7.setToNeighbours(toNeighbours7);

        List<Node> vertices = new ArrayList<>();

        vertices.add(node1);
        vertices.add(node2);
        vertices.add(node3);
        vertices.add(node4);
        vertices.add(node5);
        vertices.add(node6);
        vertices.add(node7);

        pathsGetter.setVertices(vertices);
        pathsGetter.setSourceIndex(0);
        pathsGetter.setSinkIndex(vertices.size() - 1);
        pathsGetter.getPaths();

        System.out.println(pathsGetter.getNonTouchingLoops());

    }

}