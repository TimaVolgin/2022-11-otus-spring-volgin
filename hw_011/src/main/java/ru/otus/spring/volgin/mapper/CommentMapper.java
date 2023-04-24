package ru.otus.spring.volgin.mapper;

import org.mapstruct.Mapper;
import ru.otus.spring.volgin.domain.Comment;
import ru.otus.spring.volgin.dto.CommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment bookComment);

    List<CommentDto> toDto(List<Comment> genres);
}
