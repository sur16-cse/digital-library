package com.example.digital_library.repository;

import com.example.digital_library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Author findByEmail(String authorEmail);
}
