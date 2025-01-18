package com.example.digital_library.repository;

import com.example.digital_library.model.Book;
import com.example.digital_library.model.Student;
import com.example.digital_library.model.Transaction;
import com.example.digital_library.model.enums.TransactionStatus;
import com.example.digital_library.model.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    Transaction findTopByBookAndStudentAndTransactionTypeAndTransactionStatusOrderByTransactionTimeDesc(Book book, Student student, TransactionType transactionType, TransactionStatus transactionStatus);
}
