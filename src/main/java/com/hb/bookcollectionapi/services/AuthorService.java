package com.hb.bookcollectionapi.services;

import java.util.List;
import java.util.Optional;

import com.hb.bookcollectionapi.domain.Author;

public interface  AuthorService {
    
    boolean isAuthorExists(Long id);

    Author save(Author author);

    Optional<Author> findById(Long id);

    List<Author> listAuthors();

    void deleteAuthorById(Long id);
}
