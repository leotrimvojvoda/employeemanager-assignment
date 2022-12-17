package com.elba.employeemanager.entities;

import com.elba.employeemanager.enums.DepartmentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_phone_number")
    private String departmentPhoneNumber;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User departmentLeader;




}
