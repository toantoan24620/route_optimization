package com.toanhv22.routeoptimization.factory.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ResponseStatusCode {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private final LocalDateTime responseTime = LocalDateTime.now();

    @Setter(AccessLevel.NONE)
    private String code;

    private String message;

    public void setCode(String appName, int code) {
        this.code = StringUtils.joinWith("-", StringUtils.upperCase(appName), StringUtils.leftPad(code + StringUtils.EMPTY, 4, "0"));
    }
}
