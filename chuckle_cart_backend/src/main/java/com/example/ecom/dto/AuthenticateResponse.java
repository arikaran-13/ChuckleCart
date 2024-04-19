package com.example.ecom.dto;

import com.example.ecom.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateResponse {

    private Long id;
    private String name;
    private UserRole role;
    private String email;
    private String jwtToken;
}
