package com.example.digital_library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String contact;
    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private List<Book> books;
    @OneToMany(mappedBy = "student")
    @JsonIgnoreProperties({"student"})
    private List<Transaction> transactionList;
    @OneToOne
    @JoinColumn(name = "username")
    private SecuredUser securedUser;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
    private Date cardValidOn;
}
