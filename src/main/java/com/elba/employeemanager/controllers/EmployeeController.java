package com.elba.employeemanager.controllers;

import com.elba.employeemanager.common.ResponseObject;
import com.elba.employeemanager.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        ResponseObject responseObject = employeeService.search(search);

        return ResponseEntity.status(responseObject.getStatus()).body(responseObject);
    }



}
