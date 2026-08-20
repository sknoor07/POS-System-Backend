package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.EmployeeService;
import com.sknoor07.pos_system.mapper.UserMapper;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
@Tag(name = "Employee Management", description = "Endpoints for creating, updating, listing, and removing store/branch employees and cashiers")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Operation(summary = "Create Store Employee", description = "Creates a new employee (e.g. Store Admin/Manager) associated with a store.")
    @ApiResponse(responseCode = "201", description = "Store employee successfully created")
    @PostMapping("/store/{storeId}")
    public ResponseEntity<UserDTO> createStoreEmployee(
            @RequestBody UserDTO employee,
            @Parameter(description = "ID of the store", required = true) @PathVariable Long storeId) throws Exception {
        UserDTO newEmployee = employeeService.createStoreEmployee(employee, storeId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @Operation(summary = "Create Branch Employee", description = "Creates a new employee (e.g. Cashier, Branch Manager) assigned to a specific branch.")
    @ApiResponse(responseCode = "201", description = "Branch employee successfully created")
    @PostMapping("/branch/{branchId}")
    public ResponseEntity<UserDTO> createBranchEmployee(
            @RequestBody UserDTO employee,
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId) throws Exception {
        UserDTO newEmployee = employeeService.createBranchEmployee(employee, branchId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEmployee);
    }

    @Operation(summary = "Update Employee", description = "Updates employee details (name, email, role, phone) by employee ID.")
    @ApiResponse(responseCode = "200", description = "Employee successfully updated")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @PutMapping("/{employeeId}")
    public ResponseEntity<UserDTO> updateEmployee(
            @RequestBody UserDTO employee,
            @Parameter(description = "ID of the employee", required = true) @PathVariable Long employeeId) throws Exception {
        User newEmployee = employeeService.updateEmployee(employeeId, employee);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toDTO(newEmployee));
    }

    @Operation(summary = "Delete Employee", description = "Removes an employee by employee ID.")
    @ApiResponse(responseCode = "200", description = "Employee successfully deleted")
    @ApiResponse(responseCode = "404", description = "Employee not found")
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<ApiResposne> deleteEmployee(
            @Parameter(description = "ID of the employee", required = true) @PathVariable Long employeeId) throws Exception {
        employeeService.deleteEmployee(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Employee deleted successfully"));
    }

    @Operation(summary = "Find All Store Employees", description = "Retrieves all employees for a store, optionally filtered by user role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved store employees list")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<UserDTO>> findAllStoreEmployees(
            @Parameter(description = "ID of the store", required = true) @PathVariable Long storeId,
            @Parameter(description = "Optional user role filter (e.g. ROLE_STORE_ADMIN, ROLE_CASHIER)") @RequestParam(required = false) UserRole userRole) throws Exception {
        List<UserDTO> allStoreEmployees = employeeService.findAllStoreEmployees(storeId, userRole);
        return ResponseEntity.status(HttpStatus.OK).body(allStoreEmployees);
    }

    @Operation(summary = "Find All Branch Employees", description = "Retrieves all employees assigned to a specific branch, optionally filtered by role.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved branch employees list")
    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<UserDTO>> findAllBranchEmployees(
            @Parameter(description = "ID of the branch", required = true) @PathVariable Long branchId,
            @Parameter(description = "Optional user role filter") @RequestParam(required = false) UserRole userRole) throws Exception {
        List<UserDTO> allStoreEmployees = employeeService.findAllBranchEmployees(branchId, userRole);
        return ResponseEntity.status(HttpStatus.OK).body(allStoreEmployees);
    }
}
