package com.example.SFG.services;

import com.example.SFG.model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Tester {
    private ForwardPathsGetter pathsGetter;

    @Autowired
    public Tester(ForwardPathsGetter pathsGetter) {
        this.pathsGetter = pathsGetter;
    }

    public void test(){
        Map<Node, String> toNeighbours1 = new HashMap<>();
        Map<Node, String> toNeighbours2 = new HashMap<>();
        Map<Node, String> toNeighbours3 = new HashMap<>();
        Map<Node, String> toNeighbours4 = new HashMap<>();
//        Map<Node, String> toNeighbours5 = new HashMap<>();

        Node node1 = new Node("A");
        Node node2 = new Node("B");
        Node node3 = new Node("C");
        Node node4 = new Node("D");
        Node node5 = new Node("E");

        toNeighbours1.put(node2, "2");
        toNeighbours1.put(node3, "3");

        toNeighbours2.put(node3, "5");

        toNeighbours3.put(node4, "9");
        toNeighbours3.put(node2, "8"); /////loop

        toNeighbours4.put(node5, "10");

        node1.setToNeighbours(toNeighbours1);
        node2.setToNeighbours(toNeighbours2);
        node3.setToNeighbours(toNeighbours3);
        node4.setToNeighbours(toNeighbours4);

        List<Node> vertices = new ArrayList<>();
        vertices.add(node1);
        vertices.add(node2);
        vertices.add(node3);
        vertices.add(node4);
        vertices.add(node5);

        pathsGetter.setVertices(vertices);
        pathsGetter.setSourceIndex(0);
        pathsGetter.setSinkIndex(vertices.size() - 1);
        pathsGetter.getPaths();
    }
}
