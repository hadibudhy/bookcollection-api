package com.hb.bookcollectionapi.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hb.bookcollectionapi.domain.Author;
import com.hb.bookcollectionapi.domain.AuthorEntity;
import com.hb.bookcollectionapi.repositories.AuthorRepository;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl underTest;

    @Test
    public void testThatAuthorIsSaved() {
        final Author author = testAuthor();

        final AuthorEntity authorEntity = testAuthorEntity();

        when(authorRepository.save(any(AuthorEntity.class))).thenReturn(authorEntity);

        final Author result = underTest.save(author);
        assertEquals(author, result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoAuthor() {
        final Long id = 1L;
        when(authorRepository.findById(eq(id))).thenReturn(Optional.empty());

        final Optional<Author> result = underTest.findById(id);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnsAuthorWhenExists() {
        final Author author = testAuthor();
        final AuthorEntity authorEntity = testAuthorEntity();

        when(authorRepository.findById(eq(author.getId()))).thenReturn(Optional.of(authorEntity));

        final Optional<Author> result = underTest.findById(author.getId());
        assertEquals(Optional.of(author), result);
    }

    @Test
    public void testListAuthorsReturnsEmptyListWhenNoAuthorsExist() {
        when(authorRepository.findAll()).thenReturn(new ArrayList<>());
        final List<Author> result = underTest.listAuthors();
        assertEquals(0, result.size());
    }

    @Test
    public void testListAuthorsReturnsAuthorsWhenExist() {
        final AuthorEntity authorEntity = testAuthorEntity();
        when(authorRepository.findAll()).thenReturn(List.of(authorEntity));

        final List<Author> result = underTest.listAuthors();
        assertEquals(1, result.size());
    }

    @Test
    public void testIsAuthorExistsReturnsFalseWhenAuthorDoesntExist() {
        when(authorRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isAuthorExists(1L);
        assertEquals(false, result);
    }

    @Test
    public void testDeleteAuthorDeletesAuthor() {
        final Long id = 1L;
        underTest.deleteAuthorById(id);
        verify(authorRepository, times(1)).deleteById(eq(id));
    }

    private Author testAuthor() {
        Author author = new Author();
        author.setId(1L);
        author.setName("John Doe");
        author.setEmail("johndoe@example.com");
        return author;
    }

    private AuthorEntity testAuthorEntity() {
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(1L);
        authorEntity.setName("John Doe");
        authorEntity.setEmail("johndoe@example.com");
        return authorEntity;
    }
}
