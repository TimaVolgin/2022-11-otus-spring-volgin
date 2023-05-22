package ru.otus.spring.volgin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.spring.volgin.domain.User;

import java.util.Optional;

/**
 * Репозиторий для работы с пользователями
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Поиск по имени пользователя
     * @param userName имя
     * @return пользователь
     */
    Optional<User> findByUserName(String userName);
}

