package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.response.WardsResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.WardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/wards")
public class WardsController {
    private final WardsService wardsService;
    private final ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<WardsResponse>>> findAll(){
        return responseFactory.success(wardsService.findAll());
    }

    @GetMapping("/district/{districtCode}")
    public ResponseEntity<GeneralResponse<List<WardsResponse>>> findByDistrictCode(
            @PathVariable String districtCode){
        return responseFactory.success(wardsService.findByDistrictCode(districtCode));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<GeneralResponse<WardsResponse>> findByCode(@PathVariable String code){
        return responseFactory.success(wardsService.findByCode(code));
    }
}
