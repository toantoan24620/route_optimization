package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "wards")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wards {
    @Id
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "slug")
    private String slug;

    @Column(name = "name_with_type")
    private String nameWithType;

    @Column(name = "path")
    private String path;

    @Column(name = "path_with_type")
    private String pathWithType;

    @Column(name = "district_code")
    private String districtCode;
}
