package com.hb.bookcollectionapi.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hb.bookcollectionapi.domain.Author;
import com.hb.bookcollectionapi.services.AuthorService;

@RestController
public class AuthorController {

  private final AuthorService authorService;

  public AuthorController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @PostMapping("/authors")
  public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
    final Author savedAuthor = authorService.save(author);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
  }

  @GetMapping("/authors/{id}")
  public ResponseEntity<Author> retrieveAuthor(@PathVariable Long id) {
    final Optional<Author> foundAuthor = authorService.findById(id);
    return foundAuthor
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping("/authors")
  public ResponseEntity<List<Author>> listAuthors() {
    final List<Author> foundAuthors = authorService.listAuthors();
    return ResponseEntity.ok(foundAuthors);
  }

  @PutMapping("/authors/{id}")
  public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
    author.setId(id);
    final Author savedAuthor = authorService.save(author);
    return ResponseEntity.ok(savedAuthor);
  }

  @DeleteMapping("/authors/{id}")
  public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
    authorService.deleteAuthorById(id);
    return ResponseEntity.noContent().build();
  }
}
