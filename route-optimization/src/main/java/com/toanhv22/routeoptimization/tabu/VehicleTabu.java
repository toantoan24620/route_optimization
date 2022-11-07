package com.toanhv22.routeoptimization.tabu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class VehicleTabu {
    public ArrayList<Node> routes = new ArrayList<>();
    private double capacity;
    private double loading;
    private String currentLocation;

    private int maxTime;
    private int currentTime;

    public VehicleTabu(double capacity, String warehouseId, int maxTime){
         this.capacity = capacity;
         this.loading = 0;
         this.currentLocation = warehouseId;
         this.currentTime = 0;
         this.maxTime = maxTime;
    }

    public void addNode(Node node, int moveTime, int unloadTime){
        routes.add(node);
        this.loading += node.weight;
        this.currentLocation = node.id;
        this.currentTime += (moveTime + unloadTime);
    }

    public boolean isFit(double weight, int moveTime, int unloadTime, int backToDepotTime){
        return (loading + weight <= capacity) && (currentTime + moveTime + unloadTime + backToDepotTime <= maxTime);
    }
}
