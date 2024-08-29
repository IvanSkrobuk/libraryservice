package skr.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import skr.library.models.Library;
import skr.library.repositories.LibraryRepository;

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
        }
    }

    // Получение списка свободных книг
    @Transactional(readOnly = true)
    public List<Library> getAvailableBooks() {
        return libraryRepository.findAllByTakenAtIsNull();
    }

    // Удаление library по id книги
    @Transactional
    public void deleteLibrary(int bookId) {
        Optional<Library> libraryEntry = Optional.ofNullable(libraryRepository.findByBookId(bookId));
        libraryEntry.ifPresent(libraryRepository::delete);
    }
}
