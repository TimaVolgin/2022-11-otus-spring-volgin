package ru.otus.spring.volgin.mapper;

import org.mapstruct.Mapper;
import ru.otus.spring.volgin.domain.Book;
import ru.otus.spring.volgin.dto.BookDto;
import ru.otus.spring.volgin.dto.BookWithCommentsDto;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AuthorMapper.class, GenreMapper.class})
public interface BookMapper {

    BookDto toDto(Book book);

    BookWithCommentsDto toDtoWithComments(Book book);

    List<BookDto> toDto(List<Book> books);

    Book fromDto(BookDto bookDto);
}
