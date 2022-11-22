package com.toanhv22.routeoptimization.tabu;

import com.toanhv22.routeoptimization.config.AppConfig;
import com.toanhv22.routeoptimization.constant.ConfigParamEnum;
import com.toanhv22.routeoptimization.constant.OrdersStatusEnum;
import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.entity.CostMatrix;
import com.toanhv22.routeoptimization.entity.Orders;
import com.toanhv22.routeoptimization.entity.Schedule;
import com.toanhv22.routeoptimization.entity.ScheduleOrder;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.repository.*;
import com.toanhv22.routeoptimization.utils.ULID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Getter
@Setter
@Service
@RequiredArgsConstructor
public class TabuSearchSolver {
    private int noOfVehicles;
    private Node[] nodes;
    private int noOfCustomers;
    private int tabuHorizon;
    private int iterations;
    private VehicleTabu[] bestSolutionVehicles;

    private VehicleTabu[] vehicles;
    private int cost;
    private int bestSolutionCost;
    private int unloadTime;

    private final ConfigParamRepository configParamRepository;

    private final OrdersRepository ordersRepository;

    private final WarehouseRepository warehouseRepository;

    private final CostMatrixRepository costMatrixRepository;

    private final WardsRepository wardsRepository;

    private final ScheduleOrderRepository scheduleOrderRepository;

    private final ScheduleRepository scheduleRepository;

    private final AppConfig appConfig;

    private Map<String, Integer> moveTimeValues = new HashMap<>();

    @Transactional(rollbackFor = Exception.class)
    public void solve(String warehouseId, double capacity) {
        List<Orders> initOrders = ordersRepository.findByWarehouseIdAndStatus(warehouseId, OrdersStatusEnum.PENDING.toString());
        if(initOrders.isEmpty())
            throw new BusinessException(ResponseStatusEnum.WAREHOUSE_HAS_NOT_ORDERS);

        this.tabuHorizon = Integer.parseInt(configParamRepository.findById(ConfigParamEnum.TABU_HORIZON.toString()).get().getValue());
        this.iterations = Integer.parseInt(configParamRepository.findById(ConfigParamEnum.ITERATIONS.toString()).get().getValue());

        initSolution(warehouseId, capacity, initOrders);

        this.bestSolutionVehicles = new VehicleTabu[this.noOfVehicles];
        int maxTime = Integer.parseInt(configParamRepository.findById(ConfigParamEnum.WORKING_TIME.toString()).get().getValue());
        for (int i = 0; i < this.noOfVehicles; i++) {
            this.bestSolutionVehicles[i] = new VehicleTabu(capacity, warehouseId, maxTime);
        }

        ArrayList<Node> routesFrom;
        ArrayList<Node> routesTo;

        double movingNodeDemand = 0.0;
        double weightOfNodeMoved = 0.0;
        int vehIndexFrom, vehIndexTo;
        int bestNCost, neighborCost;

        int swapIndexA = -1, swapIndexB = -1, swapRouteFrom = -1, swapRouteTo = -1;
        int iterationNumber = 0;

        Map<String, Integer> tabuList = new HashMap<>();

        this.bestSolutionCost = this.cost;

        while (true) {
            bestNCost = Integer.MAX_VALUE;

            for (vehIndexFrom = 0; vehIndexFrom < this.vehicles.length; vehIndexFrom++) {
                routesFrom = this.vehicles[vehIndexFrom].routes;
                int routeFromLength = routesFrom.size();

                // lặp trên các node
                // chỉ tính các node khách hàng, không tính depot
                for (int i = 1; i < (routeFromLength - 1); i++) {
                    for (vehIndexTo = 0; vehIndexTo < this.vehicles.length; vehIndexTo++) {
                        routesTo = this.vehicles[vehIndexTo].routes;
                        int routeToLength = routesTo.size();

                        // thử switch node i ra sau node j, tức j -> i
                        // j < (routeToLength - 1), về kho rồi thì thôi
                        for (int j = 0; j < (routeToLength - 1); j++) {
                            movingNodeDemand = routesFrom.get(i).weight;

                            // j -> i
                            if (i == j || this.moveTimeValues.get(StringUtils.join(routesTo.get(j).id, routesFrom.get(i).id)) == null)
                                continue;
                            int moveTime = this.moveTimeValues.get(StringUtils.join(routesTo.get(j).id, routesFrom.get(i).id));
                            int backToDepotTime = this.moveTimeValues.get(StringUtils.join(routesFrom.get(i).id, warehouseId));

                            // nếu cùng xe, hoặc khác xe nhưng thoả mãn điều kiện thời gian và trọng lượng
                            if ((vehIndexFrom == vehIndexTo) || this.vehicles[vehIndexTo].isFit(movingNodeDemand, moveTime, unloadTime, backToDepotTime)) {
                                if (!((vehIndexFrom == vehIndexTo) && (j == i - 1))) { // tránh trường hợp switch nhưng không thay đổi về giải pháp, !(cùng xe và j là node trước i hoặc chính là 1 node)

                                    // thời gian di chuyển từ node i-1 -> node i
                                    int minusCost1 = this.moveTimeValues.get(StringUtils.join(routesFrom.get(i - 1).id, routesFrom.get(i).id));
                                    // thời gian di chuyển từ node i -> i + 1
                                    int minusCost2 = this.moveTimeValues.get(StringUtils.join(routesFrom.get(i).id, routesFrom.get(i + 1).id));
                                    // thời gian di chuyển từ node j -> j + 1
                                    int minusCost3 = this.moveTimeValues.get(StringUtils.join(routesTo.get(j).id, routesTo.get(j + 1).id));

                                    String idMove1 = StringUtils.join(routesFrom.get(i - 1).id, routesFrom.get(i + 1).id);
                                    String idMove2 = StringUtils.join(routesTo.get(j).id, routesFrom.get(i).id);
                                    String idMove3 = StringUtils.join(routesFrom.get(i).id, routesTo.get(j + 1).id);
                                    int addedCost1 = this.moveTimeValues.get(idMove1);
                                    int addedCost2 = this.moveTimeValues.get(idMove2);
                                    int addedCost3 = this.moveTimeValues.get(idMove3);

                                    // nếu việc di chuyển giữa các node trên là 1 tabu
                                    if ((tabuList.get(idMove1) != null && tabuList.get(idMove1) != 0)
                                            || (tabuList.get(idMove2) != null && tabuList.get(idMove2) != 0)
                                            || (tabuList.get(idMove3) != null && tabuList.get(idMove3) != 0)) {
                                        break;
                                    }

                                    // tính toán chi phí khoảng cách nếu switch node giữa các vehicle
                                    neighborCost = addedCost1 + addedCost2 + addedCost3 - minusCost1 - minusCost2 - minusCost3;

                                    // tìm trường hợp hoán đổi mà chi phí quãng đường là nhỏ nhất, có thể âm hoặc dương
                                    if (neighborCost < bestNCost) {
                                        bestNCost = neighborCost;
                                        swapIndexA = i;
                                        swapIndexB = j;
                                        swapRouteFrom = vehIndexFrom;
                                        swapRouteTo = vehIndexTo;
                                        weightOfNodeMoved = movingNodeDemand;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // tru di sau moi lan tim neighbor
            for (Map.Entry<String, Integer> entry : tabuList.entrySet()) {
                Integer value = entry.getValue();
                if (value > 0) {
                    String key = entry.getKey();
                    tabuList.put(key, value - 1);
                }
            }

            routesFrom = this.vehicles[swapRouteFrom].routes;
            routesTo = this.vehicles[swapRouteTo].routes;

            this.vehicles[swapRouteFrom].routes = null;
            this.vehicles[swapRouteTo].routes = null;

            Node swapNode = routesFrom.get(swapIndexA);

            String idNodeFromBefore = routesFrom.get(swapIndexA - 1).id;
            String idNodeFromAfter = routesFrom.get(swapIndexA + 1).id;
            String idNodeTo = routesTo.get(swapIndexB).id;
            String idNodeToAfter = routesTo.get(swapIndexB + 1).id;

            int moveTime1 = this.moveTimeValues.get(StringUtils.join(idNodeFromBefore, routesFrom.get(swapIndexA).id));
            int moveTime2 = this.moveTimeValues.get(StringUtils.join(routesFrom.get(swapIndexA).id,idNodeFromAfter));
            int moveTime3 = this.moveTimeValues.get(StringUtils.join(idNodeFromBefore,idNodeFromAfter));

            int moveTime4 = this.moveTimeValues.get(StringUtils.join(idNodeTo,routesFrom.get(swapIndexA).id));
            int moveTime5 = this.moveTimeValues.get(StringUtils.join(routesFrom.get(swapIndexA).id,idNodeToAfter));
            int moveTime6 = this.moveTimeValues.get(StringUtils.join(idNodeTo,idNodeToAfter));

            Random tabuRandom = new Random();
            // mục dích delay để các hoạt động này không hết hạn cùng lúc trong tabu list, nếu hết hạn cùng lúc thì có thể dẫn đến lặp lại giải pháp này.
            int randomDelay1 = tabuRandom.nextInt(5); // random 0 -> 4 + tabuHorizon, số vòng lặp mà hành động vẫn còn tồn tại trong tabu list
            int randomDelay2 = tabuRandom.nextInt(5);
            int randomDelay3 = tabuRandom.nextInt(5);

            // di chuyển này không mang lại hiệu quả tối ưu (do đã tìm được cách tối ưu hơn ở trên)
            tabuList.put(StringUtils.join(idNodeFromBefore, swapNode.id), this.tabuHorizon + randomDelay1);
            tabuList.put(StringUtils.join(swapNode.id, idNodeFromAfter), this.tabuHorizon + randomDelay2);
            tabuList.put(StringUtils.join(idNodeTo, idNodeToAfter), this.tabuHorizon + randomDelay3);

            routesFrom.remove(swapIndexA);

            if (swapRouteFrom == swapRouteTo) { // switch node tren cung 1 xe
                if (swapIndexA < swapIndexB) { // (i < j) swap x -> A -> B
                    routesTo.add(swapIndexB, swapNode); // thì chỉ cần add vào đúng index của j do đã xoá đi i ở trên rồi
                } else {
                    routesTo.add(swapIndexB + 1, swapNode); // add vào sau j
                }
            } else {
                routesTo.add(swapIndexB + 1, swapNode);
            }

            // cập nhật lại tuyến đường
            // trừ khối lượng và thời gian di chuyển + unload
            this.vehicles[swapRouteFrom].routes = routesFrom;
            this.vehicles[swapRouteFrom].setLoading(this.vehicles[swapRouteFrom].getLoading() - weightOfNodeMoved);
            this.vehicles[swapRouteFrom].setCurrentTime(this.vehicles[swapRouteFrom].getCurrentTime() - moveTime1 - moveTime2 + moveTime3 - unloadTime);

            this.vehicles[swapRouteTo].routes = routesTo;
            this.vehicles[swapRouteTo].setLoading(this.vehicles[swapRouteTo].getLoading() + weightOfNodeMoved);
            this.vehicles[swapRouteTo].setCurrentTime(this.vehicles[swapRouteTo].getCurrentTime() + moveTime4 + moveTime5 - moveTime6 + unloadTime);

            // bestNCost có thể là 1 số âm nên cost có thể < bestSolutionCost (bestNCost là chi phí tiết kiệm được sau khi thực hiện local search)
            // khả năng chấp nhận worst case :))
            this.cost += bestNCost;

            if (this.cost < this.bestSolutionCost) {
                iterationNumber = 0;
                System.out.println("Founded better solution!");
                this.saveBestSolution();
            } else {
                iterationNumber++;
                System.out.println(iterationNumber);
            }
            // thực hiện local search iterationNumber lần mà ko có cải thiện về kết quả thì dừng, cải thiện đươc thì tính tiếp: iterationNumber = 0
            if (iterations == iterationNumber) break;
        }
        // save data
        for (int i = 0; i < this.bestSolutionVehicles.length; i++) {
            if (this.bestSolutionVehicles[i].routes.size() > 0) {
                String scheduleId = ULID.nextULID();
                Schedule schedule = Schedule.builder()
                        .id(scheduleId)
                        .weight(this.bestSolutionVehicles[i].getLoading())
                        .moveTime(this.bestSolutionVehicles[i].getCurrentTime())
                        .warehouseId(warehouseId)
                        .status(OrdersStatusEnum.SCHEDULED.toString())
                        .build();
                scheduleRepository.save(schedule);
                for (int j = 1; j < this.bestSolutionVehicles[i].routes.size() - 1; j++) {
                    String sourceId = this.bestSolutionVehicles[i].routes.get(j-1).id;
                    String targetId = this.bestSolutionVehicles[i].routes.get(j).id;
                    CostMatrix costMatrix = costMatrixRepository.findByOrderSourceIdAndOrderTargetId(sourceId, targetId).get();
                    ScheduleOrder scheduleOrder = ScheduleOrder.builder()
                            .id(ULID.nextULID())
                            .scheduleId(scheduleId)
                            .orderId(this.bestSolutionVehicles[i].routes.get(j).id)
                            .sequence(j)
                            .status(OrdersStatusEnum.SCHEDULED.toString())
                            .moveTimeToNode(costMatrix.getMoveTime())
                            .distanceToNode(costMatrix.getDistance())
                            .build();
                    scheduleOrderRepository.save(scheduleOrder);
                }
            }
        }
        //change status all orders in warehouse
        List<Orders> orders = ordersRepository.findByWarehouseIdAndStatus(warehouseId, OrdersStatusEnum.PENDING.toString());
        orders.forEach(c -> c.setStatus(OrdersStatusEnum.SCHEDULED.toString()));
        ordersRepository.saveAll(orders);
    }

    public void initSolution(String warehouseId, double capacity, List<Orders> orders) {
        this.moveTimeValues = getCostValues();

        noOfCustomers = orders.size();
        noOfVehicles = orders.size();
        cost = 0;

        nodes = new Node[noOfCustomers];
        for (int i = 0; i < noOfCustomers; i++) {
            nodes[i] = Node.builder().id(orders.get(i).getId()).weight(orders.get(i).getWeight()).isRouted(false).build();
        }

        // working time each day
        int maxTime = Integer.parseInt(configParamRepository.findById(ConfigParamEnum.WORKING_TIME.toString()).get().getValue());
        vehicles = new VehicleTabu[this.noOfVehicles];
        for (int i = 0; i < this.noOfVehicles; i++) {
            vehicles[i] = new VehicleTabu(capacity, warehouseId, maxTime);
        }
        // end init data

        int candCost, endCost;
        int vehIndex = 0;
        this.unloadTime = Integer.parseInt(configParamRepository.findById(ConfigParamEnum.UNLOAD_TIME.toString()).get().getValue());

        while (unassignedCustomerExists(nodes)) {
            int custIndex = 0;
            Node candidate = null;
            int minCost = Integer.MAX_VALUE;

            // xuất phát từ nhà kho
            if (vehicles[vehIndex].routes.isEmpty()) {
                vehicles[vehIndex].addNode(Node.builder().id(warehouseId).weight(0).isRouted(false).build(), 0, 0);
            }

            // lặp trên tập khách hàng, tìm node có thời gian di chuyển ngắn nhất ngắn nhất từ xe có vehIndex đến đó.
            for (int i = 0; i < noOfCustomers; i++) {
                if (!nodes[i].isRouted) {
                    // thời gian di chuyển từ node hiện tại cho đến node đang xét.
                    int moveTime = moveTimeValues.get(vehicles[vehIndex].getCurrentLocation() + nodes[i].id);
                    int backToDepotTime = moveTimeValues.get(nodes[i].id + warehouseId);
                    if (vehicles[vehIndex].isFit(nodes[i].weight, moveTime, unloadTime, backToDepotTime)) {
                        candCost = moveTime;

                        if (minCost > candCost) {
                            minCost = candCost;
                            custIndex = i;
                            candidate = nodes[i];
                        }
                    }
                }
            }

            if (candidate == null) {
                // Không có một khách hàng nào phù hợp
                // Cần thêm xe
                if (vehIndex + 1 < vehicles.length) {
                    if (!vehicles[vehIndex].getCurrentLocation().equalsIgnoreCase(warehouseId)) {
                        // cho xe ve kho
                        // thời gian từ vị trí hiện tại về kho
                        endCost = moveTimeValues.get(vehicles[vehIndex].getCurrentLocation() + warehouseId);
                        vehicles[vehIndex].addNode(Node.builder().id(warehouseId).weight(0).isRouted(false).build(), endCost, 0);
                        this.cost += endCost;
                    }

                    vehIndex++;
                } else {
                    System.exit(0);
                }
            } else {
                vehicles[vehIndex].addNode(candidate, minCost, unloadTime);
                nodes[custIndex].isRouted = true;
                this.cost += minCost;
            }
        }

        // thời gian di chuyển từ vị trí hiện tại với xe hiện tại về kho.
        endCost = moveTimeValues.get(vehicles[vehIndex].getCurrentLocation() + warehouseId);
        vehicles[vehIndex].addNode(Node.builder().id(warehouseId).weight(0).isRouted(false).build(), endCost, 0);
        this.cost += endCost;
    }

    public Map<String, Integer> getCostValues() {
        List<CostMatrix> list = costMatrixRepository.findAll();
        Map<String, Integer> result = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            result.put(list.get(i).getOrderSourceId() + list.get(i).getOrderTargetId(), list.get(i).getMoveTime());
        }
        return result;
    }

    private boolean unassignedCustomerExists(Node[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (!nodes[i].isRouted)
                return true;
        }
        return false;
    }

    private void saveBestSolution() {
        this.bestSolutionCost = this.cost; // luu ket qua

        for (int j = 0; j < this.noOfVehicles; j++) {
            // clear het route
            this.bestSolutionVehicles[j].routes.clear();

            // add tuyến mới tối ưu hơn vừa tìm được vào bestSolutionVehicles
            if (!this.vehicles[j].routes.isEmpty()) {
                int routeSize = this.vehicles[j].routes.size();
                for (int k = 0; k < routeSize; k++) {
                    Node n = this.vehicles[j].routes.get(k);
                    this.bestSolutionVehicles[j].routes.add(n);
                }
                this.bestSolutionVehicles[j].setCurrentTime(this.vehicles[j].getCurrentTime());
                this.bestSolutionVehicles[j].setLoading(this.vehicles[j].getLoading());
            }
        }
    }
}
