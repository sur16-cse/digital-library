package com.example.digital_library.service;

import com.example.digital_library.dto.CreateStudentRequest;
import com.example.digital_library.dto.GetStudentResponse;
import com.example.digital_library.dto.UpdateStudentRequest;
import com.example.digital_library.model.SecuredUser;
import com.example.digital_library.model.Student;
import com.example.digital_library.repository.StudentCacheRepository;
import com.example.digital_library.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${student.authorities}")
    String authorities;

    @Autowired
    UserService userService;

    @Autowired
    StudentCacheRepository studentCacheRepository;

    public Student create(CreateStudentRequest createStudentRequest) {
        Student student = createStudentRequest.to();
        SecuredUser securedUser = student.getSecuredUser();

        securedUser.setPassword(passwordEncoder.encode(securedUser.getPassword()));
        securedUser.setAuthorities(authorities);

        securedUser = userService.save(securedUser);

        student.setSecuredUser(securedUser);
        return studentRepository.save(student);
    }

    public Student get(int id) {
        return studentRepository.findById(id).orElse(null);
    }

    public GetStudentResponse getUsingCache(int studentId) {
        GetStudentResponse studentResponse = studentCacheRepository.get(studentId);
        if (studentResponse == null) {
            Student student = studentRepository.findById(studentId).orElse(null);
            assert student != null;
            studentResponse = new GetStudentResponse(student);
            studentCacheRepository.set(studentResponse);
        }
        return studentResponse;
    }

    public Student delete(int studentId) {
        Student student = this.get(studentId);
        Student copy = new Student();
        copy.setId(student.getId());
        copy.setName(student.getName());

        studentRepository.deleteById(studentId);

        // return student detached copy
        return copy;
    }

    public Student update(int studentId, UpdateStudentRequest updateStudentRequest) {
        Student student = this.get(studentId);
//        studentRepository.
        return null;
    }

    public List<Student> getAll() {
//        return studentRepository.findAllStudents();
        return studentRepository.findAll();
    }
}
