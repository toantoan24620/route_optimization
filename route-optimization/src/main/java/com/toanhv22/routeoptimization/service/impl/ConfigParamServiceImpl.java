package com.toanhv22.routeoptimization.service.impl;

import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.dto.request.ConfigParamRequest;
import com.toanhv22.routeoptimization.dto.response.ConfigParamResponse;
import com.toanhv22.routeoptimization.entity.ConfigParam;
import com.toanhv22.routeoptimization.exception.BusinessException;
import com.toanhv22.routeoptimization.mapper.ConfigParamMapper;
import com.toanhv22.routeoptimization.repository.ConfigParamRepository;
import com.toanhv22.routeoptimization.service.ConfigParamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfigParamServiceImpl implements ConfigParamService {

    private final ConfigParamMapper configParamMapper;
    private final ConfigParamRepository configParamRepository;

    // find all
    @Override
    public List<ConfigParamResponse> findAll() {
        return configParamRepository.findAll().stream().map(configParamMapper::entityToResponse).collect(Collectors.toList());
    }

    //find By Id
    @Override
    public ConfigParamResponse findById(String id) {
        Optional<ConfigParam> configParam = configParamRepository.findById(id);

        if(!configParam.isPresent()){
            throw new BusinessException(ResponseStatusEnum.ENTITY_NOT_FOUND,"cấu hình", "ID: " + id);
        }
        return configParamMapper.entityToResponse(configParam.get());
    }

    @Override
    public ConfigParamResponse update(ConfigParamRequest configParamRequest) {
        ConfigParam configParam = configParamMapper.requestToEntity(configParamRequest);

        return configParamMapper.entityToResponse(configParamRepository.save(configParam));
    }
}
