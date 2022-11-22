package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.response.DistrictResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/district")
public class DistrictController {
    private final ResponseFactory responseFactory;
    private final DistrictService districtService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<DistrictResponse>>> findByCityProvinceCode(
            @RequestParam @NotNull String cityProvinceCode){
        return responseFactory.success(districtService.findByCityProvinceCode(cityProvinceCode));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<GeneralResponse<DistrictResponse>> findByCode(@PathVariable String code){
        return responseFactory.success(districtService.findByCode(code));
    }
}
