package com.elba.employeemanager.entities;

import com.elba.employeemanager.enums.DepartmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
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

    @Transient
    private String departmentLeaderName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "department_leader_id")
    private User departmentLeader;

    public Department(String departmentName, User departmentLeader) {
        this.departmentName = departmentName;
        this.departmentLeader = departmentLeader;
    }
}
