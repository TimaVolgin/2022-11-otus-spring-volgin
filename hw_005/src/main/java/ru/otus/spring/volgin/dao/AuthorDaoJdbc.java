package ru.otus.spring.volgin.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring.volgin.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * DAO по работе с авторами через JDBC
 */
@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbc implements AuthorDao {

    /** Запрос на вставку нового автора */
    private static final String INSERT_AUTHOR = "INSERT INTO authors (name, birthday) VALUES (:name, :birthday)";
    /** Запрос на получение всех авторов */
    private static final String SELECT_AUTHORS = "SELECT id, name, birthday FROM authors";
    /** Запрос на поиск автора по идентификатору */
    private static final String SELECT_AUTHOR_BY_ID = "SELECT id, name, birthday FROM authors WHERE id  = :id";
    /** Маппер автора */
    private final AuthorMapper authorMapper = new AuthorMapper();
    /** Компонент для работы с jdbc */
    private final NamedParameterJdbcOperations jdbcOperations;

    @Override
    public Author save(Author author) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcOperations.update(INSERT_AUTHOR, new MapSqlParameterSource(
                Map.of("name", author.getFio(),
                        "birthday", author.getBirthday())),
                keyHolder
        );
        author.setId(keyHolder.getKeyAs(Long.class));
        return author;
    }

    @Override
    public List<Author> findAll() {
        return jdbcOperations.query(SELECT_AUTHORS, authorMapper);
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(jdbcOperations.query(SELECT_AUTHOR_BY_ID, Map.of("id", id),
                rs -> rs.next() ? authorMapper.mapRow(rs, 1) : null));
    }

    private static class AuthorMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            LocalDate birthday = rs.getDate("birthday").toLocalDate();
            return Author.builder()
                    .id(id)
                    .fio(name)
                    .birthday(birthday)
                    .build();
        }
    }
}
