package skr.library.repositories;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import skr.library.models.Book;

@FeignClient(name = "book-service") // Укажите имя вашего сервиса
public interface BookClient {

    @GetMapping("/api/books/{id}")
    Book getBookById(@PathVariable("id") int id);
}
