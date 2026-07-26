package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.EmployeeService;
import com.sknoor07.pos_system.mapper.UserMapper;
import com.sknoor07.pos_system.modals.branch.Branch;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import com.sknoor07.pos_system.payload.dto.UserDTO;
import com.sknoor07.pos_system.repository.BranchRepository;
import com.sknoor07.pos_system.repository.StoreRepository;
import com.sknoor07.pos_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final BranchRepository branchRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO createStoreEmployee(UserDTO employee, Long storeId) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new Exception("Store not found"));
        Branch branch =null;

        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER) {
            if(employee.getBranchId()==null){
                throw new Exception("Cannot Create a manager with no Branch ID");
            }
            branch= branchRepository.findById(employee.getBranchId()).orElseThrow(() -> new Exception("Branch not found"));
        }
        User user= UserMapper.toEntity(employee);
        user.setStore(store);
        user.setBranch(branch);
        user.setCreatedAt(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(employee.getPassword()));
        User savedUser = userRepository.save(user);
        if(employee.getRole()==UserRole.ROLE_BRANCH_MANAGER && branch!=null){
            branch.setUserManager(savedUser );
            branchRepository.save(branch);
        }
        return UserMapper.toDTO(savedUser);
    }

    @Override
    public UserDTO createBranchEmployee(UserDTO employee, Long branchId) throws Exception {
        Branch branch= branchRepository.findById(branchId).orElseThrow(() -> new Exception("Branch not found"));
        if (employee.getRole() != UserRole.ROLE_BRANCH_CASHIER &&
                employee.getRole() != UserRole.ROLE_BRANCH_MANAGER) {
            throw new Exception("Invalid role for branch employee.");
        }
        User user= UserMapper.toEntity(employee);
            user.setBranch(branch);
            user.setStore(branch.getStore());
            user.setPassword(passwordEncoder.encode(employee.getPassword()));
            user.setFullName(employee.getFullName());
            user.setCreatedAt(LocalDateTime.now());
            user.setEmail(employee.getEmail());
            user.setPhoneNumber(employee.getPhoneNumber());
        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public User updateEmployee(Long employeeId, UserDTO employeeDetails) throws Exception {
        User existingUser = userRepository.findById(employeeId).orElseThrow(() -> new Exception("Employee not found with the given Id"));

        existingUser.setBranch(branchRepository.findById(employeeDetails.getBranchId()).orElseThrow(() -> new Exception("Branch not found")));
        existingUser.setEmail(employeeDetails.getEmail());
        existingUser.setFullName(employeeDetails.getFullName());
        existingUser.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));
        existingUser.setRole(employeeDetails.getRole());
        existingUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteEmployee(Long employeeId) throws Exception {
        User employee = userRepository.findById(employeeId)
                .orElseThrow(() -> new Exception("Employee not found"));

        Branch branch = employee.getBranch();

        if (branch != null && branch.getUserManager() != null &&
                branch.getUserManager().getId().equals(employee.getId())) {

            branch.setUserManager(null);
            branchRepository.save(branch);
        }

        userRepository.delete(employee);

    }

    @Override
    public List<UserDTO> findAllStoreEmployees(Long storeId, UserRole role) throws Exception {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new Exception("Store not found"));
        return userRepository.findByStore(store).stream().filter(user -> role ==null || user.getRole() == null).map(UserMapper::toDTO).collect(Collectors.toList());

    }

    @Override
    public List<UserDTO> findAllBranchEmployees(Long branchId, UserRole role) throws Exception {
        Branch branch= branchRepository.findById(branchId).orElseThrow(()->new Exception("Branch Not Found"));
        return userRepository.findByBranchId(branchId).stream().filter(user->role==null || user.getRole()==role).map(UserMapper::toDTO).collect(Collectors.toList());
    }
}
