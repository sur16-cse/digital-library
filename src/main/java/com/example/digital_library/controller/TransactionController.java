package com.example.digital_library.controller;

import com.example.digital_library.model.SecuredUser;
import com.example.digital_library.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/issue")
    public String issueTransaction(@RequestParam("name") String name) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return transactionService.issueTxn(name, studentId);
    }

    @PostMapping("/return")
    public String returnTransaction(@RequestParam("bookId") int bookId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SecuredUser securedUser = (SecuredUser) authentication.getPrincipal();
        int studentId = securedUser.getStudent().getId();
        return transactionService.returnTxn(bookId, studentId);
    }
}
