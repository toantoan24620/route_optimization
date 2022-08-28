package com.toanhv22.routeoptimization.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.toanhv22.routeoptimization.config.AppConfig;
import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.factory.response.GeneralResponse;
import com.toanhv22.routeoptimization.factory.response.ResponseFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseFactory responseFactory;
    private final AppConfig appConfig;
    private final ObjectMapper objectMapper;

    private <T> ResponseEntity<GeneralResponse<String>> internalServerErrorResponse(T content) {
        return responseFactory.fail(ResponseStatusEnum.INTERNAL_SERVER_ERROR,
                content instanceof String ? content.toString() : toJson(content),
                appConfig.getAppName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralResponse<String>> handleMethodArgumentNotValidExceptions(
            MethodArgumentNotValidException ex
    ) {
        log.info("handleMethodArgumentNotValidExceptions", ex);
        return badRequestResponse(ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toSet()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse<String>> handleExceptions(Exception ex) {
        log.info("handleExceptions", ex);
        return internalServerErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<GeneralResponse<String>> handleServletRequestBindingExceptions(
            ServletRequestBindingException ex
    ) {
        log.info("handleServletRequestBindingExceptions", ex);
        return badRequestResponse(ex.getMessage());
    }

    private <T> ResponseEntity<GeneralResponse<String>> badRequestResponse(T content) {
        return responseFactory.fail(ResponseStatusEnum.BAD_REQUEST,
                toJson(content),
                appConfig.getAppName());
    }

    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ignored) {
            return null;
        }
    }
}
