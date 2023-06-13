package com.hb.bookcollectionapi.services;

import java.util.List;
import java.util.Optional;

import com.hb.bookcollectionapi.domain.Book;

public interface  BookService {
    
    boolean isBookExists(Book book);

    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> listBooks();

    void deleteBookById(Long id);
}
