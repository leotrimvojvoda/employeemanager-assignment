package com.elba.employeemanager.services;

import com.elba.employeemanager.common.ResponseObject;
import com.elba.employeemanager.models.ActiveAndInactiveUsers;
import com.elba.employeemanager.models.ViewUser;
import org.junit.jupiter.api.Test;;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    EmployeeService employeeService;


    @Test
    void getActiveUsers() {
        ResponseObject<ActiveAndInactiveUsers> inactiveUsersResponseObject =  employeeService.getActiveAndInactiveUsers();

        ActiveAndInactiveUsers activeAndInactiveUsers = inactiveUsersResponseObject.getData();

        assertEquals(3, activeAndInactiveUsers.getActive().size());
    }

    @Test
    void getAscendingUsers() {
        ResponseObject<List<ViewUser>> inactiveUsersResponseObject =  employeeService.findUsersInAscendingOrder();

        List<ViewUser> users = inactiveUsersResponseObject.getData();

        assertTrue(users.get(0).getFullName().startsWith("A"));
    }
}