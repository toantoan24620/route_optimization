package com.toanhv22.routeoptimization.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    private String staffCode;
    private String phoneNumber;
    private String gender;
    private String identifyNumber;
    private Boolean status;
}
