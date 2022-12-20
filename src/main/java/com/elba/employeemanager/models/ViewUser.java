package com.elba.employeemanager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ViewUser {

    private String fullName;

    private String manager;

    private String username;

    private String email;

    private String department;

    private String phoneNumber;

    private String address;

    private LocalDate startDate;

    private LocalDate endDate;

    private String departmentName;

    private String departmentLeader;

    private String departmentPhone;

    public ViewUser(String fullName, String manager, String username, String email, String phoneNumber, String address, LocalDate startDate, LocalDate endDate, String departmentName, String departmentLeader, String departmentPhone) {
        this.fullName = fullName;
        this.manager = manager;
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.startDate = startDate;
        this.endDate = endDate;
        this.departmentName = departmentName;
        this.departmentLeader = departmentLeader;
        this.departmentPhone = departmentPhone;
    }
}
