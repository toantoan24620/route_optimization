//package com.toanhv22.routeoptimization.tabu;
//
//import com.google.maps.DistanceMatrixApi;
//import com.google.maps.DistanceMatrixApiRequest;
//import com.google.maps.GeoApiContext;
//import com.google.maps.PendingResult;
//import com.google.maps.model.DistanceMatrix;
//import com.toanhv22.routeoptimization.config.AppConfig;
//import com.toanhv22.routeoptimization.constant.ConfigParamEnum;
//import com.toanhv22.routeoptimization.constant.OrdersStatusEnum;
//import com.toanhv22.routeoptimization.entity.ConfigParam;
//import com.toanhv22.routeoptimization.entity.CostMatrix;
//import com.toanhv22.routeoptimization.entity.Orders;
//import com.toanhv22.routeoptimization.entity.Warehouse;
//import com.toanhv22.routeoptimization.repository.*;
//import com.toanhv22.routeoptimization.utils.ULID;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Getter
//@Setter
//@Service
//@RequiredArgsConstructor
//public class GreedySolver {
//    private int noOfVehicles;
//    private Node[] nodes;
//    private int noOfCustomers;
//    private VehicleTabu[] vehicles;
//
//    private double cost;
//
//    private final ConfigParamRepository configParamRepository;
//
//    private final OrdersRepository ordersRepository;
//
//    private final WarehouseRepository warehouseRepository;
//
//    private final CostMatrixRepository costMatrixRepository;
//
//    private final WardsRepository wardsRepository;
//
//    private final AppConfig appConfig;
//
//    private Map<String, Integer> moveTimeValues = new HashMap<>();
//
//    public Integer solver(String warehouseId, double capacity){
//        List<Orders> orders = ordersRepository.findByWarehouseIdAndStatus(warehouseId, OrdersStatusEnum.PENDING.toString());
//        Map<String,Integer> moveTimeValues = getCostValues();
//
//        noOfCustomers = orders.size();
//        noOfVehicles = orders.size();
//        cost = 0;
//
//        nodes = new Node[noOfCustomers];
//        for(int i = 0; i < noOfCustomers; i++){
//            nodes[i] = Node.builder().id(orders.get(i).getId()).weight(orders.get(i).getWeight()).isRouted(false).build();
//        }
//
////        // working time each day
//        int maxTime = Integer.parseInt(configParamRepository.findById(ConfigParamEnum.WORKING_TIME.toString()).get().getValue());
//        vehicles = new VehicleTabu[this.noOfVehicles];
//        for(int i = 0; i < this.noOfVehicles; i++){
//            vehicles[i] = new VehicleTabu(capacity, warehouseId, maxTime);
//        }
//        // end init data
//
//        int candCost, endCost;
//        int vehIndex = 0;
//        int unloadTime = Integer.parseInt(configParamRepository.findById(ConfigParamEnum.UNLOAD_TIME.toString()).get().getValue());
//
//        while(unassignedCustomerExists(nodes)){
//            int custIndex = 0;
//            Node candidate = null;
//            int minCost = Integer.MAX_VALUE;
//
//            if(vehicles[vehIndex].routes.isEmpty()){
//                vehicles[vehIndex].addNode(Node.builder().id(warehouseId).weight(0).isRouted(false).build(), 0);
//            }
//
//            // lặp trên tập khách hàng, tìm node có thời gian di chuyển ngắn nhất ngắn nhất từ xe có vehIndex đến đó.
//            for(int i = 0; i < noOfCustomers; i++){
//                if(!nodes[i].isRouted){
//                    // thời gian di chuyển từ node hiện tại cho đến node đang xét.
//                    int moveTime = moveTimeValues.get(vehicles[vehIndex].getCurrentLocation()+nodes[i].id);
////                    int moveTime = costMatrixRepository.findByOrderSourceIdAndOrderTargetId(vehicles[vehIndex].getCurrentLocation(), nodes[i].id).get().getMoveTime();
//                    if(vehicles[vehIndex].isFit(nodes[i].weight, moveTime,unloadTime)){
//                        candCost = moveTime;
//
//                        if(minCost > candCost){
//                            minCost = candCost;
//                            custIndex = i;
//                            candidate = nodes[i];
//                        }
//                    }
//                }
//            }
//
//            if(candidate == null){
//                // Không có một khách hàng nào phù hợp
//                // Cần thêm xe
//                if(vehIndex + 1 < vehicles.length){
//                    if(!vehicles[vehIndex].getCurrentLocation().equalsIgnoreCase(warehouseId)){
//                        // cho xe ve kho
//                        // thời gian từ vị trí hiện tại về kho
//                        endCost = moveTimeValues.get(vehicles[vehIndex].getCurrentLocation()+warehouseId);
////                        endCost = costMatrixRepository.findByOrderSourceIdAndOrderTargetId(vehicles[vehIndex].getCurrentLocation(),warehouseId).get().getMoveTime();
//                        vehicles[vehIndex].addNode(Node.builder().id(warehouseId).weight(0).isRouted(false).build(), endCost);
//                        this.cost += endCost;
//                    }
//
//                    vehIndex++;
//                }else{
//                    System.exit(0);
//                }
//            }else{
//                vehicles[vehIndex].addNode(candidate,minCost);
//                nodes[custIndex].isRouted = true;
//                this.cost += minCost;
//            }
//        }
//
////        // thời gian di chuyển từ vị trí hiện tại với xe hiện tại về kho.
////        endCost = costMatrixRepository.findByOrderSourceIdAndOrderTargetId(vehicles[vehIndex].getCurrentLocation(),warehouseId).get().getMoveTime();
//        endCost = moveTimeValues.get(vehicles[vehIndex].getCurrentLocation()+warehouseId);
//        vehicles[vehIndex].addNode(Node.builder().id(warehouseId).weight(0).isRouted(false).build(), endCost);
//        this.cost += endCost;
//
//        print();
//        return 1;
//    }
//
//    private boolean unassignedCustomerExists(Node[] nodes) {
//        for (int i = 0; i < nodes.length; i++) {
//            if (!nodes[i].isRouted)
//                return true;
//        }
//        return false;
//    }
//
//    public void print(){
//        System.out.println("===================================================");
//        System.out.println(noOfCustomers);
//
//        for(int j = 0; j < noOfCustomers; j++){
//            if(!vehicles[j].routes.isEmpty()){
//                System.out.print("Vehicle " + j + ": ");
//                int routeSize = vehicles[j].routes.size();
//
//                for(int k = 0; k < routeSize; k++){
//                    if(k == routeSize - 1){
//                        System.out.print(vehicles[j].routes.get(k).id);
//                    }else{
//                        System.out.println(vehicles[j].routes.get(k).id + "->");
//                    }
//                }
//                System.out.println();
//            }
//        }
//        System.out.println("\nBest value: " + this.cost + "\n");
//    }
//
//    public Map<String, Integer> getCostValues(){
//        List<CostMatrix> list = costMatrixRepository.findAll();
//        Map<String, Integer> result = new HashMap<>();
//
//        for(int i=0; i < list.size(); i++){
//            result.put(list.get(i).getOrderSourceId()+list.get(i).getOrderTargetId(),list.get(i).getMoveTime());
//        }
//        return result;
//    }
//
//    public void getCostMatrix(List<Orders> orders, Warehouse warehouse){
//        String pathWithType = wardsRepository.findByCode(warehouse.getWardsCode()).getPathWithType();
//        for(int i = 0; i < orders.size(); i++){
//            String targetId = orders.get(i).getId();
//            GeoApiContext context = new GeoApiContext.Builder().apiKey(appConfig.getApiKey()).build();
//
//            String[] origins = {warehouse.getAddressDetails() + ", " + pathWithType};
//            String[] destinations = {orders.get(i).getAddress()};
//
//            if(!costMatrixRepository.findByOrderSourceIdAndOrderTargetId(warehouse.getId(), targetId).isPresent()){
//                DistanceMatrixApiRequest request = DistanceMatrixApi.getDistanceMatrix(context,origins,destinations);
//
//                request.setCallback(new PendingResult.Callback<DistanceMatrix>() {
//                    @Override
//                    public void onResult(DistanceMatrix distanceMatrix) {
//                        double moveTime = (double)distanceMatrix.rows[0].elements[0].duration.inSeconds / 60;
//                        double distance = (double)distanceMatrix.rows[0].elements[0].distance.inMeters / 1000;
//                        CostMatrix costMatrix = CostMatrix.builder()
//                                .id(ULID.nextULID())
//                                .orderSourceId(warehouse.getId())
//                                .orderTargetId(targetId)
//                                .moveTime((int) Math.round(moveTime))
//                                .distance((double) Math.round(distance * 10) / 10)
//                                .build();
//                        costMatrixRepository.save(costMatrix);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//
//                    }
//                });
//            }
//
//            if(!costMatrixRepository.findByOrderSourceIdAndOrderTargetId(targetId, warehouse.getId()).isPresent()){
//                DistanceMatrixApiRequest request1 = DistanceMatrixApi.getDistanceMatrix(context,destinations,origins);
//                request1.setCallback(new PendingResult.Callback<DistanceMatrix>() {
//                    @Override
//                    public void onResult(DistanceMatrix distanceMatrix) {
//                        double moveTime = (double)distanceMatrix.rows[0].elements[0].duration.inSeconds / 60;
//                        double distance = (double)distanceMatrix.rows[0].elements[0].distance.inMeters / 1000;
//                        CostMatrix costMatrix = CostMatrix.builder()
//                                .id(ULID.nextULID())
//                                .orderSourceId(targetId)
//                                .orderTargetId(warehouse.getId())
//                                .moveTime((int) Math.round(moveTime))
//                                .distance((double) Math.round(distance * 10) / 10)
//                                .build();
//                        costMatrixRepository.save(costMatrix);
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//
//                    }
//                });
//            }
//        }
//
//        for(int i = 0; i < orders.size(); i++){
//            if(costMatrixRepository.findByOrderSourceId(orders.get(i).getId()).size() == 224){
//                continue;
//            }
//            for(int j = 0; j < orders.size(); j++){
//                if(i != j){
//                    String sourceId = orders.get(i).getId();
//                    String targetId = orders.get(j).getId();
//
//                    if(!costMatrixRepository.findByOrderSourceIdAndOrderTargetId(sourceId,targetId).isPresent()){
//                        GeoApiContext context = new GeoApiContext.Builder().apiKey(appConfig.getApiKey()).build();
//                        String[] origins = {orders.get(i).getAddress()};
//                        String[] destinations = {orders.get(j).getAddress()};
//                        DistanceMatrixApiRequest request = DistanceMatrixApi.getDistanceMatrix(context,origins,destinations);
//
//                        request.setCallback(new PendingResult.Callback<DistanceMatrix>() {
//                            @Override
//                            public void onResult(DistanceMatrix distanceMatrix) {
//                                double moveTime = (double)distanceMatrix.rows[0].elements[0].duration.inSeconds / 60;
//                                double distance = (double)distanceMatrix.rows[0].elements[0].distance.inMeters / 1000;
//                                CostMatrix costMatrix = CostMatrix.builder()
//                                        .id(ULID.nextULID())
//                                        .orderSourceId(sourceId)
//                                        .orderTargetId(targetId)
//                                        .moveTime((int) Math.round(moveTime))
//                                        .distance((double) Math.round(distance * 10) / 10)
//                                        .build();
//                                costMatrixRepository.save(costMatrix);
//                            }
//
//                            @Override
//                            public void onFailure(Throwable throwable) {
//
//                            }
//                        });
//                    }
//                }
//            }
//        }
//    }
//}
