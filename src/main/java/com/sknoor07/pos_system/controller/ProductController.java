package com.sknoor07.pos_system.controller;

import com.sknoor07.pos_system.Service.ProductService;
import com.sknoor07.pos_system.Service.UserService;
import com.sknoor07.pos_system.exceptions.UserException;
import com.sknoor07.pos_system.modals.user.User;
import com.sknoor07.pos_system.payload.dto.ProductDTO;
import com.sknoor07.pos_system.payload.response.ApiResposne;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final UserService userService;


    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO, @RequestHeader("Authorization") String token) throws Exception {
        User user= userService.getUserFromJwtToken(token);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productDTO,user));
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ProductDTO>> createProduct(@PathVariable Long storeId) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByStoreId(storeId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO,@PathVariable Long id, @RequestHeader("Authorization") String token) throws Exception {
        User user= userService.getUserFromJwtToken(token);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id,productDTO,user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResposne> deleteProduct(@PathVariable Long id, @RequestHeader("Authorization") String token) throws Exception {
        User user= userService.getUserFromJwtToken(token);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        productService.deleteProduct(id,user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResposne("Product deleted successfully"));
    }

    @GetMapping("/store/{storeId}/search")
    public ResponseEntity<List<ProductDTO>> searchProductByKeyword (@PathVariable Long storeId,
                                                              @RequestParam String keyword,
                                                              @RequestHeader("Authorization") String token) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(productService.searchProductsByKeyword(storeId,keyword));
    }



}
