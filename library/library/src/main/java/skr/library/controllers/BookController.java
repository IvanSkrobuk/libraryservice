package skr.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import skr.library.models.Book;
import skr.library.services.BookService;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Получение списка всех книг
    @GetMapping
    public String getAllBooks(Model model) {
        List<Book> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books/list";
    }

    // Переход на форму добавления новой книги
    @GetMapping("/new")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/new";
    }

    // Обработка добавления новой книги
    @PostMapping("/new")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/books";
    }
    // Получение книги по ID
    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "books/view";
        } else {
            return "redirect:/books";
        }
    }
    // Изменение информации о книге
    @GetMapping("/{id}/edit")
    public String showEditBookForm(@PathVariable("id") int id, Model model) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "books/edit";
        } else {
            return "redirect:/books";
        }
    }

    // Обработка изменения информации о книге
    @PutMapping("/{id}/edit")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "redirect:/books";
    }


    // Удаление книги
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}
