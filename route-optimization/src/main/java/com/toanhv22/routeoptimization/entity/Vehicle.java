package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "vehicle")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "type")
    private String type;

    @Column(name = "license_plate")
    private String licensePlate;

    @Column(name = "average_fuel_consumption")
    private Double averageFuelConsumption;

    @Column(name = "capacity_weight")
    private Integer capacityWeight;

    @Column(name = "capacity_volume")
    private Double capacityVolume;

    @Column(name = "shipped")
    private Boolean shipped;

    @Column(name = "warehouse_id")
    private String warehouseId;
}
