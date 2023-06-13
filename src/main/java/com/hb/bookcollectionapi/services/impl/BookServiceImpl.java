package com.hb.bookcollectionapi.services.impl;

import com.hb.bookcollectionapi.domain.AuthorEntity;
import com.hb.bookcollectionapi.domain.Book;
import com.hb.bookcollectionapi.domain.BookEntity;
import com.hb.bookcollectionapi.repositories.AuthorRepository;
import com.hb.bookcollectionapi.repositories.BookRepository;
import com.hb.bookcollectionapi.services.BookService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;
  private final AuthorRepository authorRepository;

  @Autowired
  public BookServiceImpl(final BookRepository bookRepository, final AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.authorRepository = authorRepository;
  }

  @Override
  public Book save(final Book book) {
    final BookEntity bookEntity = bookToBookEntity(book);
    final BookEntity savedBookEntity = bookRepository.save(bookEntity);
    return bookEntityToBook(savedBookEntity);
  }

  private BookEntity bookToBookEntity(Book book) {
  AuthorEntity authorEntity = authorRepository.findById(book.getAuthorId())
      .orElseThrow(() -> new NoSuchElementException("Author with ID " + book.getAuthorId() + " not found"));

  return BookEntity.builder()
      .id(book.getId())
      .title(book.getTitle())
      .description(book.getDescription())
      .author(authorEntity)
      .build();
}


  private Book bookEntityToBook(BookEntity bookEntity) {
    return Book.builder()
        .id(bookEntity.getId())
        .title(bookEntity.getTitle())
        .description(bookEntity.getDescription())
        .authorId(bookEntity.getAuthor().getId())
        .build();
  }

  @Override
  public Optional<Book> findById(Long id) {
    final Optional<BookEntity> foundBook = bookRepository.findById(id);
    return foundBook.map(book -> bookEntityToBook(book));
  }

  @Override
  public List<Book> listBooks() {
    final List<BookEntity> foundBooks = bookRepository.findAll();
    return foundBooks.stream().map(book -> bookEntityToBook(book)).collect(Collectors.toList());
  }

  @Override
  public boolean isBookExists(Book book) {
    return bookRepository.existsById(book.getId());
  }

  @Override
  public void deleteBookById(Long id) {
    try {
      bookRepository.deleteById(id);
    } catch (final EmptyResultDataAccessException ex) {
      log.debug("Attempted to delete non-existing book", ex);
    }
  }
}


