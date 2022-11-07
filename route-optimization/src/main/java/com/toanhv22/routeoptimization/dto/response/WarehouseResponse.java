package com.toanhv22.routeoptimization.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {
    private String id;
    private String name;
    private String code;
    private String addressDetails;
    private String wardsCode;
    private String longitude;
    private String latitude;
    private String pathWithType;
    private Boolean status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedDate;
}
