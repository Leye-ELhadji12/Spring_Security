package com.security.repository;

import com.security.entity.User_;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface User_repository extends JpaRepository<User_, Integer> {

    @Query(value = "SELECT * FROM User_ WHERE email = ?", nativeQuery = true)
    Optional<User_> findByEmail(String email);

}
