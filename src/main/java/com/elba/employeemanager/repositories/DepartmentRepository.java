package com.elba.employeemanager.repositories;

import com.elba.employeemanager.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}