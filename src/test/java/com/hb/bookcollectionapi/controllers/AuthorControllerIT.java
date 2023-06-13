package com.hb.bookcollectionapi.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.bookcollectionapi.TestData;
import com.hb.bookcollectionapi.domain.Author;
import com.hb.bookcollectionapi.services.AuthorService;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthorControllerIT {

  @Autowired private MockMvc mockMvc;

  @Autowired private AuthorService authorService;

  @Test
  public void testThatAuthorIsCreatedReturnsHTTP201() throws Exception {
    final Author author = TestData.testAuthor();
    final ObjectMapper objectMapper = new ObjectMapper();
    final String authorJson = objectMapper.writeValueAsString(author);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(authorJson))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(author.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(author.getEmail()));
  }

  @Test
public void testThatAuthorIsUpdatedReturnsHTTP200() throws Exception {
  final Author author = TestData.testAuthor();
  final Author savedAuthor = authorService.save(author);

  // Create a new Author object with updated values
  Author updatedAuthor = new Author();
  updatedAuthor.setId(savedAuthor.getId());
  updatedAuthor.setName("Updated Name");
  updatedAuthor.setEmail("updated@example.com");

  final ObjectMapper objectMapper = new ObjectMapper();
  final String authorJson = objectMapper.writeValueAsString(updatedAuthor);

  mockMvc
      .perform(
          MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
              .contentType(MediaType.APPLICATION_JSON)
              .content(authorJson))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedAuthor.getName()))
      .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(updatedAuthor.getEmail()));
}



  @Test
  public void testThatRetrieveAuthorReturns404WhenAuthorNotFound() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/authors/123123123"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testThatRetrieveAuthorReturnsHttp200AndAuthorWhenExists() throws Exception {
    final Author author = TestData.testAuthor();
    final Author savedAuthor = authorService.save(author);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/authors/" + savedAuthor.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(author.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(author.getEmail()));
  }

  @Test
  public void testThatListAuthorsReturnsHttp200EmptyListWhenNoAuthorsExist() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.get("/authors"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("[]"));
  }

  @Test
  public void testThatListAuthorsReturnsHttp200AndAuthorsWhenAuthorsExist() throws Exception {
    final Author author = TestData.testAuthor();
    final Author savedAuthor = authorService.save(author);

    mockMvc
        .perform(MockMvcRequestBuilders.get("/authors"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(savedAuthor.getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(author.getName()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value(author.getEmail()));
  }

  @Test
  public void testThatHttp204IsReturnedWhenAuthorDoesntExist() throws Exception {
    mockMvc
        .perform(MockMvcRequestBuilders.delete("/authors/1"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void testThatHttp204IsReturnedWhenExistingAuthorIsDeleted() throws Exception {
    final Author author = TestData.testAuthor();
    final Author savedAuthor = authorService.save(author);

    mockMvc
        .perform(MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId()))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }
}
