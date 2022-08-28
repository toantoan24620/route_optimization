package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "warehouse")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Warehouse {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address_details")
    private String addressDetails;

    @Column(name = "wards_code")
    private String wardsCode;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;
}
