package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

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

    @Column(name = "received_date")
    private LocalDateTime receivedDate;

    @Column(name = "move_time_to_node")
    private int moveTimeToNode;

    @Column(name = "distance_to_node")
    private double distanceToNode;
}
