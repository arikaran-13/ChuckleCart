package com.example.ecom.repository;

import com.example.ecom.entity.customer.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User>findByEmail(String email);
}
