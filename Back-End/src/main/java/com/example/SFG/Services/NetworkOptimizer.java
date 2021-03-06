package com.example.SFG.Services;

import com.example.SFG.Model.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NetworkOptimizer {
    private NetworkAnalyser pathsAndLoopsGetter;
    private List<Node> nodeList;
    @Autowired
    public NetworkOptimizer(NetworkAnalyser pathsAndLoopsGetter) {
        this.pathsAndLoopsGetter = pathsAndLoopsGetter;
        nodeList = new ArrayList<>();
    }

    public List<Node> optimizeNetwork(List<HashMap<String, String[]>> networkList){
        nodeList = new ArrayList<>();
        for(HashMap<String, String[]> map: networkList){
            for(String key : map.keySet()) nodeList.add(new Node(key));
            boolean found = false;
            for(Node n : nodeList){
                if(n.getSymbol().equals("Machine1")){
                    found = true;
                }
            }

            if(!found){
                nodeList.add(new Node("Machine1"));
            }
            int index = 0;
            for(String key : map.keySet()){
                Node node = nodeList.get(index++);
                Map<Node, String> toNeighbours = new HashMap<>();
                String[] arr = map.get(key);
                Node neighbour;
                for (String s : arr) {
                    String[] gain_neighbour = s.split(" Machine", 2);
                    gain_neighbour[1] = "Machine" + gain_neighbour[1];
                    neighbour = getNodeFromSymbol(gain_neighbour[1]);
                    if(gain_neighbour[0].equalsIgnoreCase("undefined")) continue;
                    if(toNeighbours.get(neighbour) == null || toNeighbours.get(neighbour).isEmpty() )
                        toNeighbours.put(neighbour, gain_neighbour[0]);
                    else
                        toNeighbours.put(neighbour, toNeighbours.get(neighbour) + " + " + gain_neighbour[0]);
                    System.out.println(node.getSymbol() + "===>"+toNeighbours.get(neighbour));
                }
                node.setToNeighbours(toNeighbours);
            }
        }

        for(Node node1: nodeList){
            System.out.println("from : " + node1.getSymbol());
            System.out.println("neighbours: ");
            for(Node key : node1.getToNeighbours().keySet()){
                System.out.println("to : " + key.getSymbol() + " with gain : " + node1.getToNeighbours().get(key));
            }
            System.out.println();
        }
        return nodeList;
    }

    public Node getNodeFromSymbol(String symbol){
        for(Node node: nodeList){
            if(Objects.equals(node.getSymbol(), symbol))
                return node;
        }
        return null;
    }

}
