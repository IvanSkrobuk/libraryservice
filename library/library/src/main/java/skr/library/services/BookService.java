package skr.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skr.library.models.Book;
import skr.library.repositories.BookRepository;
import skr.library.util.BookAlreadyExistException;
import skr.library.util.BookNotFoundException;

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
        Optional<Book> book = bookRepository.findById(id);
        return book.orElseThrow(BookNotFoundException::new);
    }

    // Получение книги по ISBN
    @Transactional(readOnly = true)
    public Book getBookByIsbn(String isbn) {
        Optional<Book> book = bookRepository.findByIsbn(isbn);
        return book.orElseThrow(BookNotFoundException::new);
    }

    // Добавление новой книги (свободной)
    public Book addBook(Book book) {
        Optional<Book> existingBook = bookRepository.findByIsbn(book.getIsbn());
        if (existingBook.isPresent()) {
            throw new BookAlreadyExistException();
        }
        Book savedBook = bookRepository.save(book);
        libraryService.addBookAsAvailable(savedBook.getId());
        return savedBook;
    }

    //Обновление информации о книше по id
    public Book updateBook(int id, Book updatedBook) {
        return bookRepository.findById(id)
                .map(book -> {
                    book.setIsbn(updatedBook.getIsbn());
                    book.setTitle(updatedBook.getTitle());
                    book.setGenre(updatedBook.getGenre());
                    book.setDescription(updatedBook.getDescription());
                    book.setAuthor(updatedBook.getAuthor());
                    return bookRepository.save(book);
                })
                .orElseThrow(BookNotFoundException::new);
    }

    // Удаление книги
    public void deleteBook(int id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException();
        }
        bookRepository.deleteById(id);
        libraryService.deleteLibrary(id);
    }
}
