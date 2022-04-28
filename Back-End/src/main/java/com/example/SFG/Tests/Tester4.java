package com.example.SFG.Tests;

import com.example.SFG.Model.Node;
import com.example.SFG.Services.NetworkAnalyser;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class Tester4 {
    private NetworkAnalyser pathsGetter;

    @Autowired
    public Tester4(NetworkAnalyser pathsGetter) {
        this.pathsGetter = pathsGetter;
    }

    public void test() {
        Map<Node, String> toNeighboursR = new HashMap<>();
        Map<Node, String> toNeighbours1 = new HashMap<>();
        Map<Node, String> toNeighbours2 = new HashMap<>();
        Map<Node, String> toNeighbours3 = new HashMap<>();
        Map<Node, String> toNeighboursC = new HashMap<>();

        Node nodeR = new Node("R");
        Node node1 = new Node("V1");
        Node node2 = new Node("V2");
        Node node3 = new Node("V3");
        Node nodeC = new Node("C");

        toNeighboursR.put(node1, "A");
        toNeighbours1.put(node2, "B");
        toNeighbours2.put(node1, "F");
        toNeighbours2.put(node3, "C");
        toNeighbours3.put(nodeC, "D");
        toNeighboursC.put(node3, "G");

        nodeR.setToNeighbours(toNeighboursR);
        node1.setToNeighbours(toNeighbours1);
        node2.setToNeighbours(toNeighbours2);
        node3.setToNeighbours(toNeighbours3);
        nodeC.setToNeighbours(toNeighboursC);

        List<Node> vertices = new ArrayList<>();

        vertices.add(nodeR);
        vertices.add(node1);
        vertices.add(node2);
        vertices.add(node3);
        vertices.add(nodeC);

        pathsGetter.setVertices(vertices);
        pathsGetter.setSourceIndex(0);
        pathsGetter.setSinkIndex(vertices.size() - 1);
        pathsGetter.getPaths();
        System.out.println("here we get the paths*****************");
        var forward = pathsGetter.getFinalSymbolsGains().values();
        System.out.println(Arrays.toString(forward.toArray()));
        System.out.println("***********************");
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