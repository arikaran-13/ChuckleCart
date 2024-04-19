package com.example.ecom.service.Auth;

import com.example.ecom.dto.admin.Admin;
import com.example.ecom.dto.AuthenticateRequest;
import com.example.ecom.dto.AuthenticateResponse;
import com.example.ecom.dto.UserDto;
import com.example.ecom.entity.customer.Order;
import com.example.ecom.entity.customer.User;
import com.example.ecom.enums.OrderStatus;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    @Override
    public UserDto register(UserDto userDto) {
        var user = mapUserDtoToUserEntity(userDto);
        var userEntity = userRepository.save(user);
        createEmptyCartForNewUser(userEntity);
        return mapUserEntityToUserDto(user);
    }

    private void createEmptyCartForNewUser(User createUser) {
        var order = new Order();
        order.setAmount(0L);
        order.setDiscount(0L);
        order.setOrderStatus(OrderStatus.Pending);
        order.setTotalAmount(0L);
        order.setUser(createUser);
        orderRepository.save(order);
    }

    private User mapUserDtoToUserEntity(UserDto userDto) {
        var user = new User();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        return user;
    }

    private UserDto mapUserEntityToUserDto(User user) {
        var userDto = new UserDto();
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    @Override
    public AuthenticateResponse login(AuthenticateRequest userDto) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDto.getEmail(),
                            userDto.getPassword()
                    )
            );
        }catch(BadCredentialsException ex){
            log.error("Login failed",ex.getMessage());
        }

        User user = userRepository
                .findByEmail(userDto.getEmail())
                .orElseThrow(
                        ()-> new UsernameNotFoundException
                                ("Username not found")
                );
        final String token = "Bearer "+jwtService.createJwtToken(user);
        return AuthenticateResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .role(user.getRole())
                .email(userDto.getEmail())
                .jwtToken(token).build();
    }

    @Override
    public Admin createAdmin(Admin admin) {
        var user = mapAdminToUserEntity(admin);
        userRepository.save(user);
        return admin;
    }

    private User mapAdminToUserEntity(Admin admin) {
        var user = new User();
        user.setRole(admin.getRole());
        user.setName(admin.getName());
        user.setEmail(admin.getEmail());
        user.setPassword(passwordEncoder.encode(admin.getPassword()));
        return user;
    }
}
