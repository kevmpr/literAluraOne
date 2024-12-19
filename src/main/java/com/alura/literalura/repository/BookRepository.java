package com.alura.literalura.repository;

import com.alura.literalura.model.Book;
import com.alura.literalura.model.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitleContainsIgnoreCase(String title);

    @Query("SELECT b FROM Book b WHERE b.language = :language")
    List<Book> showBooksByLanguage(@Param("language") Language language);

    @Query("SELECT b FROM Book b WHERE LOWER(b.author.name) LIKE LOWER(CONCAT('%', :author, '%'))")
    List<Book> showBooksByAuthor(@Param("author") String author);

    List<Book> findTop10ByOrderByDownloadCountDesc();
}
