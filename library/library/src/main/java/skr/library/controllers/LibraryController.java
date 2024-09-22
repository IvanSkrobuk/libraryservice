package skr.library.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skr.library.dto.BookDto;
import skr.library.models.Book;
import skr.library.models.Library;
import skr.library.repositories.BookClient;
import skr.library.services.LibraryService;
import skr.library.util.BookAlreadyExistException;
import skr.library.util.BookErrorResponse;
import skr.library.util.BookNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/library")
@Tag(name = "Library", description = "API для работы с библиотечными книгами")
public class LibraryController {

    private final LibraryService libraryService;
    private final BookClient bookClient;
    private final ModelMapper modelMapper;

    @Autowired
    public LibraryController(LibraryService libraryService, BookClient bookClient , ModelMapper modelMapper) {
        this.libraryService = libraryService;
        this.bookClient = bookClient;
        this.modelMapper = modelMapper;
    }

    private BookDto convertToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    @Operation(summary = "Получить список доступных книг", description = "Возвращает список всех книг, которые доступны для взятия")
    @ApiResponse(responseCode = "200", description = "Список доступных книг успешно получен",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookDto.class)))
    @GetMapping("/available")
    public ResponseEntity<List<BookDto>> getAvailableBooks() {
        List<Library> availableEntries = libraryService.getAvailableBooks();
        List<BookDto> availableBooks = availableEntries.stream()
                .map(entry -> {
                    Book book = bookClient.getBookById(entry.getBookId());
                    return convertToDto(book);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return ResponseEntity.ok(availableBooks);
    }

    @Operation(summary = "Взять книгу", description = "Отмечает книгу как взятую по ID книги")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Книга успешно взята"),
            @ApiResponse(responseCode = "404", description = "Книга не найдена или недоступна для взятия")
    })
    @PostMapping("/take")
    public ResponseEntity<Void> takeBook(@RequestParam("bookId") int bookId) {
        libraryService.takeBook(bookId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/add")
    public ResponseEntity<Void> addBookAsAvailable(@RequestParam("bookId") int bookId) {
        try {
            libraryService.addBookAsAvailable(bookId);
            return ResponseEntity.ok().build();
        } catch (BookAlreadyExistException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteBook(@RequestParam("bookId") int bookId) {
        libraryService.deleteLibrary(bookId);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(BookNotFoundException.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "409", description = "Книга не найдена")
    })
    private ResponseEntity<BookErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {
        BookErrorResponse response = new BookErrorResponse("Book not found", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
