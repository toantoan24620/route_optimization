package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.response.CityProvinceResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.CityProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/city-province")
public class CityProvinceController {

    private final CityProvinceService cityProvinceService;
    private final ResponseFactory responseFactory;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<CityProvinceResponse>>> findAll(){
        return responseFactory.success(cityProvinceService.findAll());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<GeneralResponse<CityProvinceResponse>> findByCode(@PathVariable String code){
        return responseFactory.success(cityProvinceService.findByCode(code));
    }
}
