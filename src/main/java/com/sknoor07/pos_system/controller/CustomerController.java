package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.CustomerService;
import com.sknoor07.pos_system.modals.customer.Customer;
import com.sknoor07.pos_system.payload.request.CustomerCreateDTO;
import com.sknoor07.pos_system.payload.request.CustomerUpdateDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Tag(name = "Customer Management", description = "Endpoints for creating, updating, searching, retrieving, and deleting customers")
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "Create Customer", description = "Registers a new customer in the POS system.")
    @ApiResponse(responseCode = "201", description = "Customer successfully created")
    @PostMapping
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CustomerCreateDTO customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @Operation(summary = "Update Customer", description = "Updates an existing customer's contact details or loyalty info.")
    @ApiResponse(responseCode = "200", description = "Customer successfully updated")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @Parameter(description = "ID of the customer", required = true) @PathVariable Long id,
            @Valid @RequestBody CustomerUpdateDTO customer) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.updateCustomer(id, customer));
    }

    @Operation(summary = "Delete Customer", description = "Deletes a customer by ID.")
    @ApiResponse(responseCode = "200", description = "Customer successfully deleted")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteCustomer(
            @Parameter(description = "ID of the customer", required = true) @PathVariable Long id) throws Exception {
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Customer Deleted Successfully"));
    }

    @Operation(summary = "Get All Customers", description = "Retrieves a complete list of all registered customers.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved customers list")
    @GetMapping()
    public ResponseEntity<List<Customer>> getAllCustomer() {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getAllCustomers());
    }

    @Operation(summary = "Get Customer by ID", description = "Retrieves customer profile and loyalty details by customer ID.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved customer details")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(
            @Parameter(description = "ID of the customer", required = true) @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.getCustomer(id));
    }

    @Operation(summary = "Search Customers", description = "Searches for customers by name, phone, or email with pagination.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated search results")
    @GetMapping("/search")
    public ResponseEntity<Page<Customer>> setCustomer(
            @Parameter(description = "Keyword to search by (name, phone, email)", required = true) @RequestParam String keyword,
            Pageable pageable) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.searchCustomer(keyword, pageable));
    }
}
