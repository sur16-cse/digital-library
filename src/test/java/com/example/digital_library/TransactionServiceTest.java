package com.example.digital_library;

import com.example.digital_library.dto.SearchBookRequest;
import com.example.digital_library.model.Book;
import com.example.digital_library.model.Student;
import com.example.digital_library.model.Transaction;
import com.example.digital_library.model.enums.TransactionStatus;
import com.example.digital_library.repository.TransactionRepository;
import com.example.digital_library.service.BookService;
import com.example.digital_library.service.StudentService;
import com.example.digital_library.service.TransactionService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
        String extTransactionId = UUID.randomUUID().toString();

        List<Book> books = Collections.singletonList(Book.builder().id(bookId).name(bookName).build());
        Student student = Student.builder().id(studentId).name("Peter").build();
        SearchBookRequest searchBookRequest = SearchBookRequest.builder().searchKey("name").searchValue(bookName).operator("=").available(true).build();
        Transaction transaction = Transaction.builder().externalTxnId(extTransactionId).book(books.get(0)).student(student).build();

        Mockito.when(bookService.search(Mockito.any(SearchBookRequest.class))).thenReturn(books);
        Mockito.when(studentService.get(studentId)).thenReturn(student);
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);

        String txnId = transactionService.issueTxn(bookName, studentId);

        Assert.assertEquals(extTransactionId, txnId);

        Mockito.verify(studentService, Mockito.times(1)).get(studentId);
        Mockito.verify(bookService, Mockito.times(1)).search(Mockito.any(SearchBookRequest.class));
        Mockito.verify(transactionRepository, Mockito.times(2)).save(Mockito.any(Transaction.class));
        Mockito.verify(bookService, Mockito.times(1)).assignBookToStudent(Mockito.any(), Mockito.any());
    }

    @Test
    public void issueTxn_SuccessScenario_Test() throws Exception {
        // Setup
        String bookName = "ABC";
        int studentId = 1;
        String initialTxnId = UUID.randomUUID().toString();
        String finalTxnId = UUID.randomUUID().toString();

        Book book = Book.builder().id(1).name(bookName).build();
        Student student = Student.builder().id(studentId).name("Peter").build();

        // Initial transaction with PENDING status
        Transaction initialTransaction = Transaction.builder()
                .externalTxnId(initialTxnId)
                .book(book)
                .student(student)
                .transactionStatus(TransactionStatus.PENDING)
                .build();

        // Final transaction with SUCCESS status
        Transaction finalTransaction = Transaction.builder()
                .externalTxnId(finalTxnId)
                .book(book)
                .student(student)
                .transactionStatus(TransactionStatus.SUCCESS)
                .build();

        // Mock behaviors
        Mockito.when(bookService.search(Mockito.any(SearchBookRequest.class)))
                .thenReturn(Collections.singletonList(book));
        Mockito.when(studentService.get(studentId)).thenReturn(student);

        // Mock repository save calls with different returns for initial and final saves
        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class)))
                .thenReturn(initialTransaction)
                .thenReturn(finalTransaction);

        // Execute
        String resultTxnId = transactionService.issueTxn(bookName, studentId);

        // Verify
        Assert.assertEquals(finalTxnId, resultTxnId);
        Mockito.verify(transactionRepository, Mockito.times(2)).save(Mockito.any(Transaction.class));
    }
}
