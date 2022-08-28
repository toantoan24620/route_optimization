package com.toanhv22.routeoptimization.factory.response;

import com.toanhv22.routeoptimization.config.AppConfig;
import com.toanhv22.routeoptimization.constant.ResponseStatusEnum;
import com.toanhv22.routeoptimization.locale.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
@Slf4j
@RequiredArgsConstructor
public class ResponseFactory {
    private final AppConfig appConfig;
    private final Translator translator;

    public <T> ResponseEntity<GeneralResponse<T>> success(T data, String... values) {
        return ResponseEntity.status(ResponseStatusEnum.SUCCESS.getHttpCode())
                .body(buildGeneralResponse(data, ResponseStatusEnum.SUCCESS, values));
    }

    private <T> GeneralResponse<T> buildGeneralResponse(T data, ResponseStatusEnum status, String... values) {
        return buildGeneralResponse(data, parseResponseStatus(status.getCode(), values));
    }

    private <T> GeneralResponse<T> buildGeneralResponse(T data, ResponseStatusCode status) {
        GeneralResponse<T> generalResponse = new GeneralResponse<T>();
        generalResponse.setData(data);
        generalResponse.setStatus(status);
        return generalResponse;
    }

    private ResponseStatusCode parseResponseStatus(
            int code,
            String... values
    ) {
        ResponseStatusCode responseStatusCode = new ResponseStatusCode();
        responseStatusCode.setCode(appConfig.getAppName(), code);
        String message = StringUtils
                .firstNonBlank(format(translator.getLocaleMessage(code), values), responseStatusCode.getCode());
        responseStatusCode.setMessage(message);
        return responseStatusCode;
    }

    private String format(String template, String... args) {
        return StringEscapeUtils.unescapeJava(MessageFormat.format(template, (Object[]) args));
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(
            ResponseStatusEnum responseStatusEnum, String... values
    ) {
        return fail(null, responseStatusEnum, values);
    }

    public <T> ResponseEntity<GeneralResponse<T>> fail(T data, ResponseStatusEnum responseStatusEnum,
                                                       String... values) {
        return ResponseEntity.status(responseStatusEnum.getHttpCode())
                .body(buildGeneralResponse(data, responseStatusEnum, values));
    }
}
