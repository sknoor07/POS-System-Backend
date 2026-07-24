package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.CategoryService;
import com.sknoor07.pos_system.Service.StoreService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.mapper.CategoryMapper;
import com.sknoor07.pos_system.mapper.StoreMapper;
import com.sknoor07.pos_system.modals.category.Category;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.modals.user.UserRole;
import com.sknoor07.pos_system.payload.dto.CategoryDTO;
import com.sknoor07.pos_system.payload.dto.StoreDTO;
import com.sknoor07.pos_system.repository.CategoryRepository;
import com.sknoor07.pos_system.repository.StoreRepository;
import com.sknoor07.pos_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;
    private final UserService userService;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) throws Exception {
        User user= userService.getCurrentUser();
        Store  store= storeRepository.findById(categoryDTO.getStoreId()).orElseThrow(
                () -> new RuntimeException("Store not found")
        );
        Category category= Category.builder()
                .name(categoryDTO.getName())
                .store(store)
                .build();

        checkAuthority(userService.getCurrentUser(),category.getStore());

        return CategoryMapper.tocategoryDTO(categoryRepository.save(category));

    }

    @Override
    public List<CategoryDTO> getCategoriesByStore(Long storeId) {
        return categoryRepository.findByStoreId(storeId).stream().map(CategoryMapper::tocategoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) throws Exception {
        Category category= categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        checkAuthority(userService.getCurrentUser(),category.getStore());
        category.setName(categoryDTO.getName());
        return CategoryMapper.tocategoryDTO(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        Category category= categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
        checkAuthority(userService.getCurrentUser(),category.getStore());
        categoryRepository.delete(category);

    }

    private void checkAuthority(User user, Store store) throws Exception {
        boolean isAdmin= user.getRole().equals(UserRole.ROLE_STORE_ADMIN);
        boolean isManager= user.getRole().equals(UserRole.ROLE_STORE_MANAGER);
        boolean isSameStore= user.equals(store.getStoreAdmin());

        if (!(isManager || (isAdmin && isSameStore))) {
            throw new Exception("You do not have permission to access this resource");
        }



    }
}
