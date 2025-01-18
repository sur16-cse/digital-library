package com.example.digital_library.repository;

import com.example.digital_library.model.Author;
import com.example.digital_library.model.Book;
import com.example.digital_library.model.Student;
import com.example.digital_library.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByNameAndStudentIsNull(String name);

    List<Book> findByName(String name);

    List<Book> findByAuthor(Author author);

    List<Book> findByGenre(Genre genre);

    @Modifying
    @Transactional
    @Query("update  Book b set b.student=?2 where b.id=?1 and b.student is null ")
    void assignBookToStudent(int bookId, Student student);

    @Modifying
    @Transactional
    @Query("update  Book b set b.student=null where b.id=?1")
    void unassignBookToStudent(int bookId);
}
