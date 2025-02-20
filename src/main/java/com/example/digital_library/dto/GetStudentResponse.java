package com.example.digital_library.dto;

import com.example.digital_library.model.Student;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class GetStudentResponse implements Serializable {
    private int id;
    private String name;
    private String contact;
    private Date createdOn;
    private Date updatedOn;
    private Date cardValidOn;

    public GetStudentResponse(Student student) {
        this.id = student.getId();
        this.name = student.getName();
        this.contact = student.getContact();
        this.createdOn = student.getCreatedOn();
        this.updatedOn = student.getUpdatedOn();
        this.cardValidOn = student.getCardValidOn();
    }
}
