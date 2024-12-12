package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

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
    //@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)

    public Author(){}

    public Author(DataAuthor dataAuthor){
        this.name = dataAuthor.name();
        this.birthYear = OptionalInt.of(Integer.valueOf(dataAuthor.birthYear())).orElse(0);
        this.deathYear = OptionalInt.of(Integer.valueOf(dataAuthor.deathYear())).orElse(0);
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
}