package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "Schedule_order")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleOrder {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "schedule_id")
    private String scheduleId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "status")
    private String status;
}
