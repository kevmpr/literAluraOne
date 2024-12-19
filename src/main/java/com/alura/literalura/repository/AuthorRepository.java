package com.alura.literalura.repository;

import com.alura.literalura.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByNameContainsIgnoreCase(String name);

    @Query("SELECT a FROM Author a WHERE " +
            "(a.birthYear <= :yearSearched AND (a.deathYear >= :yearSearched OR a.deathYear IS NULL))")
    List<Author> findAuthorsAliveInYear(@Param("yearSearched") int yearSearched);
}
