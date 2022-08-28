package com.toanhv22.routeoptimization.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "district")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class District {
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

    @Column(name = "city_province_code")
    private String cityProvinceCode;
}
