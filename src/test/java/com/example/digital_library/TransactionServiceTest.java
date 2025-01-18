package com.example.digital_library;

import com.example.digital_library.model.Book;
import com.example.digital_library.model.Student;
import com.example.digital_library.repository.TransactionRepository;
import com.example.digital_library.service.BookService;
import com.example.digital_library.service.StudentService;
import com.example.digital_library.service.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private BookService bookService;
    @Mock
    private StudentService studentService;

    @Test
    public void issueTxn_Test() throws Exception {
        String bookName = "ABC";
        int bookId = 1;
        int studentId = 1;
        List<Book> books = Collections.singletonList(Book.builder().id(bookId).name(bookName).build());
        Student student = Student.builder().id(studentId).build();
        Mockito.when(bookService.search(Mockito.any())).thenReturn(books);
        Mockito.when(studentService.get(studentId)).thenReturn(student);
        transactionService.issueTxn(bookName, studentId);
    }
}
