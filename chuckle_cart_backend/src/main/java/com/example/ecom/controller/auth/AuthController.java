package com.example.ecom.controller.auth;

import com.example.ecom.dto.admin.Admin;
import com.example.ecom.dto.AuthenticateRequest;
import com.example.ecom.dto.AuthenticateResponse;
import com.example.ecom.dto.UserDto;
import com.example.ecom.service.Auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto>register(@RequestBody UserDto userDto){
        return ResponseEntity.ok(authService.register(userDto));
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticateResponse>login(@RequestBody AuthenticateRequest request){
       return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/admin")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin){
        return ResponseEntity.ok(authService.createAdmin(admin));
    }

}
