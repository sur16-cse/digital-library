package com.example.digital_library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SecuredUser implements UserDetails {
    private final static String AUTHORITIES_DELIMITER = "::";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;
    private String authorities;

    @Getter
    @OneToOne(mappedBy = "securedUser")
    @JsonIgnoreProperties(value = {"securedUser"})
    private Student student;

    @Getter
    @OneToOne(mappedBy = "securedUser")
    @JsonIgnoreProperties(value = {"securedUser"})
    private Admin admin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String[] authoritiesList = this.authorities.split(AUTHORITIES_DELIMITER);
        return Arrays.stream(authoritiesList)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}