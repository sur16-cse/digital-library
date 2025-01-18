package com.example.digital_library.repository;

import com.example.digital_library.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query("SELECT '*' FROM Student")
    List<Student> findAllStudents();

}
