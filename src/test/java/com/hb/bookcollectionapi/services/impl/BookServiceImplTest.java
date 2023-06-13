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

import com.hb.bookcollectionapi.domain.AuthorEntity;
import com.hb.bookcollectionapi.domain.Book;
import com.hb.bookcollectionapi.domain.BookEntity;
import com.hb.bookcollectionapi.repositories.AuthorRepository;
import com.hb.bookcollectionapi.repositories.BookRepository;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl underTest;

    @Test
    public void testThatBookIsSaved() {
        final Book book = testBook();

        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);
        when(authorRepository.findById(eq(book.getAuthorId()))).thenReturn(Optional.of(bookEntity.getAuthor()));

        final Book result = underTest.save(book);
        assertEquals(book, result);
    }

    @Test
    public void testThatFindByIdReturnsEmptyWhenNoBook() {
        final Long id = 1L;
        when(bookRepository.findById(eq(id))).thenReturn(Optional.empty());

        final Optional<Book> result = underTest.findById(id);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists() {
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepository.findById(eq(book.getId()))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = underTest.findById(book.getId());
        assertEquals(Optional.of(book), result);
    }


    @Test
    public void testListBooksReturnsEmptyListWhenNoBooksExist() {
        when(bookRepository.findAll()).thenReturn(new ArrayList<>());
        final List<Book> result = underTest.listBooks();
        assertEquals(0, result.size());
    }

    @Test
    public void testListBooksReturnsBooksWhenExist() {
        final BookEntity bookEntity = testBookEntity();
        when(bookRepository.findAll()).thenReturn(List.of(bookEntity));

        final List<Book> result = underTest.listBooks();
        assertEquals(1, result.size());
    }


     @Test
    public void testIsBookExistsReturnsFalseWhenBookDoesntExist() {
        when(bookRepository.existsById(any())).thenReturn(false);
        final boolean result = underTest.isBookExists(testBook());
        assertEquals(false, result);
    }


    @Test
    public void testDeleteBookDeletesBook() {
        final Long id = 1L;
        underTest.deleteBookById(id);
        verify(bookRepository, times(1)).deleteById(eq(id));
    }

    // Test data and entity conversion methods

    private Book testBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("Sample Book");
        book.setDescription("This is a sample book");
        book.setAuthorId(1L);
        return book;
    }

    private BookEntity testBookEntity() {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setId(1L);
        bookEntity.setTitle("Sample Book");
        bookEntity.setDescription("This is a sample book");
        
        AuthorEntity authorEntity = new AuthorEntity();
        authorEntity.setId(1L);
        authorEntity.setName("John Doe");
        authorEntity.setEmail("johndoe@example.com");
        bookEntity.setAuthor(authorEntity);
        
        return bookEntity;
    }
}
