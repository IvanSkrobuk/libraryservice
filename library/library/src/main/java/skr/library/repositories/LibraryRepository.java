package skr.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import skr.library.models.Library;
import java.time.LocalDateTime;
import java.util.List;

public interface LibraryRepository extends JpaRepository<Library, Integer> {
    Library findByBookId(int bookId);
    List<Library> findAllByTakenAtIsNull();

}
