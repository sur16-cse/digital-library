package com.example.digital_library.model;

import com.example.digital_library.model.enums.TransactionStatus;
import com.example.digital_library.model.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @UpdateTimestamp
    private Date updatedOn;
    private String externalTxnId;
    @CreationTimestamp
    private Date transactionTime;
    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;
    private Double fine;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"transactionList"})
    private Book book;

    @ManyToOne
    @JoinColumn
    @JsonIgnoreProperties({"transactionList"})
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;
}
