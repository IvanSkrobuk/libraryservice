package skr.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import skr.library.models.Book;
import skr.library.models.Library;
import skr.library.services.BookService;
import skr.library.services.LibraryService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;
    private final BookService bookService;
    @Autowired
    public LibraryController(LibraryService libraryService, BookService bookService) {
        this.libraryService = libraryService;
        this.bookService = bookService;
    }
    //Получение доступных книг
    @GetMapping()
    public String getAvailableBooks(Model model) {
        List<Library> availableEntries = libraryService.getAvailableBooks();
        List<Book> availableBooks = new ArrayList<>();

        for (Library entry : availableEntries) {
            Book book = bookService.getBookById(entry.getBookId());
            if (book != null) {
                availableBooks.add(book);
            }
        }
        model.addAttribute("availableBooks", availableBooks);
        return "books/library/available";
    }

    // Обработка взятия книги
    @PostMapping("/take")
    public String takeBook(@RequestParam("bookId") int bookId) {
        libraryService.takeBook(bookId);
        return "redirect:/library";
    }
}
