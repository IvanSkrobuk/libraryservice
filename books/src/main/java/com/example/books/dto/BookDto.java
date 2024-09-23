package com.example.books.dto;

import jakarta.validation.constraints.NotBlank;

public class BookDto {

    @NotBlank(message = "ISBN не может быть пустым")
    private String isbn;

    @NotBlank(message = "Название книги не может быть пустым")
    private String title;

    private String genre;

    private String description;

    @NotBlank(message = "Имя автора не может быть пустым")
    private String author;

    public  String getIsbn() {
        return isbn;
    }

    public void setIsbn( String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor( String author) {
        this.author = author;
    }
}
