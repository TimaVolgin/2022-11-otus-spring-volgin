package ru.otus.spring.volgin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring.volgin.domain.document.AuthorDoc;
import ru.otus.spring.volgin.domain.document.BookDoc;
import ru.otus.spring.volgin.domain.document.CommentDoc;
import ru.otus.spring.volgin.domain.document.GenreDoc;
import ru.otus.spring.volgin.domain.entity.Author;
import ru.otus.spring.volgin.domain.entity.Book;
import ru.otus.spring.volgin.domain.entity.Comment;
import ru.otus.spring.volgin.domain.entity.Genre;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 * Конвертер сущностей разных БД
 */
@Mapper(componentModel = "spring")
public abstract class EntityMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Mapping(target = "previousId", source = "id")
    @Mapping(target = "id", ignore = true)
    public abstract AuthorDoc toAuthorDocument(Author author);

    @Mapping(target = "previousId", source = "id")
    @Mapping(target = "id", ignore = true)
    public abstract GenreDoc toGenreDocument(Genre genre);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "genre", qualifiedByName = "findGenreByPreviousId")
    @Mapping(target = "author", qualifiedByName = "findAuthorByPreviousId")
    public abstract BookDoc toBookDocument(Book book);

    @Mapping(target = "id", ignore = true)
    public abstract CommentDoc toBookComment(Comment bookComment);

    @Named("findGenreByPreviousId")
    protected GenreDoc findGenreByPreviousId(Genre genre) {
        return mongoTemplate.findOne(query(where("previousId").is(genre.getId())), GenreDoc.class);
    }

    @Named("findAuthorByPreviousId")
    protected AuthorDoc findAuthorByPreviousId(Author genre) {
        return mongoTemplate.findOne(query(where("previousId").is(genre.getId())), AuthorDoc.class);
    }
}
