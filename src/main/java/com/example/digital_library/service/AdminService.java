package com.example.digital_library.service;

import com.example.digital_library.dto.CreateAdminRequest;
import com.example.digital_library.model.Admin;
import com.example.digital_library.model.SecuredUser;
import com.example.digital_library.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${admin.authorities}")
    String authorities;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserService userService;

    public Admin create(CreateAdminRequest createAdminRequest) {
        Admin admin = createAdminRequest.to();
        SecuredUser securedUser = admin.getSecuredUser();
        securedUser.setPassword(passwordEncoder.encode(securedUser.getPassword()));
        securedUser.setAuthorities(authorities);
        securedUser = userService.save(securedUser);
        admin.setSecuredUser(securedUser);
        return adminRepository.save(admin);
    }
}
