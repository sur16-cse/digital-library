package com.example.digital_library.controller;

import com.example.digital_library.dto.CreateStudentRequest;
import com.example.digital_library.dto.UpdateStudentRequest;
import com.example.digital_library.model.Student;
import com.example.digital_library.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")  // Changed to plural form
public class StudentController {
    @Autowired
    StudentService studentService;

    @PostMapping("/create")  // More specific endpoint
    public Student createStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest) {
        return studentService.create(createStudentRequest);
    }

    @GetMapping("/all")  // Changed from /all to /list
    public List<Student> getAllStudents() {
        return studentService.getAll();
    }

    @GetMapping("/id/{studentId}")  // More specific endpoint
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentService.get(studentId);
    }

    @DeleteMapping("/delete")  // More specific endpoint
    public Student deleteStudent(@RequestParam("id") int studentId) {
        return studentService.delete(studentId);
    }

    @PutMapping("/update/{studentId}")  // More specific endpoint
    public Student updateStudent(@PathVariable("studentId") int studentId, @RequestBody @Valid UpdateStudentRequest updateStudentRequest) {
        return studentService.update(studentId, updateStudentRequest);
    }
}
