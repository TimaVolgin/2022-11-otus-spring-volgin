--liquibase formatted sql

--changeset VolginTA: demoData context:prod
INSERT INTO authors(id, fio, birthday)
VALUES (1, 'Александр Сергеевич Пушкин', '1799-06-06'),
       (2, 'Михаил Юрьевич Лермонтов', '1814-10-15');
ALTER TABLE authors ALTER COLUMN id RESTART WITH 3;

INSERT INTO genres(id, name)
VALUES (1, 'Поэма'),
       (2, 'Повесть'),
       (3, 'Роман'),
       (4, 'Баллада');
ALTER TABLE genres ALTER COLUMN id RESTART WITH 5;

INSERT INTO books (title, published, genre_id, author_id)
VALUES ('Руслан и Людмила', '1820-04-01', 1, 1),
       ('Пиковая дама', '1834-04-01', 2, 1),
       ('Евгений Онегин', '1831-10-05', 3, 1),
       ('Бородино', '1812-09-07', 4, 2),
       ('Герой нашего времени', '1839-03-01', 3, 2);

INSERT INTO comments(id, text, book_id)
VALUES (1, 'Всем советую прочесть', 1),
       (2, 'Замечательная книга', 1),
       (3, 'Не забываемые впечатления от прочтения', 1),
       (4, 'Серьезное произведение', 2),
       (5, 'Есть над чем подумать', 2),
       (6, 'Лучшее из прочитанного', 3),
       (7, 'Советую прочитать каждому', 4),
       (8, 'Очень современно и на злобу дня', 5);
ALTER TABLE comments ALTER COLUMN id RESTART WITH 9;

--changeset VolginTA: testData context:test
INSERT INTO authors(id, fio, birthday)
VALUES (1, 'Александр Сергеевич Пушкин', '1799-06-06'),
       (2, 'Михаил Юрьевич Лермонтов', '1814-10-15'),
       (3, 'Толстой Алексей Константинович', '1817-09-05');
ALTER TABLE authors ALTER COLUMN id RESTART WITH 4;

INSERT INTO genres(id, name)
VALUES (1, 'Поэма'),
       (2, 'Повесть'),
       (3, 'Роман'),
       (4, 'Баллада'),
       (5, 'Трагедия');
ALTER TABLE genres ALTER COLUMN id RESTART WITH 6;

INSERT INTO books(id, title, published, genre_id, author_id)
VALUES (1, 'Руслан и Людмила', '1820-04-01', 1, 1),
       (2, 'Пиковая дама', '1834-04-01', 2, 1),
       (3, 'Евгений Онегин', '1831-10-05', 3, 1),
       (4, 'Бородино', '1812-09-07', 4, 2),
       (5, 'Герой нашего времени', '1839-03-01', 3, 2),
       (6, 'Царь Борис', '1870-01-01', 5, 3),
       (7, 'Смерть Иоанна Грозного', '1865-01-01', 5, 3);
ALTER TABLE books ALTER COLUMN id RESTART WITH 8;

INSERT INTO comments(id, text, book_id)
VALUES (1, 'Всем советую прочесть', 1),
       (2, 'Замечательная книга', 1),
       (3, 'Не забываемые впечатления от прочтения', 1),
       (4, 'Серьезное произведение', 2),
       (5, 'Есть над чем подумать', 2),
       (6, 'Лучшее из прочитанного', 3),
       (7, 'Советую прочитать каждому', 4),
       (8, 'Очень современно и на злобу дня', 5),
       (9, 'Книга книг', 3),
       (10, 'Задумался', 4),
       (11, 'Не пожалел что потратил время на прочтение', 2);
ALTER TABLE comments ALTER COLUMN id RESTART WITH 12;

