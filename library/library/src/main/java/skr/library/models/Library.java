package skr.library.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "library")
public class Library {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_id")
    private int bookId;

    @Column(name = "taken_at")
    private LocalDateTime takenAt;

    @Column(name = "return_by")
    private LocalDateTime returnBy;

    public Library() {
    }

    public Library(int bookId, LocalDateTime takenAt, LocalDateTime returnBy) {
        this.bookId = bookId;
        this.takenAt = takenAt;
        this.returnBy = returnBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDateTime getTakenAt() {
        return takenAt;
    }

    public void setTakenAt(LocalDateTime takenAt) {
        this.takenAt = takenAt;
    }

    public LocalDateTime getReturnBy() {
        return returnBy;
    }

    public void setReturnBy(LocalDateTime returnBy) {
        this.returnBy = returnBy;
    }
}
