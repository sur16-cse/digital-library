package com.example.digital_library.service;

import com.example.digital_library.dto.CreateBookRequest;
import com.example.digital_library.dto.SearchBookRequest;
import com.example.digital_library.model.Author;
import com.example.digital_library.model.Book;
import com.example.digital_library.model.Student;
import com.example.digital_library.model.enums.Genre;
import com.example.digital_library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorService authorService;


    public Book create(CreateBookRequest createBookRequest) {
        Book book = createBookRequest.to();
        Author author = authorService.createOrGet(book.getAuthor());
        book.setAuthor(author);
        return bookRepository.save(book);
    }

    public void assignBookToStudent(Book book, Student student) {
        bookRepository.assignBookToStudent(book.getId(), student);
    }

    public void unassignBookFromStudent(Book book) {
        bookRepository.unassignBookToStudent(book.getId());
    }

    public Book delete(int bookId) {
        Book book = this.bookRepository.findById(bookId).orElse(null);
        bookRepository.deleteById(bookId);
        return book;
    }

    public List<Book> search(SearchBookRequest searchBookRequest) throws Exception {
        boolean isValidRequest = searchBookRequest.validate();
        if (!isValidRequest) {
            throw new Exception("Invalid Request");
        }
        String sql = "select b from Book b where b.searchKey searchOperator searchValue";
        switch (searchBookRequest.getSearchKey()) {
            case "name":
                if (searchBookRequest.isAvailable()) {
                    return bookRepository.findByNameAndStudentIsNull(searchBookRequest.getSearchValue());
                }
                return bookRepository.findByName(searchBookRequest.getSearchValue());
//            case "author":
//                return bookRepository.findByAuthor(searchBookRequest.getSearchValue());
            case "genre":
                return bookRepository.findByGenre(Genre.valueOf(searchBookRequest.getSearchValue()));
            case "id": {
                Book book = bookRepository.findById(Integer.parseInt(searchBookRequest.getSearchValue())).orElse(null);
                return Collections.singletonList(book);
            }

            default:
                throw new Exception("Invalid Search Key");
        }
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
