package com.hb.bookcollectionapi.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.hb.bookcollectionapi.domain.Author;
import com.hb.bookcollectionapi.domain.AuthorEntity;
import com.hb.bookcollectionapi.repositories.AuthorRepository;
import com.hb.bookcollectionapi.services.AuthorService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorServiceImpl(final AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  @Override
  public Author save(final Author author) {
    final AuthorEntity authorEntity = authorToAuthorEntity(author);
    final AuthorEntity savedAuthorEntity = authorRepository.save(authorEntity);
    return authorEntityToAuthor(savedAuthorEntity);
  }

  private AuthorEntity authorToAuthorEntity(Author author) {
    return AuthorEntity.builder()
        .name(author.getName())
        .email(author.getEmail())
        .build();
  }

  private Author authorEntityToAuthor(AuthorEntity authorEntity) {
    return Author.builder()
        .id(authorEntity.getId())
        .name(authorEntity.getName())
        .email(authorEntity.getEmail())
        .build();
  }

  @Override
  public Optional<Author> findById(Long id) {
    final Optional<AuthorEntity> foundAuthor = authorRepository.findById(id);
    return foundAuthor.map(author -> authorEntityToAuthor(author));
  }

  @Override
  public List<Author> listAuthors() {
    final List<AuthorEntity> foundAuthors = authorRepository.findAll();
    return foundAuthors.stream().map(author -> authorEntityToAuthor(author)).collect(Collectors.toList());
  }

  @Override
  public boolean isAuthorExists(Long id) {
    return authorRepository.existsById(id);
  }

  @Override
  public void deleteAuthorById(Long id) {
    try {
      authorRepository.deleteById(id);

    } catch (final EmptyResultDataAccessException ex) {
      log.debug("Attempted to delete non-existing author", ex);
    }
  }
}
