package com.example.digital_library.controller;

import com.example.digital_library.dto.CreateStudentRequest;
import com.example.digital_library.dto.UpdateStudentRequest;
import com.example.digital_library.model.SecuredUser;
import com.example.digital_library.model.Student;
import com.example.digital_library.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public Student getStudentForAdmin(@PathVariable("studentId") int studentId) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        return studentService.get(studentId);
    }

    @GetMapping("/id")  // More specific endpoint
    public Student getStudentById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return studentService.get(studentId);
    }

    @DeleteMapping("/delete")  // More specific endpoint
    public Student deleteStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return studentService.delete(studentId);
    }

    @PutMapping("/update")  // More specific endpoint
    public Student updateStudent(@RequestBody @Valid UpdateStudentRequest updateStudentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return studentService.update(studentId, updateStudentRequest);
    }
}
