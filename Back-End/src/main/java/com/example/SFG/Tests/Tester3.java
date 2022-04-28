package com.example.SFG.Tests;

import com.example.SFG.Model.Node;
import com.example.SFG.Services.NetworkAnalyser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Tester3 {
    private NetworkAnalyser pathsGetter;

    @Autowired
    public Tester3(NetworkAnalyser pathsGetter) {
        this.pathsGetter = pathsGetter;
    }

    public void test() {
        Map<Node, String> toNeighboursR = new HashMap<>();
        Map<Node, String> toNeighbours1 = new HashMap<>();
        Map<Node, String> toNeighbours2 = new HashMap<>();
        Map<Node, String> toNeighbours3 = new HashMap<>();
        Map<Node, String> toNeighbours4 = new HashMap<>();
        Map<Node, String> toNeighbours5 = new HashMap<>();
        Map<Node, String> toNeighbours6 = new HashMap<>();
        Map<Node, String> toNeighboursC = new HashMap<>();

        Node nodeR = new Node("R");
        Node node1 = new Node("V1");
        Node node2 = new Node("V2");
        Node node3 = new Node("V3");
        Node node4 = new Node("V4");
        Node node5 = new Node("V5");
        Node node6 = new Node("V6");
        Node nodeC = new Node("C");

        toNeighboursR.put(node1, "G1");
        toNeighboursR.put(node4, "G5");
        toNeighbours1.put(node2, "G2");
        toNeighbours2.put(node1, "H2");
        toNeighbours2.put(node3, "G3");
        toNeighbours3.put(nodeC, "G4");
        toNeighbours3.put(node2, "H3");
        toNeighbours4.put(node5, "G6");
        toNeighbours5.put(node4, "H6");
        toNeighbours5.put(node6, "G7");
        toNeighbours6.put(node5, "H7");
        toNeighbours6.put(nodeC, "G8");

        nodeR.setToNeighbours(toNeighboursR);
        node1.setToNeighbours(toNeighbours1);
        node2.setToNeighbours(toNeighbours2);
        node3.setToNeighbours(toNeighbours3);
        node4.setToNeighbours(toNeighbours4);
        node5.setToNeighbours(toNeighbours5);
        node6.setToNeighbours(toNeighbours6);
        nodeC.setToNeighbours(toNeighboursC);

        List<Node> vertices = new ArrayList<>();

        vertices.add(nodeR);
        vertices.add(node1);
        vertices.add(node2);
        vertices.add(node3);
        vertices.add(node4);
        vertices.add(node5);
        vertices.add(node6);
        vertices.add(nodeC);

        pathsGetter.setVertices(vertices);
        pathsGetter.setSourceIndex(0);
        pathsGetter.setSinkIndex(vertices.size() - 1);
        pathsGetter.getPaths();
        var x = pathsGetter.getNonTouchingLoops();
        try {
            var y = pathsGetter.calcDelta();
            System.out.println(y);
        }catch (Exception e){
            System.out.println("Error in calcDelta");
        }

        try {
            var z = pathsGetter.calcDeltas();
            System.out.println("calcDeltas::::::::::");
            System.out.println(Arrays.toString(z.toArray()));
        }catch (Exception e){
            System.out.println("Error in calcDeltasss");
        }

        try {
            var TF = pathsGetter.getTransferFunction();
            System.out.println("Transfer Function::::::::::");
            System.out.println(TF);
        }catch (Exception e){
            System.out.println("Error in calcDeltasss");
        }


        System.out.println(pathsGetter.getNonTouchingLoops());

    }

}