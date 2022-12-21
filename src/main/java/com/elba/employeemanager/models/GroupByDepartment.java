package com.elba.employeemanager.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GroupByDepartment {

    private String department;

    private String employeesLastName;

    private List<String> employeeLastNamesList;

    public GroupByDepartment(String department, String employeesLastName) {
        this.department = department;
        this.employeesLastName = employeesLastName;
    }
}
