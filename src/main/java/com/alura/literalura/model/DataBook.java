package com.alura.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DataBook(
    @JsonAlias("title") String title,
    @JsonAlias("authors") List<DataAuthor> authors,
    @JsonAlias("language") String language,
    @JsonAlias("download_count") Long downloadCount){
}
