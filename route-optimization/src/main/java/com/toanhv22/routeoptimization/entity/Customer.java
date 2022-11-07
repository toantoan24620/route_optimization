package com.toanhv22.routeoptimization.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Table(name = "customer")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "username")
    private String username;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birthday")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    @Column(name = "gender")
    private String gender;
}
