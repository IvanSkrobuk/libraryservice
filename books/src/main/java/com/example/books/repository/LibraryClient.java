package com.example.books.repository;


import com.example.books.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "library-api", url = "http://localhost:8082") // Замените на ваш URL
public interface LibraryClient {

    @PostMapping("/api/library/add")
    void addBookAsAvailable(@RequestParam("bookId") int bookId);

    @DeleteMapping
    List<BookDto> deleteLibrary(@RequestParam("bookId") int bookId);
}
