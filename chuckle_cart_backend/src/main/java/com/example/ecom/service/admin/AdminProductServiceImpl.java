package com.example.ecom.service.admin;

import com.example.ecom.dto.admin.ProductDto;
import com.example.ecom.dto.admin.SearchProductDetails;
import com.example.ecom.entity.admin.Category;
import com.example.ecom.entity.admin.Product;
import com.example.ecom.repository.AdminProductRepository;
import com.example.ecom.repository.CategoryRepository;
import com.example.ecom.service.admin.AdminProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminProductServiceImpl implements AdminProductService {

    private final AdminProductRepository adminProductRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public ProductDto createProduct(ProductDto productDto) throws IOException {
        Optional<Product> optProduct = Optional.empty();
      if(productDto.getId() != null) {
          optProduct = adminProductRepository.findById(productDto.getId());
      }

       if(optProduct.isPresent()) {
          return updateExistingProductDetails(optProduct.get(), productDto);
       }
       else {
           var productEntity = mapProductDtoToEntity(productDto);
           return mapProductEntityToDto(adminProductRepository.save(productEntity));
       }
    }

    private ProductDto updateExistingProductDetails(Product existingProductDetails, ProductDto newProductDetails ) throws IOException {
         var newProduct = mapProductDtoToEntity(newProductDetails);
         existingProductDetails.setCategory(newProduct.getCategory());
         existingProductDetails.setName(newProduct.getName());
         existingProductDetails.setPrice(newProduct.getPrice());
         existingProductDetails.setImg(newProduct.getImg());
         existingProductDetails.setDescription(newProduct.getDescription());
         log.info("Product details updated existing product {} , new product {}", existingProductDetails , newProduct);
         return mapProductEntityToDto(adminProductRepository.save(existingProductDetails));

    }

    @Override
    public List<ProductDto> getAllProducts() {
        return adminProductRepository.findAll().stream().map(this::mapProductEntityToDto).collect(Collectors.toList());
    }

    @Override
    public boolean deleteProduct(Long id) {
        try {
            adminProductRepository.deleteById(id);
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public ProductDto getProductById(Long id) {
        var product = adminProductRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("Product not found")
        );
        return mapProductEntityToDto(product);
    }

    @Override
    public List<ProductDto> searchProduct(String searchTxt) {
        return getAllProducts()
                .stream()
                .filter(p->p.getName()
                        .contains(searchTxt))
                .collect(Collectors.toList());
    }

    private ProductDto mapProductEntityToDto(Product product) {
        var productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setByteImg(product.getImg());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setCategory_id(product.getCategory().getId());
        productDto.setCategoryName(product.getCategory().getName());
        return productDto;

    }

    private Product mapProductDtoToEntity(ProductDto productDto) throws IOException {
        var product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImg(productDto.getImg().getBytes());
        Category category = categoryRepository.findById(productDto.getCategory_id()).orElseThrow(()-> new RuntimeException("Category not found"));
        product.setCategory(category);
        return product;
    }
}
