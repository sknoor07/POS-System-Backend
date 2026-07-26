package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import com.sknoor07.pos_system.payload.dto.UserDTO;

import java.util.List;

public interface EmployeeService {

    UserDTO createStoreEmployee(UserDTO employee,  Long storeId) throws Exception;

    UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception;

    User updateEmployee( Long employeeId, UserDTO employeeDetails) throws Exception;

    void deleteEmployee(Long employeeId) throws Exception;

    List<UserDTO> findAllStoreEmployees(Long storeId, UserRole role) throws Exception;

    List<UserDTO>findAllBranchEmployees(Long branchId, UserRole role) throws Exception;




}
