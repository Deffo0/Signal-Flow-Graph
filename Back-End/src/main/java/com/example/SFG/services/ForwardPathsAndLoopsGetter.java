package com.example.SFG.services;

import com.example.SFG.model.Node;
import org.paukov.combinatorics3.Generator;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class ForwardPathsAndLoopsGetter {
    private List<Node> vertices;
    private int sourceIndex;
    private int sinkIndex;
    private boolean[] visited;
    private List<String> pathGains;
    private List<String> pathSymbols;

    private Map<List<String>, List<String>> finalSymbolsGains;
    private Map<List<String>, List<String>> finalloopsGains;

    boolean isNumeric;

    public ForwardPathsAndLoopsGetter() {
        vertices = new ArrayList<>();
        sinkIndex = 0;
        sourceIndex = 0;
        visited = new boolean[0];
        this.pathGains = new ArrayList<>();
        this.pathSymbols = new ArrayList<>();
        this.finalSymbolsGains = new HashMap<>();
        this.finalloopsGains = new HashMap<>();
        this.isNumeric = true;
    }

    public Map<List<String>, List<String>> getFinalloopsGains() {
        System.out.println(this.finalloopsGains);
        for(List<String> list : this.finalloopsGains.values()){
            Collections.sort(list);
        }

        for(List<String> key : this.finalloopsGains.keySet()){
            for(List<String> key2 : this.finalloopsGains.keySet()){
                if(key != key2){
                    if(this.finalloopsGains.get(key).equals(this.finalloopsGains.get(key2))){
                    }
                }
            }
        }
        System.out.println(this.finalloopsGains);
        return finalloopsGains;
    }

    public Map<List<String>, List<String>> getFinalSymbolsGains() {
        return finalSymbolsGains;
    }

    public void setVertices(List<Node> vertices) {
        this.vertices = vertices;
        visited = new boolean[vertices.size()];
        for(Node node: vertices){
            if(!isInteger(node.getSymbol())){
                isNumeric = false;
            }
        }
    }
    public void setSourceIndex(int sourceIndex) {this.sourceIndex = sourceIndex;}
    public void setSinkIndex(int sinkIndex) {this.sinkIndex = sinkIndex;}

    public boolean similarityCheck(ArrayList<String> list1, ArrayList<String> list2){
        boolean result = true;
        for(String elem: list1){
            if(!list2.contains(elem))
                result = false;
        }
        return  result;
    }
    public void getPaths(){
        this.pathSymbols.add(vertices.get(sourceIndex).getSymbol());
        System.out.println("source " + sourceIndex);
        dfs(this.sourceIndex);

        //Remove redundant loops
        int i=0;
        ArrayList<List<String>> tobeRemoved = new ArrayList<>();
        for(var key: finalloopsGains.keySet()){
            var values1 = (ArrayList<String>) ((ArrayList<String>) finalloopsGains.get(key)).clone();
            int j=0;
            for(var key2: finalloopsGains.keySet()){
                if(j>=i)
                    continue;
                var values2 = (ArrayList<String>) ((ArrayList<String>) finalloopsGains.get(key2)).clone();
                boolean areSimilar = false;
                if(i != j)
                    areSimilar = similarityCheck(values1, values2);
                if(areSimilar) {
                    tobeRemoved.add(key2);
                }
                j++;
            }
            i++;
        }

        for(var elem: tobeRemoved){
            this.finalloopsGains.remove(elem);
        }
        try {
            for(List<String> symbols : this.finalSymbolsGains.keySet()){
                System.out.println("Path: " );
                for(String symbol : symbols) System.out.println(symbol + ", ");
                System.out.println("\nGain: ");
                List<String> gains = this.finalSymbolsGains.get(symbols);
                for(String gain: gains) System.out.println(gain + ", ");
                System.out.println();
            }

            for(List<String> symbols : this.finalloopsGains.keySet()){
                System.out.println("Loop: " );
                for(String symbol : symbols) System.out.println(symbol + ", ");
                System.out.println("\nGain: ");
                List<String> gains = this.finalloopsGains.get(symbols);
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
            finalloopsGains.put(loopSymbols, loopGains);
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

    public Map<Integer, List<List<List<String>>>> getNonTouchingLoops() {
        return getNonTouchingLoops(this.finalloopsGains);
    }

    public Map<Integer, List<List<List<String>>>> getNonTouchingLoops(Map<List<String>, List<String>> finalloopssGains){


        Map<String, List<String>> loops = new HashMap<>();
        List<String> loopsNames = new ArrayList<>();
        List<List<List<String>>> loopSubsets = new ArrayList<>();

        int ctr = 0;
        for(List<String> key : finalloopssGains.keySet()){
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
                nonTouchGains.get(loopSubset.size()).get(nonTouchGains.get(loopSubset.size()).size() - 1).add(finalloopssGains.get(loops.get(loop)));

            }

        }
        return nonTouchGains;
    }

    public int findMultiplication(List<String> arr){
        int result = 1;
        for(String str: arr){
            result *= Integer.parseInt(str);
        }
        return result;
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }



    public String calcDelta(){
        var nonToucingGains = getNonTouchingLoops(this.finalloopsGains);
        int sign = -1;
        String delta = "1";
        List<List<String>> loopsGains = new ArrayList<>();
        for(List<List<List<String>>> type_i_nonTouching_loops: nonToucingGains.values()){
            List<String> loops_type_i_gains = new ArrayList<>();
            for(List<List<String>> set_of_loops: type_i_nonTouching_loops){
                String Multiplication = "";
                if(isNumeric){
                    int multiplication = 1;
                    for(List<String> loop: set_of_loops){
                        multiplication *= Integer.parseInt(calcGain(loop));
                    }
                    Multiplication = String.valueOf(multiplication);
                }else{
                    boolean flag = false;
                    for(List<String> loop: set_of_loops){
                        if(flag)
                            Multiplication = Multiplication.concat("*");
                        Multiplication = Multiplication.concat(calcGain(loop));
                        flag = true;
                    }
                }
                loops_type_i_gains.add(Multiplication);
            }
            loopsGains.add(loops_type_i_gains);
        }
        for(List<String> type: loopsGains){
            if(sign == 1)
                delta = delta.concat(" + ( ");
            else
                delta = delta.concat(" - ( " );
            for(int i = 0; i < type.size(); i++){
                delta = delta.concat(type.get(i));
                if(i != type.size() - 1){
                    delta = delta.concat(" + ");
                }
            }
            delta = delta.substring(0, delta.length());
            delta = delta.concat(" ) ");
            sign *= -1;
        }
        return delta;
    }

    private boolean haveCommon(ArrayList<String> arr1, ArrayList<String> arr2){
        ArrayList arr1_copy = new ArrayList();
        arr1_copy = (ArrayList) arr1.clone();
        ArrayList arr2_copy = new ArrayList();
        arr2_copy = (ArrayList) arr2.clone();
        arr1_copy.retainAll(arr2_copy);
        boolean result = (arr1_copy.size()==0)?false:true;
        return result;
    }
    public List<String> calcDeltas(){
        var nonTouchingGains = getNonTouchingLoops(this.finalloopsGains);
        List<String> deltas = new ArrayList<>();
        for(List<String> path : this.finalSymbolsGains.values()) {
            int sign = -1;
            String delta = "1";
            List<List<String>> loopsGains = new ArrayList<>();
            for(List<List<List<String>>> type_i_nonTouching_loops: nonTouchingGains.values()){
                List<String> loops_type_i_gains = new ArrayList<>();
                for(List<List<String>> set_of_loops: type_i_nonTouching_loops){
                    String Multiplication = "";
                    boolean haveCommon = false;
                    if(isNumeric){
                        int multiplication = 1;
                        for(List<String> loop: set_of_loops){
                            if(haveCommon((ArrayList<String>) loop,(ArrayList<String>) path)) {
                                haveCommon = true;
                                continue;
                            }
                            multiplication *= Integer.parseInt(calcGain(loop));
                        }
                        Multiplication = String.valueOf(multiplication);
                    }else{
                        boolean flag = false;
                        for(List<String> loop: set_of_loops){
                            if(haveCommon((ArrayList<String>) loop,(ArrayList<String>) path)) {
                                haveCommon = true;
                                continue;
                            }
                            if(flag)
                                Multiplication = Multiplication.concat("*");
                            Multiplication = Multiplication.concat(calcGain(loop));
                            flag = true;
                        }
                    }
                    if(!haveCommon)
                        loops_type_i_gains.add(Multiplication);
                }
                loopsGains.add(loops_type_i_gains);
            }
            for(List<String> type: loopsGains){
                if(type.isEmpty()){
                    continue;
                }
                if(sign == 1)
                    delta = delta.concat(" + ( ");
                else
                    delta = delta.concat(" - ( ");
                for(String term: type){
                    if(term != "1" && term != "1 - ") {
                        delta = delta.concat(term);
                        delta = delta.concat(" + ");
                    }
                }
                delta = delta.replaceAll("\\(1\\)", "");
                delta = delta.replaceAll("\\( 1 \\)", "");
                delta = delta.replaceAll("\\( 1\\)", "");
                delta = delta.replaceAll("\\(1 \\)", "");
                delta = delta.replaceAll("\\(1-\\)", "");
                delta = delta.replaceAll("\\( 1- \\)", "");
                delta = delta.replaceAll("\\( 1-\\)", "");
                delta = delta.replaceAll("\\(1- \\)", "");
                delta = delta.replaceAll("1-\\)", "1");
                delta = delta.substring(0, delta.length() - 1);
                if(delta.charAt(delta.length() - 1) == '+')
                    delta = delta.substring(0, delta.length() - 1);
                delta = delta.concat(" ) ");
                delta = delta.replaceAll("1-\\)", "1");
                sign *= -1;
            }
            deltas.add(delta);
        }
        return deltas;
    }

    public String getTransferFunction(){
        List<String> Ps = new ArrayList<>();
        List<String> deltas = new ArrayList<>();
        String delta;
        Ps = calcPs();
        delta = calcDelta();
        deltas = calcDeltas();
        String TF = "( ";
        for(int i=0; i<Ps.size(); i++){
            TF = TF.concat(Ps.get(i));
            if(deltas.get(i) != "1" && deltas.get(i) != "1 - " && deltas.get(i) != "" && deltas.get(i) != " ") {
                TF = TF.concat(" * ");
                TF = TF.concat(" ( ");
                TF = TF.concat(deltas.get(i));
                TF = TF.concat(" ) ");
            }
            if(i != Ps.size() - 1){
                TF = TF.concat(" + ");
            }
        }
        TF = TF.concat(" ) / ");
        TF = TF.concat(delta);
        System.out.println("TRANSFER FUNCTION: \n" + TF);
        String remove = "\\*\\(1\\)";
        TF = TF.replaceAll("\\*\\s*\\(1-\\)", "");
        TF = TF.replaceAll("\\*\\s*\\(1\\)", "");
        TF = TF.replaceAll("\\*\\s*\\)", "");
        TF = TF.replaceAll("\\*\\s*\\(1-\\)\\)", "");
        TF = TF.replaceAll("\\*\\s*\\(1\\)\\)", "");
        TF = TF.replaceAll("\\*\\s*\\)\\)", "");
        System.out.println("TRANSFER FUNCTION: \n" + TF);
        return TF;
    }

    public String calcGain(List<String> path){
        String gain;
        if(isNumeric) {
            int result = findMultiplication(path);
            gain = String.valueOf(result);
        }
        else{
            String TF;
            int intPart = 1;
            String strPart = "";
            for(var symbol: path){
                if(isInteger(symbol))
                    intPart *= Integer.parseInt(symbol);
                else
                    strPart = strPart.concat(symbol);

            }
            if(intPart == 1)
                gain = strPart;
            else
                gain = String.valueOf((Integer)intPart).concat(strPart);
        }
        return gain;
    }

    public List<String> calcPs(){
        List<String> Ps = new ArrayList<>();
        if(isNumeric)
            Ps = finalSymbolsGains.values().stream().map(i -> String.valueOf(findMultiplication(i))).collect(Collectors.toList());
        else{
            int intPart = 1;
            String strPart = "";
            for(var path: finalSymbolsGains.values()){
                Ps.add(calcGain(path));
            }
        }
        return Ps;
    }

}
