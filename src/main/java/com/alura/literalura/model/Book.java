package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String title;
    @Enumerated(EnumType.STRING)
    private Language language;
    private Long downloadCount;
    @ManyToOne
    private Author author;

    public Book(){}

    public Book(DataBook dataBook){
        this.title = dataBook.title();

        List<DataAuthor> dataAuthors = dataBook.authors().stream().limit(1).collect(Collectors.toList());
        if(!dataAuthors.isEmpty()){
            DataAuthor dataAuthor = dataAuthors.get(0);
            this.author = new Author(dataAuthor);
        }

        if(dataBook.languages() != null && !dataBook.languages().isEmpty()){
            this.language = Language.fromGutendex(dataBook.languages().get(0));
        }

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

    public Language getLanguage() {return language;}

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Long getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Long downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return """
               Title: %s
               Author: %s
               Language: %s
               Download Count: %d
               """.formatted(
                        this.title,
                        this.author.getName(),
                        this.language.getLanguageEnglish().toString(),
                        this.downloadCount);
    }
}
