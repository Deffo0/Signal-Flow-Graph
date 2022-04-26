package com.example.SFG.Controllers;

import com.example.SFG.model.Node;
import com.example.SFG.services.ForwardPathsAndLoopsGetter;
import com.example.SFG.services.NetworkOptimizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RestController
@CrossOrigin("http://localhost:4200")
public class ServerController {
    ArrayList<HashMap<String, String[]>> newProductionNetwork;
    private NetworkOptimizer optimizer;


    @Autowired
    public ServerController(NetworkOptimizer optimizer) {
        this.newProductionNetwork = new ArrayList<>();
        this.optimizer = optimizer;
    }

    public String[] convertToArray(ArrayList<String> list){
        String[] arr = new String[list.size()];
        for(int i=0; i<list.size(); i++){
            arr[i] = list.get(i);
        }
        return arr;
    }

    @PostMapping("/generateNetwork")
    String generateNetwork(@RequestBody String productionNetwork){
        System.out.println("INSIDE GENERATE NETWORK");
        try {
            if (this.newProductionNetwork.size() > 1) {
                this.newProductionNetwork = new ArrayList<>();
            }

            TypeFactory factory = TypeFactory.defaultInstance();
            ObjectMapper map = new ObjectMapper();
            this.newProductionNetwork.add(map.readValue(productionNetwork, new TypeReference<HashMap<String, String[]>>() {}));
            return "NETWORK GENERATED SUCCESSFULLY";

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getResult")
    String[][] getResult(){
        System.out.println("INSIDE GET RESULT");
        try {
            ForwardPathsAndLoopsGetter getter = new ForwardPathsAndLoopsGetter();
            List<String> forwardPathsGains = new ArrayList<String>();
            List<String> loopsGains = new ArrayList<>();
            List<String> nonTouchingRETURNED = new ArrayList<>();
            List<String> deltas = new ArrayList<>();
            List<String> TF = new ArrayList<>();
            List<Node> vertices =  optimizer.optimizeNetwork(newProductionNetwork);
            getter.setVertices(vertices);
            getter.setSourceIndex(0);
            getter.setSinkIndex(vertices.size() - 1);
            getter.getPaths();
            for(List<String> path: getter.getFinalSymbolsGains().values()){
                String pathGain = getter.calcGain(path);
                forwardPathsGains.add(pathGain);
            }
            for(List<String> loop: getter.getFinalloopsGains().values()){
                String loopGain = getter.calcGain(loop);
                forwardPathsGains.add(loopGain);
            }
            var nonTouching = getter.getNonTouchingLoops();
            for(List<List<List<String>>> nonTouchingType: nonTouching.values()){
                List<String> arr1 = new ArrayList<>();
                for(List<List<String>> nonTouching_set: nonTouchingType){
                    List<String> arr2 = new ArrayList<>();
                    for(List<String> nonTouchingloop: nonTouching_set){
                        String gainOfloop = getter.calcGain(nonTouchingloop);
                        arr2.add(gainOfloop);
                    }
                    arr1.add(Arrays.toString(arr2.toArray()));
                }
                nonTouchingRETURNED.add(Arrays.toString(arr1.toArray()));
            }
            deltas = getter.calcDeltas();
            TF.add(getter.getTransferFunction());
            System.out.println(Arrays.toString(nonTouchingRETURNED.toArray()));
            String[][] Result = new String[5][];
            Result[0] = convertToArray((ArrayList<String>) forwardPathsGains);
            Result[1] = convertToArray((ArrayList<String>) loopsGains);
            Result[2] = convertToArray((ArrayList<String>) nonTouchingRETURNED);
            Result[3] = convertToArray((ArrayList<String>) deltas);
            Result[4] =  convertToArray((ArrayList<String>) TF);
            return Result;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}