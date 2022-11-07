package com.toanhv22.routeoptimization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseRequest {
    private String id;

    @NotNull(message = "Tên nhà kho không được để trống")
    private String name;

    @NotNull(message = "Mã nhà kho không được để trống")
    private String code;

    @NotNull(message = "Địa chỉ chi tiết không được để trống")
    private String addressDetails;

    @NotNull(message = "Phường/xã không được để trống")
    private String wardsCode;

    private String longitude;
    private String latitude;
    private Boolean status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedDate;
}
