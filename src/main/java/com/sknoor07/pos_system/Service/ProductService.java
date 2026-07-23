package com.sknoor07.pos_system.Service;

import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.ProductDTO;

import java.util.List;

public interface ProductService {
    ProductDTO createProduct(ProductDTO productDTO, User user) throws Exception;
    ProductDTO updateProduct(Long Id, ProductDTO productDTO, User user) throws Exception;
    void deleteProduct(Long Id, User user) throws Exception;
    List<ProductDTO> getProductsByStoreId(Long storeId);
    List<ProductDTO> searchProductsByKeyword(Long storeId, String keyword);



}
