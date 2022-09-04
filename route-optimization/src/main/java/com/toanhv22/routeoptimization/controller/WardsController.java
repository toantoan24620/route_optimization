package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.response.WardsResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.WardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/wards")
public class WardsController {
    private final WardsService wardsService;
    private final ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<WardsResponse>>> findByDistrictCode(
            @RequestParam @NotNull String districtCode){
        return responseFactory.success(wardsService.findByDistrictCode(districtCode));
    }
}
