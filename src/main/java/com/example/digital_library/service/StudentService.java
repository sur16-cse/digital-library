package com.example.digital_library.service;

import com.example.digital_library.dto.CreateStudentRequest;
import com.example.digital_library.dto.UpdateStudentRequest;
import com.example.digital_library.model.Student;
import com.example.digital_library.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    public Student create(CreateStudentRequest createStudentRequest) {
        Student student = createStudentRequest.to();
        return studentRepository.save(student);
    }

    public Student get(int id) {
        return studentRepository.findById(id).orElse(null);
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
