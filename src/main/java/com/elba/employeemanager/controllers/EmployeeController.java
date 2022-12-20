package com.elba.employeemanager.controllers;

import com.elba.employeemanager.common.ResponseObject;
import com.elba.employeemanager.models.ActiveAndInactiveUsers;
import com.elba.employeemanager.models.ViewUser;
import com.elba.employeemanager.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${base.url}/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/hello")
    public String hello(){
        return "Hello World!";
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadXlsxFile(@RequestParam String xlsxFile){
        return employeeService.saveEmployees(xlsxFile);
    }

    @GetMapping("/search/{search}")
    public ResponseEntity<?> search(@PathVariable String search){

        ResponseObject<List<ViewUser>> responseObject = employeeService.search(search);

        return ResponseEntity.status(responseObject.getStatus()).body(responseObject);
    }

    @GetMapping("/active-inactive")
    public ResponseEntity<?> getActiveAndInactiveUsers(){

        ResponseObject<ActiveAndInactiveUsers> responseObject = employeeService.getActiveAndInactiveUsers();

        return ResponseEntity.status(responseObject.getStatus()).body(responseObject);
    }

    @GetMapping("/users-asc-order")
    public ResponseEntity<?> getUsersAscending(){

        ResponseObject<List<ViewUser>> responseObject = employeeService.findUsersInAscendingOrder();

        return ResponseEntity.status(responseObject.getStatus()).body(responseObject);
    }

    @GetMapping("/grouped-by-department")
    public ResponseEntity<?> getUsersByDepartment(){

        employeeService.findGroupedUsersByDepartment();

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
