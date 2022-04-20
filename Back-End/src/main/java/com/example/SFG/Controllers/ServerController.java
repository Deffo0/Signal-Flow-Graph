package com.example.SFG.Controllers;

import com.example.SFG.model.Node;
import com.example.SFG.services.NetworkOptimizer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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

            List<Node> vertices =  optimizer.optimizeNetwork(newProductionNetwork);
            return ("Network is generated successfully");
        }catch (Exception e){
            e.printStackTrace();
            return(e.getMessage());
        }
    }








}
