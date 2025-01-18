package com.example.digital_library.model;

import com.example.digital_library.model.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Integer pages;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"books"})
    private Student student;
    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"books"})
    private Author author;
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"book"})
    private List<Transaction> transactionList;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
}
