package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    private String author;
    private List<String> language;
    private Long downloadCount;
    @ManyToOne
    private Author authorBook;

    public Book(){}

    public Book(DataBook dataBook){
        this.title = dataBook.title();
        this.author = String.valueOf(dataBook.authors().get(0).name());
        this.language = dataBook.language();
        this.downloadCount = dataBook.downloadCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<String> getLanguage() {
        return language;
    }

    public void setLanguage(List<String> language) {
        this.language = language;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return """
               Title: %s
               Author: %s
               Language: %s
               Download Count: %d
               """.formatted(this.title, this.author, this.language, this.downloadCount);
    }
}
