package com.example.ecom.controller.admin;

import com.example.ecom.dto.admin.CategoryDto;
import com.example.ecom.service.admin.AdminCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AdminCategoryController {

    private final AdminCategoryService categoryService;

    @PostMapping("/create/category")
    public ResponseEntity<CategoryDto>createCategory(@RequestBody CategoryDto categoryDto){

        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoryService
                        .createCategory(categoryDto)
        );
    }

    @GetMapping("/category")
    public ResponseEntity<List<CategoryDto>>getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(
                categoryService.getAllCategories()
        );
    }


}
