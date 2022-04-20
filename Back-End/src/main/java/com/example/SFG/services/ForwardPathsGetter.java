package com.example.SFG.services;

import com.example.SFG.model.Node;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ForwardPathsGetter {
    private List<Node> vertices;
    private int sourceIndex;
    private int sinkIndex;
    private boolean[] visited;
    private List<String> pathGains;
    private List<String> pathSymbols;

    private Map<List<String>, List<String>> finalSymbolsGains;
    private Map<List<String>, List<String>> finalloopssGains;

    public ForwardPathsGetter() {
        vertices = new ArrayList<>();
        sinkIndex = 0;
        sourceIndex = 0;
        visited = new boolean[0];
        this.pathGains = new ArrayList<>();
        this.pathSymbols = new ArrayList<>();
        this.finalSymbolsGains = new HashMap<>();
        this.finalloopssGains = new HashMap<>();
    }

    public void setVertices(List<Node> vertices) {
        this.vertices = vertices;
        visited = new boolean[vertices.size()];
    }
    public void setSourceIndex(int sourceIndex) {this.sourceIndex = sourceIndex;}
    public void setSinkIndex(int sinkIndex) {this.sinkIndex = sinkIndex;}

    public void getPaths(){
        this.pathSymbols.add(vertices.get(sourceIndex).getSymbol());
        System.out.println("source " + sourceIndex);
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

            for(List<String> symbols : this.finalloopssGains.keySet()){
                System.out.println("Loop: " );
                for(String symbol : symbols) System.out.println(symbol + ", ");
                System.out.println("\nGain: ");
                List<String> gains = this.finalloopssGains.get(symbols);
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
            //There is loop
            List<String> loopGains = new ArrayList<>();
            List<String> loopSymbols = new ArrayList<>();
            var top = pathSymbols.get(pathSymbols.size() - 1);
            loopSymbols.add(top);
            int pos1 = pathSymbols.size() - 1;
            int pos2 = pathGains.size();
            do{
                pos1--;
                pos2--;
                loopSymbols.add(pathSymbols.get(pos1));
                loopGains.add(pathGains.get(pos2));
            }while(pathSymbols.get(pos1) != top);
            finalloopssGains.put(loopSymbols, loopGains);
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
            this.pathSymbols.add( neighbour.getSymbol());
            this.pathGains.add(vertices.get(startIndex).getToNeighbours().get(neighbour));
            dfs(vertices.indexOf(neighbour));
        }
        visited[startIndex] = false;
        if(startIndex != this.sourceIndex){
            this.pathGains.remove(this.pathGains.size() - 1);
            this.pathSymbols.remove(this.pathSymbols.size() - 1);
        }
    }

    public Map<Integer, List<List<List<String>>>> getNonTouchingLoops(){


        Map<String, List<String>> loops = new HashMap<>();
        List<String> loopsNames = new ArrayList<>();
        List<List<List<String>>> loopSubsets = new ArrayList<>();

        int ctr = 0;
        for(List<String> key : this.finalloopssGains.keySet()){
            loops.put("L".concat(Integer.toString(ctr + 1)), key);
            loopsNames.add("L".concat(Integer.toString(ctr + 1)));
            ctr++;
        }



        for(int i = 1; i <= 5; i++){
            loopSubsets.add(Generator.combination(loopsNames).simple(i).stream().collect(Collectors.toList()));
        }

        List<List<String>> nonTouchLoop = new ArrayList<>();
        for(List<List<String>> listSubset : loopSubsets){
            for(List<String> subset : listSubset){
                Set<String> subSetsNodes = new HashSet<>();
                boolean nonTouch = true;
                for(String loop : subset){
                    for(int i = 0; i < loops.get(loop).size() - 1; i++){
                        nonTouch = subSetsNodes.add(loops.get(loop).get(i));
                        if(!nonTouch){
                            break;
                        }
                    }
                    if(!nonTouch){
                        break;
                    }
                }
                if(nonTouch){
                    nonTouchLoop.add(subset);
                }
            }
        }
        Map<Integer, List<List<List<String>>>> nonTouchGains = new HashMap<>();
        for(List<String> loopSubset : nonTouchLoop){

            System.out.println(loopSubset);

            nonTouchGains.putIfAbsent(loopSubset.size(), new ArrayList<>());
            nonTouchGains.get(loopSubset.size()).add(new ArrayList<>());

            for(String loop : loopSubset){
                nonTouchGains.get(loopSubset.size()).get(nonTouchGains.get(loopSubset.size()).size() - 1).add(this.finalloopssGains.get(loops.get(loop)));

            }

        }
        return nonTouchGains;
    }
}
