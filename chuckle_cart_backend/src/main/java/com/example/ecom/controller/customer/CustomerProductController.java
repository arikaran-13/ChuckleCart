package com.example.ecom.controller.customer;

import com.example.ecom.dto.admin.ProductDto;
import com.example.ecom.service.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
@CrossOrigin("*")
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>>getAllProducts(){
        return ResponseEntity.ok(customerProductService.getAllProduct());
    }

    @GetMapping("/products/{name}")
    public ResponseEntity<List<ProductDto>>getAllProductsByProductName(@PathVariable("name")String name){
        return ResponseEntity.ok(customerProductService.getAllProductByproductName(name));
    }
}
