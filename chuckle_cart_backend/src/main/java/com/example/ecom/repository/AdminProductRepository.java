package com.example.ecom.repository;

import com.example.ecom.dto.admin.ProductDto;
import com.example.ecom.entity.admin.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminProductRepository extends JpaRepository<Product,Long> {

    List<Product>findAllByNameContaining(String productName);
}
