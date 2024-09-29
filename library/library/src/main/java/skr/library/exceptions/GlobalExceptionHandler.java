package skr.library.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<BookErrorResponse> handleBookNotFoundException(BookNotFoundException ex) {
        BookErrorResponse response = new BookErrorResponse("Book not found", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyExistException.class)
    public ResponseEntity<BookErrorResponse> handleBookAlreadyExistException(BookAlreadyExistException ex) {
        BookErrorResponse response = new BookErrorResponse("Book already exists", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BookErrorResponse> handleGenericException(Exception ex) {
        BookErrorResponse response = new BookErrorResponse("An error occurred", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
