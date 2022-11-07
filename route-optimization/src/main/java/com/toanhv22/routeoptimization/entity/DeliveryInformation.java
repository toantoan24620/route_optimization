package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "delivery_information")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeliveryInformation {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "address_details")
    private String addressDetails;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "wards_code")
    private String wardsCode;
}
