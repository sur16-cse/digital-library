package com.example.digital_library.repository;

import com.example.digital_library.model.SecuredUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SecuredUser, Integer> {
    SecuredUser findByUsername(String username);
}
