package com.example.sgf.service;

import com.example.sgf.model.Node;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ForwardPathsGetter {
    private List<Node> vertices;
    private int sourceIndex;
    private int sinkIndex;
    private boolean[] visited;
    private List<String> pathGains;
    private List<String> pathSymbols;
    private Map<List<String>, List<String>> finalSymbolsGains;

    public ForwardPathsGetter() {
        vertices = new ArrayList<>();
        sinkIndex = 0;
        sourceIndex = 0;
        visited = new boolean[0];
        this.pathGains = new ArrayList<>();
        this.pathSymbols = new ArrayList<>();
        this.finalSymbolsGains = new HashMap<>();
    }

    public void setVertices(List<Node> vertices) {
        this.vertices = vertices;
        visited = new boolean[vertices.size()];
    }
    public void setSourceIndex(int sourceIndex) {this.sourceIndex = sourceIndex;}
    public void setSinkIndex(int sinkIndex) {this.sinkIndex = sinkIndex;}

    public void getPaths(){
        this.pathSymbols.add(vertices.get(sourceIndex).getSymbol());
        dfs(this.sourceIndex);
        try {
            for(List<String> symbols : this.finalSymbolsGains.keySet()){
                System.out.println("Path: " );
                for(String symbol : symbols) System.out.println(symbol + ", ");
                System.out.println("\nGain: ");
                List<String> gains = this.finalSymbolsGains.get(symbols);
                for(String gain: gains) System.out.println(gain + ", ");
                System.out.println();
            }
        }
        catch (Exception e){
            System.out.println("No paths");
        }

    }

    private void dfs(int startIndex){
        if(visited[startIndex]) {
            this.pathGains.remove(this.pathGains.size() - 1);
            this.pathSymbols.remove(this.pathSymbols.size() - 1);
            return;
        }
        visited[startIndex] = true;
        if(startIndex == this.sinkIndex){
            List<String> paths = new ArrayList<>(this.pathSymbols);
            List<String> gains = new ArrayList<>(this.pathGains);
            this.finalSymbolsGains.put(paths, gains);
        }
        for(Node neighbour: vertices.get(startIndex).getToNeighbours().keySet()){
            this.pathSymbols.add(neighbour.getSymbol());
            this.pathGains.add(vertices.get(startIndex).getToNeighbours().get(neighbour));
            dfs(vertices.indexOf(neighbour));
        }
        visited[startIndex] = false;
        if(startIndex != this.sourceIndex){
            this.pathGains.remove(this.pathGains.size() - 1);
            this.pathSymbols.remove(this.pathSymbols.size() - 1);
        }
    }
}
