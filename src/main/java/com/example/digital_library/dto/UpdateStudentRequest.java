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
public class UpdateStudentRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String contact;
    private Date validity;

    Student to() {
        return Student.builder().name(this.name).contact(this.contact).cardValidOn(this.validity).build();
    }
}
