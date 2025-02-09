package com.example.digital_library.controller;

import com.example.digital_library.dto.CreateBookRequest;
import com.example.digital_library.dto.DeleteBookResponse;
import com.example.digital_library.dto.SearchBookRequest;
import com.example.digital_library.model.Book;
import com.example.digital_library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping
    public Book createBook(@RequestBody @Valid CreateBookRequest bookRequest) {
        return bookService.create(bookRequest);
    }

    @DeleteMapping("/{bookId}")
    public DeleteBookResponse deleteBook(@PathVariable("bookId") Integer bookId) {
        return DeleteBookResponse.from(bookService.delete(bookId));
    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAll();
    }

    @GetMapping("/search")
    public List<Book> getBookBySearch(@RequestBody @Valid SearchBookRequest searchBookRequest) throws Exception {
        return bookService.search(searchBookRequest);
    }
}
