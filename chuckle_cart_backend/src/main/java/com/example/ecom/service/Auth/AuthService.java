package com.example.ecom.service.Auth;

import com.example.ecom.dto.admin.Admin;
import com.example.ecom.dto.AuthenticateRequest;
import com.example.ecom.dto.AuthenticateResponse;
import com.example.ecom.dto.UserDto;

public interface AuthService {

    UserDto register(UserDto userDto);
    AuthenticateResponse login(AuthenticateRequest userDto);

   Admin createAdmin(Admin admin);

}
