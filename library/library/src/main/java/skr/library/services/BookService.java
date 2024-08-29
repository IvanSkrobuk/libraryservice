package skr.library.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skr.library.models.Book;
import skr.library.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final LibraryService libraryService;

    @Autowired
    public BookService(BookRepository bookRepository, LibraryService libraryService) {
        this.bookRepository = bookRepository;
        this.libraryService = libraryService;
    }

    // Получение списка всех книг
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Получение книги по ID
    @Transactional(readOnly = true)
    public Book getBookById(int id) {
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    // Получение книги по ISBN
    @Transactional(readOnly = true)
    public Book getBookByIsbn(String isbn) {
        Optional<Book> foundBook = bookRepository.findByIsbn(isbn);
        return foundBook.orElse(null);
    }

    // Добавление новой книги(свободной)
    @Transactional
    public void addBook(Book book) {
        Book savedBook = bookRepository.save(book);
        libraryService.addBookAsAvailable(savedBook.getId());
    }

    // Изменение существующей книги
    public void updateBook(int id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setIsbn(updatedBook.getIsbn());
            book.setTitle(updatedBook.getTitle());
            book.setGenre(updatedBook.getGenre());
            book.setDescription(updatedBook.getDescription());
            book.setAuthor(updatedBook.getAuthor());
            bookRepository.save(book);
        }
    }
    // Удаление книги
    @Transactional
    public void deleteBook(int id) {
        bookRepository.deleteById(id);
        libraryService.deleteLibrary(id);
    }
}
