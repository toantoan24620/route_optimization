package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "schedule")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "vehicle_id")
    private String vehicleId;

    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;
}
