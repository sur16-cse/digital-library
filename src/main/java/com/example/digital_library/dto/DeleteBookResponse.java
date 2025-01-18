package com.example.digital_library.dto;

import com.example.digital_library.model.Author;
import com.example.digital_library.model.Book;
import com.example.digital_library.model.Student;
import com.example.digital_library.model.enums.Genre;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class DeleteBookResponse {
    private int id;
    private String name;
    private Integer pages;
    private Genre genre;
    private Student student;
    private Author author;
    private Date createdOn;
    private Date updatedOn;

    public static DeleteBookResponse from(Book book) {
        return DeleteBookResponse.builder().id(book.getId()).name(book.getName()).pages(book.getPages()).genre(book.getGenre()).createdOn(book.getCreatedOn()).updatedOn(book.getUpdatedOn()).student(book.getStudent()).author(book.getAuthor()).build();
    }
}
