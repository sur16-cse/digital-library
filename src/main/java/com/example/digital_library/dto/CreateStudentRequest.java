package com.example.digital_library.dto;

import com.example.digital_library.model.Student;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateStudentRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String contact;

    public Student to() {
        return Student.builder().name(this.name).contact(this.contact).cardValidOn(new Date(System.currentTimeMillis() + (365L * 24 * 60 * 60 * 1000))).build();
    }
}
