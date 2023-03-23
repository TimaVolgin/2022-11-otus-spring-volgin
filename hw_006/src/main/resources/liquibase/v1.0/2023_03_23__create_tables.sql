--liquibase formatted sql

--changeset VolginT:createTables
CREATE TABLE genres (
    id   BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE authors (
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    fio      VARCHAR(255) NOT NULL,
    birthday DATE NOT NULL
);

CREATE TABLE books (
    id  BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    published DATE NOT NULL,
    genre_id BIGINT,
    author_id BIGINT,
    FOREIGN KEY (genre_id) REFERENCES genres(id),
    FOREIGN KEY (author_id) REFERENCES authors(id)
);

CREATE TABLE comments
(
    id      BIGINT PRIMARY KEY AUTO_INCREMENT,
    text    TEXT NOT NULL,
    book_id BIGINT,
    FOREIGN KEY (book_id) REFERENCES books (id)
);
