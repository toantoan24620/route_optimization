package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.request.EmployeeRequest;
import com.toanhv22.routeoptimization.dto.request.WarehouseRequest;
import com.toanhv22.routeoptimization.dto.response.EmployeeResponse;
import com.toanhv22.routeoptimization.dto.response.WarehouseResponse;
import com.toanhv22.routeoptimization.entity.Warehouse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/warehouse")
@Validated
public class WarehouseController {
    private final WarehouseService warehouseService;
    private final ResponseFactory responseFactory;

    @PostMapping
    public ResponseEntity<GeneralResponse<WarehouseResponse>> createWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest){
        return responseFactory.success(warehouseService.create(warehouseRequest));
    }

    @PutMapping
    public ResponseEntity<GeneralResponse<WarehouseResponse>> updateWarehouse(@RequestBody @Valid WarehouseRequest warehouseRequest){
        return responseFactory.success(warehouseService.update(warehouseRequest));
    }

    @GetMapping
    public ResponseEntity<GeneralResponse<List<WarehouseResponse>>> findAll(@RequestParam Boolean active){
        return responseFactory.success(warehouseService.findAll(active));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GeneralResponse<WarehouseResponse>> findById(@PathVariable String id, @RequestParam Boolean active){
        return responseFactory.success(warehouseService.findById(id,active));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponse<Void>> deleteById(@PathVariable String id){
        warehouseService.delete(id);
        return responseFactory.success(null);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GeneralResponse<List<WarehouseResponse>>> findByName(@PathVariable String name, @RequestParam Boolean active){
        return responseFactory.success(warehouseService.findByName(name, active));
    }

    @GetMapping("/area/{area}")
    public ResponseEntity<GeneralResponse<List<WarehouseResponse>>> findByArea(@PathVariable String area, @RequestParam Boolean active){
        return responseFactory.success(warehouseService.findByArea(area, active));
    }
}
