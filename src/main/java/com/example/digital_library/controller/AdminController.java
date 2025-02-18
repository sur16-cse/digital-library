package com.example.digital_library.controller;

import com.example.digital_library.dto.CreateAdminRequest;
import com.example.digital_library.model.Admin;
import com.example.digital_library.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/create")
    public Admin createAdmin(@RequestBody @Valid CreateAdminRequest createAdminRequest) {
        return adminService.create(createAdminRequest);
    }
}
