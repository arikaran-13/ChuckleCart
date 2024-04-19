package com.example.ecom.security;

import com.example.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserRepository userRepository;
    @Bean
    public UserDetailsService createUserDetailServiceBean(){
       return username -> userRepository.findByEmail(username)
               .orElseThrow(()-> new UsernameNotFoundException("Username not found"));
    }

    @Bean
    public AuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(createUserDetailServiceBean());
        authenticationProvider.setPasswordEncoder(getPassWordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder getPassWordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
