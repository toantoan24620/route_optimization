package com.toanhv22.routeoptimization.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Column(name = "size")
    private Double size;

    @Column(name = "status")
    private String status;

    @Column(name = "note")
    private String note;

    @Column(name = "total_money")
    private Double totalMoney;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @CreationTimestamp
    @Column(name = "created_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime updatedDate;

    @Column(name = "finish_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime finishDate;
}
