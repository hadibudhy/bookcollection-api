package com.hb.bookcollectionapi.controllers;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hb.bookcollectionapi.domain.Book;
import com.hb.bookcollectionapi.services.AuthorService;
import com.hb.bookcollectionapi.services.BookService;

@RestController
public class BookController {

  private final BookService bookService;
  private final AuthorService authorService;

  @Autowired
  public BookController(final BookService bookService, final AuthorService authorService) {
    this.bookService = bookService;
    this.authorService = authorService;
  }

  @PutMapping(path = "/books/{id}")
  public ResponseEntity<Book> createUpdateBook(
      @PathVariable final Long id, @RequestBody final Book book) {
    book.setId(id);

    Long authorId = book.getAuthorId();
    if (!authorService.isAuthorExists(authorId)) {
      return ResponseEntity.badRequest().build();
    }

    final boolean isBookExists = bookService.isBookExists(book);
    final Book savedBook = bookService.save(book);

    if (isBookExists) {
      return ResponseEntity.ok(savedBook);
    } else {
      return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }
  }

  @GetMapping(path = "/books/{id}")
  public ResponseEntity<Book> retrieveBook(@PathVariable final Long id) {
    final Optional<Book> foundBook = bookService.findById(id);
    return foundBook
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping(path = "/books")
  public ResponseEntity<List<Book>> listBooks() {
    final List<Book> foundBooks = bookService.listBooks();
    return ResponseEntity.ok(foundBooks);
  }

  @DeleteMapping(path = "/books/{id}")
  public ResponseEntity<Void> deleteBook(@PathVariable final Long id) {
    bookService.deleteBookById(id);
    return ResponseEntity.noContent().build();
  }

}
