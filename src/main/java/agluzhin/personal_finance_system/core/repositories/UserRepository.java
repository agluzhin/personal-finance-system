package agluzhin.personal_finance_system.core.repositories;

import agluzhin.personal_finance_system.core.entities.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Данный класс содержит всю информацию о коллекции экземпляров класса "User".
 * Используется для манипуляции данной коллекцией.
 */
@Component
public class UserRepository {
    // Логгер, используемый для вывода в консоль информации о работе класса "UserRepository".
    private static final Logger LOG = LoggerFactory.getLogger(UserRepository.class);

    // Присвоение хранилища "пользователей".
    private static final Map<String, User> USER_STORAGE = InMemoryDataStorage.users;

    /**
     * Метод получения "пользователя" по значению его "id".
     * @param id уникальный идентификатор "пользователя";
     * @return экземпляр класса "User".
     */
    public static User getUserById(String id) {
        LOG.info(" ===== GETTING USER BY ID ===== ");

        User user = USER_STORAGE.get(id);

        if (user != null) {
            String loggingMessage = String.format(
                    "SUCCESS: user with id '%s' was got",
                    id
            );
            LOG.info(loggingMessage);
        }

        return user;
    }

    /**
     * Метод получения "пользователя" по значению его "login'а".
     * @param login значение логина "пользователя";
     * @return экземпляр класса "User".
     */
    public static User getUserByLogin(String login) {
        LOG.info(" ===== GETTING USER BY LOGIN ===== ");

        User targetUser = null;
        for (User user : USER_STORAGE.values()) {
            if (user.getLogin().equals(login)) {
                targetUser = user;
            }
        }

        if (targetUser != null) {
            String loggingMessage = String.format(
                    "SUCCESS: user with login '%s' was got",
                    login
            );
            LOG.info(loggingMessage);
        }

        return targetUser;
    }

    /**
     * Метод получения всех "пользователей" из коллекции.
     * @return коллекция экземпляров класса "User".
     */
    public static Map<String, User> getAllUsers() {
        LOG.info(" ===== GETTING ALL USERS ===== ");

        if (USER_STORAGE != null ){
            LOG.info("SUCCESS: all users were got");
        }

        return USER_STORAGE;
    }

    /**
     * Метод добавления "пользователя" по его "id".
     * @param id уникальный идентификатор "пользователя";
     * @param user экземпляр класса "User".
     */
    public static void addUserById(String id, User user) {
        LOG.info(" ===== ADDING NEW USER BY ID ===== ");

        USER_STORAGE.put(id, user);

        String loggingMessage = String.format(
                "SUCCESS: user with id '%s' was added",
                id
        );
        LOG.info(loggingMessage);
    }

    /**
     * Метод обновления "пользователя" по его "id".
     * @param id уникальный идентификатор "пользователя";
     * @param user экземпляр класса "User".
     */
    public static void updateUserById(String id, User user) {
        LOG.info(" ===== UPDATING USER BY ID ===== ");

        USER_STORAGE.replace(id, user);

        String loggingMessage = String.format(
                "SUCCESS: user with id '%s' was updated",
                id
        );
        LOG.info(loggingMessage);
    }
}
