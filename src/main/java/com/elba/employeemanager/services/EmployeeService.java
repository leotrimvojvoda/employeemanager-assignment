package com.elba.employeemanager.services;

import com.elba.employeemanager.common.Base64ToFile;
import com.elba.employeemanager.common.DateUtilities;
import com.elba.employeemanager.common.ResponseObject;
import com.elba.employeemanager.common.Utilities;
import com.elba.employeemanager.entities.Address;
import com.elba.employeemanager.entities.Department;
import com.elba.employeemanager.entities.User;
import com.elba.employeemanager.entities.UserDetails;
import com.elba.employeemanager.enums.UserState;
import com.elba.employeemanager.models.ActiveAndInactiveUsers;
import com.elba.employeemanager.models.GroupByDepartment;
import com.elba.employeemanager.models.ViewUser;
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
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final Base64ToFile base64ToFile;
    private final UserRepository userRepository;
    private final DateUtilities dateUtilities;

    private final Utilities utilities;
    private final UserDetailsRepository userDetailsRepository;

    public EmployeeService(Base64ToFile base64ToFile, UserRepository userRepository,
                           DateUtilities dateUtilities, Utilities utilities,
                           UserDetailsRepository userDetailsRepository) {
        this.base64ToFile = base64ToFile;
        this.userRepository = userRepository;
        this.dateUtilities = dateUtilities;
        this.utilities = utilities;
        this.userDetailsRepository = userDetailsRepository;
    }

    public ResponseEntity<?> saveEmployees(String xlsxFile) {

        File file = base64ToFile.getFile(xlsxFile, "employees.xlsx");

        userRepository.deleteAll();

        try {

            List<XlsxDto> xlsxData = Poiji.fromExcel(file, XlsxDto.class);

            List<User> users = new ArrayList<>();
            List<Department> departments = new ArrayList<>();

            for (XlsxDto employee : xlsxData) {
                User user = new User();
                user.setFirstName(employee.getFullName().split(" ")[0]);
                user.setLastName(employee.getFullName().split(" ")[1]);
                user.setEmail(employee.getEmail());
                user.setUsername(employee.getUsername());
                user.setManagerUserName(employee.getManager());
                user.setDepartmentName(employee.getDepartment());
                if (dateUtilities.getDate(employee.getEndDate()).isBefore(LocalDate.now()))
                    user.setState(UserState.INACTIVE);
                users.add(user);

                UserDetails userDetails = new UserDetails();
                userDetails.setPhoneNumber(employee.getPhoneNumber());
                userDetails.setStartDaate(dateUtilities.getDate(employee.getStartDate()));
                userDetails.setEndDate(dateUtilities.getDate(employee.getEndDate()));

                Address address = new Address();
                address.setCity(employee.getAddress().split(",")[0]);
                address.setStreet(employee.getAddress().split(",")[1]);
                userDetails.setAddress(address);

                if (employee.getDepartmentName() != null) {
                    Department department = new Department();
                    department.setDepartmentName(employee.getDepartmentName());
                    department.setDepartmentPhoneNumber(employee.getDepartmentPhone());
                    department.setDepartmentLeaderName(employee.getDepartmentLeader());
                    departments.add(department);
                }
                user.setUserDetails(userDetails);

            }

            //Adds missing departments and assigns a random employee as the department leader
            createMissingDepartments(users,departments);

            //Add relations
            for (User user : users) {
                //Set managers
                for (User u1 : users) {
                    if (user.getManagerUserName() != null && u1.getUsername() != null &&
                            user.getManagerUserName().equals(u1.getUsername())) {
                        user.setManager(u1);
                        user.setManagerUserName(u1.getUsername());
                    }
                }
                //Set departments
                for (Department department : departments) {
                    if (department.getDepartmentLeaderName() != null && department.getDepartmentLeaderName().equals(user.getUsername()))
                        department.setDepartmentLeader(user);

                    if (user.getDepartmentName().equals(department.getDepartmentName())) {
                        user.getUserDetails().setDepartment(department);
                    }
                }

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

    private void createMissingDepartments(List<User> users, List<Department> departments) {
        List<String> missingDepartments =
                new ArrayList<>(users.stream().collect(Collectors.groupingBy(User::getDepartmentName)).keySet().stream().sorted().toList());

        missingDepartments.removeAll(departments.stream().map(Department::getDepartmentName).sorted().toList());

        missingDepartments.forEach(departmentName ->
                departments.add(new Department(departmentName, users.get(utilities.getRandomNumber(0, users.size())))));

    }

    public ResponseObject<List<ViewUser>> search(String search) {

        ResponseObject<List<ViewUser>> responseObject = new ResponseObject<>();
        responseObject.prepareHttpStatus(HttpStatus.BAD_REQUEST);

        try {
            search = "%"+search+"%";
            List<ViewUser> users = userRepository.searchUsersByNameAndEmail(search);
            responseObject.prepareHttpStatus(HttpStatus.OK);
            responseObject.setData(users);
            return responseObject;
        } catch (Exception e) {
            return responseObject;
        }

    }

    public ResponseObject<ActiveAndInactiveUsers> getActiveAndInactiveUsers(){

        ResponseObject<ActiveAndInactiveUsers> responseObject = new ResponseObject<>();
        responseObject.prepareHttpStatus(HttpStatus.OK);

        List<ViewUser> activeUsers = userRepository.findUsersByState(UserState.ACTIVE);
        List<ViewUser> inactiveUsers = userRepository.findUsersByState(UserState.INACTIVE);

        ActiveAndInactiveUsers activeAndInactiveUsers = new ActiveAndInactiveUsers();
        activeAndInactiveUsers.setActive(activeUsers);
        activeAndInactiveUsers.setInactive(inactiveUsers);

        responseObject.setData(activeAndInactiveUsers);

        if(activeUsers.size()+inactiveUsers.size() == 0) responseObject.prepareHttpStatus(HttpStatus.NO_CONTENT);

        return responseObject;
    }

    public ResponseObject<List<ViewUser>> findUsersInAscendingOrder() {

        ResponseObject<List<ViewUser>> responseObject = new ResponseObject<>();
        responseObject.prepareHttpStatus(HttpStatus.OK);

        List<ViewUser> usersInAscOrder = userRepository.getUsersAscOrder();

        if(!usersInAscOrder.isEmpty()) responseObject.setData(usersInAscOrder);
        else responseObject.prepareHttpStatus(HttpStatus.NO_CONTENT);

        return responseObject;

    }

    public ResponseObject<List<GroupByDepartment>> findGroupedUsersByDepartment(){
        ResponseObject<List<GroupByDepartment>> responseObject = new ResponseObject<>();
        responseObject.prepareHttpStatus(HttpStatus.OK);

        List<GroupByDepartment> usersInAscOrder = userRepository.getUsersGroupByDepartment();

        Map<String, List<String>> usersByDepartment = usersInAscOrder.stream()
                .collect(Collectors.groupingBy(
                        GroupByDepartment::getEmployeesLastName,
                        TreeMap::new,
                        Collectors.mapping(GroupByDepartment::getDepartment, Collectors.toList())));

        List<GroupByDepartment> groupByDepartments = new ArrayList<>();

        for(String department : usersByDepartment.keySet()){
            GroupByDepartment groupByDepartment = new GroupByDepartment();
            groupByDepartment.setDepartment(department);
            groupByDepartment.setEmployeeLastNamesList(usersByDepartment.get(department));
            groupByDepartments.add(groupByDepartment);
        }

        if(!groupByDepartments.isEmpty()) responseObject.setData(groupByDepartments);
        else responseObject.prepareHttpStatus(HttpStatus.NO_CONTENT);

        return responseObject;
    }
}