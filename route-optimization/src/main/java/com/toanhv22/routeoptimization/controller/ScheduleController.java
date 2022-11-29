package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.request.ScheduleCreationRequest;
import com.toanhv22.routeoptimization.dto.request.ScheduleUpdateRequest;
import com.toanhv22.routeoptimization.dto.response.ScheduleResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.ScheduleService;
import com.toanhv22.routeoptimization.tabu.TabuSearchSolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/schedule")
public class ScheduleController {
    private final TabuSearchSolver tabuSearchSolver;
    private final ResponseFactory responseFactory;
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<GeneralResponse<Void>> create(@RequestBody ScheduleCreationRequest request){
        tabuSearchSolver.solve(request.getWarehouseId(), request.getCapacity());
        return responseFactory.success(null);
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<List<ScheduleResponse>>> findAll(){
        return responseFactory.success(scheduleService.findAll());
    }

    @GetMapping("/warehouse/{warehouseName}")
    public ResponseEntity<GeneralResponse<List<ScheduleResponse>>> findByWarehouseName(@PathVariable(name = "warehouseName") String warehouseName){
        return responseFactory.success(scheduleService.findByWarehouseName(warehouseName));
    }

    @GetMapping("/vehicle/{licensePlate}")
    public ResponseEntity<GeneralResponse<List<ScheduleResponse>>> findByVehicle(@PathVariable(name = "licensePlate") String licensePlate){
        return responseFactory.success(scheduleService.findByVehicle(licensePlate));
    }

    @GetMapping("/employee/{employeeCode}")
    public ResponseEntity<GeneralResponse<List<ScheduleResponse>>> findByEmployee(@PathVariable(name = "employeeCode") String employeeCode){
        return responseFactory.success(scheduleService.findByEmployee(employeeCode));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GeneralResponse<ScheduleResponse>> findById(@PathVariable(name = "id") String id){
        return responseFactory.success(scheduleService.findById(id));
    }

    @GetMapping("/status")
    public ResponseEntity<GeneralResponse<List<ScheduleResponse>>> findByStatusAndDeliveryDate(
            @RequestParam String deliveryDate
            ){
        return responseFactory.success(scheduleService.findByDeliveryDate(deliveryDate));
    }

    @PutMapping
    public ResponseEntity<GeneralResponse<Void>> update(@RequestBody ScheduleUpdateRequest request){
        scheduleService.update(request);
        return responseFactory.success(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<Void>> cancelationSchedule(@PathVariable String id){
        scheduleService.cancelationSchedule(id);
        return responseFactory.success(null);
    }
}
