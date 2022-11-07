package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "cost_matrix")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CostMatrix {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "order_source_id")
    private String orderSourceId;

    @Column(name = "order_target_id")
    private String orderTargetId;

    @Column(name = "move_time")
    private Integer moveTime;

    @Column(name = "distance")
    private Double distance;
}
