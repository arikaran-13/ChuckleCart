package com.example.ecom.dto.admin;

import com.example.ecom.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin {

    private String name;
    private String email;
    private String password;
    private UserRole role;
}
