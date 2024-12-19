package com.alura.literalura.model;

import org.antlr.v4.runtime.InputMismatchException;

import java.util.Arrays;

public enum Language {
    SPANISH("es", "Spanish"),
    ENGLISH("en", "English"),
    PORTUGUESE("pt", "Portuguese"),
    FRENCH("fr", "French"),
    ITALIAN("it",  "Italian"),
    GERMAN("de",  "German"),;

    private String languageGutendex;
    private String languageEnglish;

    Language(String languageGutendex, String languageEnglish){
        this.languageGutendex = languageGutendex;
        this.languageEnglish = languageEnglish;
    }

    public static Language fromGutendex(String text) {
        for (Language language : Language.values()) {
            if (language.languageGutendex.equals(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("No language found: " + text);
    }

   public static Language fromEnglish(String text) {
        try{
            for (Language language : Language.values()) {
                if (language.languageEnglish.toLowerCase().contains(text.toLowerCase())) {
                    return language;
                }
            }
        } catch (Error e){
            throw new IllegalArgumentException("No language found: " + text + "\n Available languages: " +
                    Arrays.toString(Language.values()));

       }
       return null;
   }

    public String getLanguageGutendex() {
        return languageGutendex;
    }

    public void setLanguageGutendex(String languageGutendex) {
        this.languageGutendex = languageGutendex;
    }

    public String getLanguageEnglish() {
        return languageEnglish;
    }

    public void setLanguageEnglish(String languageEnglish) {
        this.languageEnglish = languageEnglish;
    }
}
