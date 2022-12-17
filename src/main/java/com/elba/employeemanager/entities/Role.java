package com.elba.employeemanager.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

//TODO UNNECESSARY
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "role_name")
    private String name;

}
