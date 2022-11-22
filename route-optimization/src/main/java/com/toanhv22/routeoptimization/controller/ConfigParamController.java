package com.toanhv22.routeoptimization.controller;

import com.toanhv22.routeoptimization.dto.request.ConfigParamRequest;
import com.toanhv22.routeoptimization.dto.response.ConfigParamResponse;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import com.toanhv22.routeoptimization.service.ConfigParamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("route-optimization/private/v1/config-param")
@Validated
public class ConfigParamController {
    private final ResponseFactory responseFactory;
    private final ConfigParamService configParamService;

    @GetMapping
    public ResponseEntity<GeneralResponse<List<ConfigParamResponse>>> findAll(){
        return responseFactory.success(configParamService.findAll());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GeneralResponse<ConfigParamResponse>> findById(@PathVariable String id){
        return responseFactory.success(configParamService.findById(id));
    }

    @PutMapping
    public ResponseEntity<GeneralResponse<ConfigParamResponse>> update(@RequestBody @Valid ConfigParamRequest configParamRequest){
        return responseFactory.success(configParamService.update(configParamRequest));
    }
}
