package com.hb.bookcollectionapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hb.bookcollectionapi.domain.BookEntity;

@Repository
public interface BookRepository
    extends JpaRepository<BookEntity, Long> {}