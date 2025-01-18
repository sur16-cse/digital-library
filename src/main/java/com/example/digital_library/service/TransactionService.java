package com.example.digital_library.service;

import com.example.digital_library.dto.SearchBookRequest;
import com.example.digital_library.model.Book;
import com.example.digital_library.model.Student;
import com.example.digital_library.model.Transaction;
import com.example.digital_library.model.enums.TransactionStatus;
import com.example.digital_library.model.enums.TransactionType;
import com.example.digital_library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {
    @Autowired
    StudentService studentService;

    @Autowired
    BookService bookService;

    @Autowired
    TransactionRepository transactionRepository;

    @Value("${student.issue.number_of_days}")
    private int number_of_days_for_book_issuance;
    @Value("${student.issue.max_books}")
    private int max_books_for_issue;

    public String issueTxn(String bookName, int studentId) throws Exception {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = bookService.search(SearchBookRequest.builder().searchKey("name").searchValue(bookName).operator("=").available(true).build());
        } catch (Exception e) {
            throw new RuntimeException("Book not found");
        }
        Student student = studentService.get(studentId);
        if (student.getBooks() != null && student.getBooks().size() >= max_books_for_issue) {
            throw new Exception("Book limit exceeded");
        }
        if (bookList.isEmpty()) {
            throw new RuntimeException("Not able to find any book with name " + bookName);
        }
        Book book = bookList.get(0);
        Transaction transaction = Transaction.builder().externalTxnId(UUID.randomUUID().toString()).transactionType(TransactionType.ISSUED).student(student).book(book).transactionStatus(TransactionStatus.PENDING).build();
        transaction = transactionRepository.save(transaction);

        try {
            book.setStudent(student);
            bookService.assignBookToStudent(book, student);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        } finally {
            return transactionRepository.save(transaction).getExternalTxnId();
        }

    }

    public String returnTxn(int bookId, int studentId) {
        Book book;
        try {
            book = this.bookService.search(SearchBookRequest.builder().searchKey("id").searchValue(String.valueOf(bookId)).operator("=").build()
            ).get(0);
        } catch (Exception e) {
            throw new RuntimeException("Not able to find any book with id " + bookId);
        }

        if (book.getStudent() == null || book.getStudent().getId() != studentId) {
            throw new RuntimeException("Book is not assigned to this student" + studentId);
        }
        Student student = studentService.get(studentId);
        Transaction transaction = Transaction.builder().externalTxnId(UUID.randomUUID().toString()).transactionType(TransactionType.RETURNED).student(student).book(book).transactionStatus(TransactionStatus.PENDING).build();
        transaction = transactionRepository.save(transaction);
        Transaction issueTransaction = transactionRepository.findTopByBookAndStudentAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(book, studentService.get(studentId), TransactionType.ISSUED, TransactionStatus.SUCCESS);
        long issueTxnMillis = issueTransaction.getTransactionTime().getTime();
        long currentTimeMillis = System.currentTimeMillis();
        long timeDifferenceInMillis = currentTimeMillis - issueTxnMillis;
        long timeDifferenceInDays = TimeUnit.DAYS.convert(timeDifferenceInMillis, TimeUnit.MILLISECONDS);
        Double fine = 0.0;
        if (timeDifferenceInDays > number_of_days_for_book_issuance) {
            fine = (double) ((timeDifferenceInDays - number_of_days_for_book_issuance) * 10);
        }
        try {
            book.setStudent(null);
            bookService.unassignBookFromStudent(book);
            transaction.setTransactionStatus(TransactionStatus.SUCCESS);
            return transaction.getExternalTxnId();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.setTransactionStatus(TransactionStatus.FAILED);
        } finally {
            transaction.setFine(fine);
            return transactionRepository.save(transaction).getExternalTxnId();
        }

        //Fine calculation

    }
}
