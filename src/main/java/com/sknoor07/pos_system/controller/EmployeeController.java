package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.EmployeeService;
import com.sknoor07.pos_system.mapper.UserMapper;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDTO> createStoreEmployee(@RequestBody UserDTO employee, @PathVariable Long storeId) throws Exception {
        UserDTO newEmployee= employeeService.createStoreEmployee(employee, storeId);
        return  ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDTO> createBranchEmployee(@RequestBody UserDTO employee, @PathVariable Long branchId) throws Exception {
        UserDTO newEmployee= employeeService.createBranchEmployee(employee, branchId);
        return  ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @PutMapping("/{employeeId}")
    public ResponseEntity<UserDTO> updateEmployee(@RequestBody UserDTO employee, @PathVariable Long employeeId) throws Exception {
        User newEmployee= employeeService.updateEmployee(employeeId,employee);
        return  ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDTO(newEmployee));
    }


    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ApiResposne> deleteEmployee(@PathVariable Long employeeId) throws Exception {
        employeeService.deleteEmployee(employeeId);
        return  ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Employee deleted successfully"));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<UserDTO>> findAllStoreEmployees(@PathVariable Long storeId, @RequestParam(required = false) UserRole userRole) throws Exception {
        List<UserDTO> allStoreEmployees= employeeService.findAllStoreEmployees(storeId, userRole);
        return  ResponseEntity.status(HttpStatus.OK).body(allStoreEmployees);
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<UserDTO>> findAllBranchEmployees(@PathVariable Long branchId, @RequestParam(required = false) UserRole userRole) throws Exception {
        List<UserDTO> allStoreEmployees= employeeService.findAllBranchEmployees(branchId, userRole);
        return  ResponseEntity.status(HttpStatus.OK).body(allStoreEmployees);
    }



}
