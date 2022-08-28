package com.toanhv22.routeoptimization.factory.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class GeneralResponse<T> {
    private ResponseStatusCode status;

    @JsonProperty("data")
    private T data;

    public GeneralResponse(T data) {
        this.data = data;
    }
}
