package agluzhin.personal_finance_system.core.entities.user;

import agluzhin.personal_finance_system.core.utils.PasswordUtil;

import java.util.UUID;

/**
 * Данный класс представляет собой сущность "User" (пользователь). Имеет следующие параметры:
 * уникальный идентификатор (id), логин (login), хэш пароля (password), статус активности (isActive),
 * статус авторизации (isAuthorized), уникальный идентификатор "кошелька" (walletId).
 */
public class User {
    String id;
    String login;
    String password;
    boolean isActive;
    boolean isAuthorize;
    String walletId;

    /**
     * Конструктор создания экземпляра класса "User".
     * @param login входное значение логина, полученное от клиента;
     * @param password входное значение пароля, полученное от клиента.
     */
    public User(String login, String password) {
        // Присвоение случайного идентификатора.
        id = UUID.randomUUID().toString();
        // Присвоение значения логина.
        this.login = login;
        // Присвоение хеш-значения пароля.
        this.password = PasswordUtil.encode(password);
        // Присвоение статуса: активен.
        isActive = true;
        // Присвоение статуса: не авторизован.
        isAuthorize = false;
        // Присвоение случайного идентификатора.
        walletId = UUID.randomUUID().toString();
    }


    /**
     * Базовый getter для уникального идентификатора "пользователя".
     * @return значение по полю "id".
     */
    public String getId() {
        return id;
    }

    /**
     * Базовый getter для логина "пользователя".
     * @return значение по полю "login".
     */
    public String getLogin() {
        return login;
    }

    /**
     * Базовый getter для хэша пароля "пользователя".
     * @return значение по полю "password".
     */
    public String getPassword() {
        return password;
    }

    /**
     * Базовый getter для уникального идентификатора "кошелька".
     * @return значение по полю "walletId".
     */
    public String getWalletId() {
        return walletId;
    }

    /**
     * Базовый getter для статуса активности "пользователя".
     * @return значение по полю "isActive".
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Базовый setter для статуса активности "пользователя".
     * @param isActive входное значение для поля "isActive" (true/false).
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Базовый getter для статуса авторизации "пользователя".
     * @return значение по полю "isAuthorize".
     */
    public boolean getIsAuthorize() {
        return isAuthorize;
    }

    /**
     * Базовый setter для статуса авторизации "пользователя".
     * @param isAuthorized  входное значение для поля "isAuthorize" (true/false).
     */
    public void setIsAuthorize(boolean isAuthorized) {
        this.isAuthorize = isAuthorized;
    }
}
