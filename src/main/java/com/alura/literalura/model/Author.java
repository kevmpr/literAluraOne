package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalInt;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer birthYear;
    private Integer deathYear;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Book> books;

    public Author(){}

    public Author(DataAuthor dataAuthor){
        this.name = dataAuthor.name();
        this.birthYear = dataAuthor.birthYear();
        this.deathYear = dataAuthor.deathYear();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        books.forEach(b -> b.setAuthor(this));
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    @Override
    public String toString() {
        return """
               Name: %s
               Birth Year: %d
               Death Year: %d
               """.formatted(
                        this.name,
                        this.birthYear != null ? birthYear : "Unknown",
                        this.deathYear != null ? deathYear : "Unknown");
    }
}
