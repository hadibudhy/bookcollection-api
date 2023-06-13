package com.hb.bookcollectionapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.hb.bookcollectionapi.domain.AuthorEntity;

@Repository
public interface AuthorRepository
    extends JpaRepository<AuthorEntity, Long> {}