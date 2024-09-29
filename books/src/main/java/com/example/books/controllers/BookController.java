package com.example.books.controllers;

import com.example.books.dto.BookDto;
import com.example.books.mapper.BookMapper;
import com.example.books.models.Book;
import com.example.books.services.BookService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "API для работы с книгами")
public class BookController {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }


    @Operation(summary = "Получить список всех книг", description = "Возвращает полный список книг в библиотеке")
    @ApiResponse(responseCode = "200", description = "Список книг успешно получен",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookDto> bookDtos = books.stream()
                .map(bookMapper::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookDtos);
    }


    @Operation(summary = "Получить книгу по ID", description = "Возвращает книгу по идентификатору")
    @ApiResponse(responseCode = "200", description = "Книга найдена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    @ApiResponse(responseCode = "404", description = "Книга не найдена")
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") int id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(bookMapper.convertToDto(book));
    }


    @Operation(summary = "Добавить новую книгу", description = "Добавляет новую книгу в библиотеку")
    @ApiResponse(responseCode = "200", description = "Книга успешно добавлена",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    @ApiResponse(responseCode = "409", description = "Книга с таким ISBN уже существует")
    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        Book book = bookMapper.convertToEntity(bookDto);
        Book savedBook = bookService.addBook(book);
        return ResponseEntity.ok(bookMapper.convertToDto(savedBook));
    }

    @Operation(summary = "Получить книгу по ISBN", description = "Возвращает книгу по указанному ISBN")
    @ApiResponse(responseCode = "200", description = "Книга успешно найдена")
    @ApiResponse(responseCode = "404", description = "Книга не найдена")
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<BookDto> getBookByIsbn(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbn(isbn);
        return ResponseEntity.ok(bookMapper.convertToDto(book));
    }

    @Operation(summary = "Обновить информацию о книге", description = "Обновляет информацию о книге по ID")
    @ApiResponse(responseCode = "200", description = "Книга успешно обновлена",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    @ApiResponse(responseCode = "404", description = "Книга не найдена")

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") int id, @RequestBody BookDto bookDto) {
        Book updatedBook = bookMapper.convertToEntity(bookDto);
        Book updatedEntity = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(bookMapper.convertToDto(updatedEntity));
    }



    @Operation(summary = "Удалить книгу", description = "Удаляет книгу по ID")
    @ApiResponse(responseCode = "204", description = "Книга успешно удалена")
    @ApiResponse(responseCode = "404", description = "Книга не найдена")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

}
