package com.elba.employeemanager.models;

import lombok.Data;

import java.util.List;

@Data
public class ActiveAndInactiveUsers {

    private List<ViewUser> active;
    private List<ViewUser> inactive;
}
