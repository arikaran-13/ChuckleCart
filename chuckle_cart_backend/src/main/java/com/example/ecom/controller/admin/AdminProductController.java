package com.example.ecom.controller.admin;

import com.example.ecom.dto.admin.ProductDto;
import com.example.ecom.dto.admin.SearchProductDetails;
import com.example.ecom.service.admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin/product")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminProductController {

    private final AdminProductService adminProductService;

    @PostMapping("/create")
    public ResponseEntity<ProductDto> createProduct(
            @ModelAttribute ProductDto productDto
    ) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminProductService.createProduct(productDto));
    }

    @GetMapping("/all")
   public ResponseEntity<List<ProductDto>>getAllProducts(){
        return ResponseEntity.ok(adminProductService.getAllProducts());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean>deleteById(@PathVariable("id")Long id){
        return ResponseEntity.ok(adminProductService.deleteProduct(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto>getProductById(@PathVariable("id")Long id){
        return ResponseEntity.ok(adminProductService.getProductById(id));
    }

    @GetMapping("/search/{searchTxt}")
    public ResponseEntity<List<ProductDto>>getProductByProductName(@PathVariable String searchTxt){
        return ResponseEntity.ok(adminProductService.searchProduct(searchTxt));
    }
}
