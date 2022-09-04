package com.toanhv22.routeoptimization.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "employee")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "staff_code")
    private String staffCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "identify_number")
    private String identifyNumber;

    @Column(name = "birthday")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @Column(name = "address")
    private String address;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "status")
    private Boolean status;
}
