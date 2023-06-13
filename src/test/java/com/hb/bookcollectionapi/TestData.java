package com.hb.bookcollectionapi;

import com.hb.bookcollectionapi.domain.Author;
import com.hb.bookcollectionapi.domain.AuthorEntity;
import com.hb.bookcollectionapi.domain.Book;
import com.hb.bookcollectionapi.domain.BookEntity;

public final class TestData {

  private TestData() {}

  public static Book testBook() {
    return Book.builder()
        .id(1L)
        .title("The Waves")
        .description("A novel by Virginia Woolf")
        .authorId(1L)
        .build();
  }

  public static BookEntity testBookEntity() {
    return BookEntity.builder()
        .id(1L)
        .title("The Waves")
        .description("A novel by Virginia Woolf")
        .author(TestData.testAuthorEntity())
        .build();
  }

  public static Author testAuthor() {
    return Author.builder()
        .id(1L)
        .name("Virginia Woolf")
        .email("virginia@example.com")
        .build();
  }

  public static AuthorEntity testAuthorEntity() {
    return AuthorEntity.builder()
        .id(1L)
        .name("Virginia Woolf")
        .email("virginia@example.com")
        .build();
  }
}

