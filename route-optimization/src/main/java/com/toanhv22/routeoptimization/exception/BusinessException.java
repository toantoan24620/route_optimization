package com.toanhv22.routeoptimization.exception;

import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final String[] args;
    private final ResponseStatusEnum responseStatusEnum;

    public BusinessException(ResponseStatusEnum responseStatusEnum, String... args) {
        super(responseStatusEnum.name());
        this.args = args;
        this.responseStatusEnum = responseStatusEnum;
    }
}
