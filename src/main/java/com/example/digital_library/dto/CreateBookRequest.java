package com.example.digital_library.dto;

import com.example.digital_library.model.Author;
import com.example.digital_library.model.Book;
import com.example.digital_library.model.enums.Genre;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class CreateBookRequest {
    @NotBlank
    private String name;
    @NotNull
    private Genre genre;
    @NotNull
    private Integer pages;
    @NotBlank
    private String authorName;
    private String authorCountry;
    @NotBlank
    private String authorEmail;

    public Book to() {
        return Book.builder()
                .name(this.name)
                .genre(this.genre)
                .pages(this.pages)
                .author(
                        Author.builder().name(this.authorName).country(this.authorCountry).email(this.authorEmail).build()
                ).build();
    }
}
