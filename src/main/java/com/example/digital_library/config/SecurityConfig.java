package com.example.digital_library.config;

import com.example.digital_library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/student/id/**").hasAnyAuthority("get-student-profile")
                .antMatchers(HttpMethod.GET, "/student/**").hasAnyAuthority("get-student-details")
                .antMatchers(HttpMethod.POST, "/student/**").permitAll()
                .antMatchers("/student/**").hasAnyAuthority("update-student-account")
                .antMatchers(HttpMethod.GET, "/book/**").hasAnyAuthority("get-book-details")
                .antMatchers("/book/**").hasAnyAuthority("update-book")
                .antMatchers("/transaction/**").hasAnyAuthority("book-transaction")
                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("add-admin")
                .and().formLogin();

    }

    @Bean
    PasswordEncoder getPE() {
        return new BCryptPasswordEncoder();
    }
}
