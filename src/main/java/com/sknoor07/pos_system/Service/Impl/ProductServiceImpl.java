package com.sknoor07.pos_system.Service.Impl;

import com.sknoor07.pos_system.Service.ProductService;
import com.sknoor07.pos_system.mapper.ProductMapper;
import com.sknoor07.pos_system.modals.category.Category;
import com.sknoor07.pos_system.modals.product.Product;
import com.sknoor07.pos_system.modals.store.Store;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.ProductDTO;
import com.sknoor07.pos_system.repository.CategoryRepository;
import com.sknoor07.pos_system.repository.ProductRepository;
import com.sknoor07.pos_system.repository.StoreRepository;
import com.sknoor07.pos_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception {
        Store store= storeRepository.findById(productDTO.getStoreId()).orElseThrow(()->new Exception("Store not found"));
        Category category=categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->new Exception("Category not found"));
        Product product= ProductMapper.toProduct(productDTO,store,category);
        return ProductMapper.toProductDTO(productRepository.save(product));

    }

    @Override
    public ProductDTO updateProduct(Long Id, ProductDTO productDTO, User user) throws Exception {
        Product product= productRepository.findById(Id).orElseThrow(()-> new Exception("Product Not Found"));

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSku(productDTO.getSku());
        product.setImage(productDTO.getImage());
        product.setMrp(productDTO.getMrp());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setColor(productDTO.getColor());
        product.setBrand(productDTO.getBrand());
        product.setUpdatedAt(LocalDateTime.now());
        if(productDTO.getCategoryId()!=null){
            Category category= categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(()->new Exception("Category not found"));
            product.setCategory(category);
        }
        return ProductMapper.toProductDTO(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Long Id, User user) throws Exception {
        Product product= productRepository.findById(Id).orElseThrow(()-> new Exception("Product Not Found"));
        productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getProductsByStoreId(Long storeId) {
        return productRepository
                .findByStoreId(storeId)
                .stream()
                .map(ProductMapper::toProductDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> searchProductsByKeyword(Long storeId, String keyword) {
        return productRepository
                .searchByKeyword(storeId,keyword)
                .stream()
                .map(ProductMapper::toProductDTO)
                .collect(Collectors.toList());
    }
}
