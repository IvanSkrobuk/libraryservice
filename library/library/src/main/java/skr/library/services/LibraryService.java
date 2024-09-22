package skr.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skr.library.models.Library;
import skr.library.repositories.LibraryRepository;
import skr.library.util.BookNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LibraryService {

    private final LibraryRepository libraryRepository;

    @Autowired
    public LibraryService(LibraryRepository libraryRepository) {
        this.libraryRepository = libraryRepository;
    }

    // Добавление новой свободной книги
    public void addBookAsAvailable(int bookId) {
        Library library = new Library(bookId, null, null);
        libraryRepository.save(library);
    }

    // Взятие книги
    public void takeBook(int bookId) {
        Library library = libraryRepository.findByBookId(bookId);
        if (library != null) {
            library.setTakenAt(LocalDateTime.now());
            library.setReturnBy(LocalDateTime.now().plusMinutes(5));
            libraryRepository.save(library);
        } else {
            throw new BookNotFoundException();
        }
    }

    // Получение списка свободных книг
    @Transactional(readOnly = true)
    public List<Library> getAvailableBooks() {
        LocalDateTime now = LocalDateTime.now();
        return libraryRepository.findAll().stream()
                .filter(library -> library.getTakenAt() == null || (library.getReturnBy() != null && library.getReturnBy().isBefore(now)))
                .toList();
    }

    // Удаление книги по ID
    public void deleteLibrary(int bookId) {
        Library library = libraryRepository.findByBookId(bookId);
        if (library != null) {
            libraryRepository.delete(library);
        } else {
            throw new BookNotFoundException();
        }
    }
}
