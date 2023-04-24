import {OnlyInstantiableByContainer, Singleton} from "typescript-ioc";
import axios from "axios";
import {SpringPageable, TableData} from "@/ts/components/PageableTable";
import {Notification} from "element-ui";

/**
 * Сервис по работе с книгами
 */
@Singleton
@OnlyInstantiableByContainer
export class BookService {

    /**
     * Возвращает книгу по идентификатору
     * @param id идентификатор книги
     */
    async getBookById(id: string): Promise<BookWithComments> {
        return (await axios.get(`/book/${id}`)).data;
    }

    /**
     * Возвращает список всех книг
     * @param pageable запрос с пагинацией
     */
    async loadBooks(pageable: SpringPageable): Promise<TableData<Book>> {
        return (await axios.get("/books", {
            params: pageable
        })).data;
    }

    /**
     * Удаляет книгу
     * @param id идентификатор книги
     */
    async deleteBook(id: string): Promise<void> {
        await axios.delete(`/book/${id}`);
        Notification.success("Книга успешно удалена");
    }

    /**
     */
    async saveOrUpdateBook(book: Book): Promise<Book> {
        const savedBook = (await axios.post<Book>("/book", book)).data;
        Notification.success("Книга успешно сохранена");
        return savedBook;
    }

    /**
     * Добавляет комментарий к книге
     * @param comment комментарий
     * @param bookId  идентификатор книги
     */
    async addComment(comment: BookComment, bookId: string): Promise<void> {
        return (await axios.post(`/book/${bookId}/comment`, comment));
    }
}

/** Книга с комментариями */
export type BookWithComments = Book & {
    /** Комментарии */
    comments: BookComment[];
}

/** Данные о книге */
export type Book = {
    /** Идентификатор книги */
    id: string | null;
    /** Заголовок */
    title: string;
    /** Дата публикации */
    published: string;
    /** Жанр */
    genre: {
        /** Идентификатор жанра */
        id: string | null;
        /** Название жанра */
        name: string;
    }
    /** Автор */
    author: {
        /** Идентификатор автора */
        id: string | null;
        /** Название автора */
        fio: string;
    }
}

/** Комментарий к книге */
export type BookComment = {
    /** Идентификатор комментария */
    id: string | null;
    /** Текст комментария */
    text: string;
}
