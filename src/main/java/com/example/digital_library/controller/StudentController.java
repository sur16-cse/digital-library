package com.example.digital_library.controller;

import com.example.digital_library.dto.CreateStudentRequest;
import com.example.digital_library.dto.UpdateStudentRequest;
import com.example.digital_library.model.Student;
import com.example.digital_library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/{studentId}")
    public Student getStudentByIdFromAdmin(@PathVariable("studentId") int studentId) {
        return studentService.get(studentId);
    }

    @GetMapping("/id")  // More specific endpoint
    public Student getStudentById() {
        //TODO Need to add logic for retrieving studentId
        int studentId = 0;
        return studentService.get(studentId);
    }

    @DeleteMapping("/delete")  // More specific endpoint
    public Student deleteStudent() {
        //TODO Need to add logic for retrieving studentId
        int studentId = 0;
        return studentService.delete(studentId);
    }

    @PutMapping("/update")  // More specific endpoint
    public Student updateStudent(@RequestBody @Valid UpdateStudentRequest updateStudentRequest) {
        //TODO Need to add logic for retrieving studentId
        int studentId = 0;
        return studentService.update(studentId, updateStudentRequest);
    }
}
