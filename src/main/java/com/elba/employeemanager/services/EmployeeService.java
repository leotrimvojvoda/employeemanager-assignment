package com.elba.employeemanager.services;

import com.elba.employeemanager.common.Base64ToFile;
import com.elba.employeemanager.common.DateUtilities;
import com.elba.employeemanager.entities.Address;
import com.elba.employeemanager.entities.Department;
import com.elba.employeemanager.entities.User;
import com.elba.employeemanager.entities.UserDetails;
import com.elba.employeemanager.enums.UserState;
import com.elba.employeemanager.models.XlsxDto;
import com.elba.employeemanager.repositories.UserDetailsRepository;
import com.elba.employeemanager.repositories.UserRepository;
import com.poiji.bind.Poiji;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final Base64ToFile base64ToFile;
    private final UserRepository userRepository;

    private DateUtilities dateUtilities;
    private final UserDetailsRepository userDetailsRepository;

    public EmployeeService(Base64ToFile base64ToFile, UserRepository userRepository,
                           UserDetailsRepository userDetailsRepository) {
        this.base64ToFile = base64ToFile;
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
    }

    public ResponseEntity<?> saveEmployees(String xlsxFile) {

        File file = base64ToFile.getFile(xlsxFile, "employees.xlsx");

        try{

            List<XlsxDto> xlsxData = Poiji.fromExcel(file, XlsxDto.class);

            List<User> users = new ArrayList<>();

            for(XlsxDto employee : xlsxData){
                User user = new User();
                user.setFirstName(employee.getFullName().split(" ")[0]);
                user.setLastName(employee.getFullName().split(" ")[1]);
                user.setEmail(employee.getEmail());
                user.setUsername(employee.getUsername());

                UserDetails userDetails = new UserDetails();
                userDetails.setPhoneNumber(employee.getPhoneNumber());
                userDetails.setStartDaate(dateUtilities.getDate(employee.getStartDate()));
                userDetails.setEndDate(dateUtilities.getDate(employee.getEndDate()));

                Address address = new Address();
                address.setCity(employee.getAddress().split(",")[0]);
                address.setStreet(employee.getAddress().split(",")[1]);

                Department department = new Department();
                department.setDepartmentLeader(user);
                department.setDepartmentName(employee.getDepartmentName());
                department.setDepartmentPhoneNumber(employee.getDepartmentPhone());

                if(dateUtilities.getDate(employee.getEndDate()).isBefore(LocalDate.now()))
                    user.setState(UserState.INACTIVE);

                userDetails.setAddress(address);
                userDetails.setDepartment(department);
                user.setUserDetails(userDetails);

                users.add(user);
            }

            userRepository.saveAll(users);


        } catch (Exception e) {
            System.out.println("Error while parsing file");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (file.exists()) file.delete();
        }
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
