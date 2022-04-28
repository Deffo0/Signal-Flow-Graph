package com.example.SFG.Model;

import java.util.HashMap;
import java.util.Map;

public class Node{
    private String symbol;
    Map<Node, String> toNeighbours;

    public Node(String symbol) {
        this.symbol = symbol;
        this.toNeighbours = new HashMap<>();
    }

    public String getSymbol() {return symbol;}
    public void setSymbol(String symbol) {this.symbol = symbol;}

    public Map<Node, String> getToNeighbours() {
        return toNeighbours;
    }

    public void setToNeighbours(Map<Node, String> toNeighbours) {
        this.toNeighbours = toNeighbours;
    }
}
