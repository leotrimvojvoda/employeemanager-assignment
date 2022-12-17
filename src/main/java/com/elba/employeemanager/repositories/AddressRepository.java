package com.elba.employeemanager.repositories;

import com.elba.employeemanager.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}