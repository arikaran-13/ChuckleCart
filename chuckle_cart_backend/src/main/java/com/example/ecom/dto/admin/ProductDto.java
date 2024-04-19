package com.example.ecom.dto.admin;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {

    private Long id;
    private String name;
    private Long price;
    private String description;
    private byte[] byteImg;
    private Long category_id;
    private String categoryName;
    private MultipartFile img;
}
