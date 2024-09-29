package skr.library.mapper;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import skr.library.dto.BookDto;
import skr.library.models.Book;


@Component
public class BookMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BookDto convertToDto(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    public Book convertToEntity(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
