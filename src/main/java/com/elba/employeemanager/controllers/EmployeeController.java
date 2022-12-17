package com.elba.employeemanager.controllers;

import com.elba.employeemanager.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${base.url}/upload-employees-xlsx-file")
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

}
