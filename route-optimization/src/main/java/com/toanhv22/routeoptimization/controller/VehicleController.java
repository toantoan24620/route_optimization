package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.request.VehicleRequest;
import com.toanhv22.routeoptimization.dto.response.VehicleResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/vehicles")
@Validated
public class VehicleController {

    private final ResponseFactory responseFactory;
    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<VehicleResponse>>> getAll(@RequestParam Boolean active){
        return responseFactory.success(vehicleService.getAll(active));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GeneralResponse<VehicleResponse>> getById(@PathVariable String id){
        return responseFactory.success(vehicleService.getById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GeneralResponse<List<VehicleResponse>>> getByName(@PathVariable String name){
        return responseFactory.success(vehicleService.getByName(name));
    }

    @GetMapping("/license-plate/{licensePlate}")
    public ResponseEntity<GeneralResponse<VehicleResponse>> getByLicensePlate(@PathVariable String licensePlate,@RequestParam Boolean active){
        return responseFactory.success(vehicleService.getByLicensePlate(licensePlate,active));
    }

    @GetMapping("/weight/{weight}")
    public ResponseEntity<GeneralResponse<List<VehicleResponse>>> getByWeightGreater(@PathVariable Integer weight){
        return responseFactory.success(vehicleService.getByWeightGreater(weight));
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<VehicleResponse>> create(@RequestBody @Valid VehicleRequest vehicleRequest){
        return responseFactory.success(vehicleService.create(vehicleRequest));
    }

    @PutMapping
    public ResponseEntity<GeneralResponse<VehicleResponse>> update(@RequestBody @Valid VehicleRequest vehicleRequest){
        return responseFactory.success(vehicleService.update(vehicleRequest));
    }

    @DeleteMapping
    public ResponseEntity<GeneralResponse<Void>> delete(@RequestParam @NotNull(message = "ID phương tiện không được để trống") String id){
        vehicleService.delete(id);
        return responseFactory.success(null);
    }
}
