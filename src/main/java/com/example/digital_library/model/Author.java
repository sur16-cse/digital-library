package com.example.digital_library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    private String country;
    private Integer age;
    @OneToMany(mappedBy = "author")
    @JsonIgnoreProperties({"author"})
    private List<Book> books;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
}
