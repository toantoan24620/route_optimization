package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "orders")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @Column(name = "can_receive_date")
    private LocalDateTime canReceiveDate;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "address_id")
    private String addressId;

    @Column(name = "warehouse_id")
    private String warehouseId;
}
