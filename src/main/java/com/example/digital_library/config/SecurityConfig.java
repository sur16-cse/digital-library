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
        http.httpBasic().and().authorizeHttpRequests()
                .antMatchers("/student/id/**").hasAnyAuthority("student")
                .antMatchers(HttpMethod.GET, "/student/**").hasAnyAuthority("admin")
                .antMatchers(HttpMethod.POST, "/student/**").permitAll()
                .antMatchers("/student/**").hasAnyAuthority("student")
                .antMatchers(HttpMethod.GET, "/book/**").hasAnyAuthority("admin", "student")
                .antMatchers("/book/**").hasAnyAuthority("admin")
                .antMatchers("/transaction/**").hasAnyAuthority("student")
                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority("admin")
                .and().formLogin();

    }

    @Bean
    PasswordEncoder getPE() {
        return new BCryptPasswordEncoder();
    }
}
