package com.toanhv22.routeoptimization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {
    private String id;

    @NotNull(message = "Tên không được để trống")
    private String name;

    @NotNull(message = "Địa chỉ không được để trống")
    private String address;

    @NotNull(message = "Loại phương tiện không được để trống")
    private String vehicleType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Ngày sinh không được để trống")
    private LocalDate birthday;

    @NotNull(message = "Max nhân viên không được để trống")
    private String staffCode;

    @NotNull(message = "Số điện thoại không được để trống.")
    private String phoneNumber;

    @NotNull(message = "Giới tính không được để trống")
    private String gender;

    @NotNull(message = "Số CMT/CCCD không được để trống")
    private String identifyNumber;

    private Boolean status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedDate;
}
