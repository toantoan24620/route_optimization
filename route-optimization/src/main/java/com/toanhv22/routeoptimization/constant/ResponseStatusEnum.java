package com.toanhv22.routeoptimization.constant;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatusEnum {
    SUCCESS(0, HttpStatus.OK.value()),
    INTERNAL_SERVER_ERROR(9999, HttpStatus.INTERNAL_SERVER_ERROR.value()),
    BAD_REQUEST(9998, HttpStatus.BAD_REQUEST.value()),
    DATA_NOT_VALID(9997, HttpStatus.BAD_REQUEST.value()),
    ENTITY_DUPLICATED(9996, HttpStatus.OK.value()),
    ENTITY_NOT_FOUND(9995, HttpStatus.OK.value()),
    RESOURCE_LOCKED(9994, HttpStatus.OK.value()),
    FORBIDDEN(9993, HttpStatus.OK.value()),

    LIST_EMPTY(15, HttpStatus.OK.value()),
    UPLOAD_SUCCESS(16, HttpStatus.OK.value()),
    UPLOAD_FILE_DOCS_SUCCESS(25, HttpStatus.OK.value()),
    FILE_NOT_FOUND(17, HttpStatus.OK.value()),
    CANT_READ_FILE(18, HttpStatus.OK.value()),
    HANDLING_REQUEST_TIME_INVALID(19, HttpStatus.OK.value()),

    DUPLICATED_IDENTIFY_NUMBER(20, HttpStatus.OK.value());

    private final int code;
    private final int httpCode;

    ResponseStatusEnum(int code, int httpCode) {
        this.code = code;
        this.httpCode = httpCode;
    }
}
