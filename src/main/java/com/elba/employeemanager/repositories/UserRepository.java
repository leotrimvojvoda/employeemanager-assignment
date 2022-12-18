package com.elba.employeemanager.repositories;

import com.elba.employeemanager.entities.User;
import com.elba.employeemanager.models.ViewUser;
import com.elba.employeemanager.models.XlsxDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {


    @Query("SELECT new com.elba.employeemanager.models.ViewUser(" +
            " concat(u.firstName,'', u.lastName) ," +
            " concat(u.manager.firstName, ' ', u.manager.lastName), " +
            " u.username, " +
            " u.email, " +
            " ud.phoneNumber," +
            " concat(a.city,', ', a.street)," +
            " ud.startDaate, " +
            " ud.endDate, " +
            " d.departmentName, " +
            " concat(d.departmentLeader.firstName, ' ',d.departmentLeader.lastName)," +
            " d.departmentPhoneNumber )" +
            " FROM User u" +
            " JOIN UserDetails ud on u.userDetails.id = ud.id" +
            " JOIN Address a on ud.address.id = a.id"+
            " JOIN Department d on ud.department.id = d.id" +
            " WHERE u.firstName LIKE LOWER(:search) OR u.lastName like LOWER(:search)" +
            " OR LOWER(CONCAT(u.firstName, u.lastName)) LIKE LOWER(:search)" +
            " OR u.email LIKE LOWER(:search) ")
    List<com.elba.employeemanager.models.ViewUser> searchUsersByNameAndEmail(@Param("search") String search);


}