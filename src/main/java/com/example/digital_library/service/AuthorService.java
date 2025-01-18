package com.example.digital_library.service;

import com.example.digital_library.model.Author;
import com.example.digital_library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public Author createOrGet(Author author) {
        Author authorFromDB = authorRepository.findByEmail(author.getEmail());
        if (authorFromDB != null) {
            return authorFromDB;
        }
        return this.authorRepository.save(author);
    }

    public List<Author> getAll() {
        return this.authorRepository.findAll();
    }
}
