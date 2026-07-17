package com.berkant.reelshelf.repository;

import com.berkant.reelshelf.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AuthRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
