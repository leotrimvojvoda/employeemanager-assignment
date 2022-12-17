package com.elba.employeemanager.repositories;

import com.elba.employeemanager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}