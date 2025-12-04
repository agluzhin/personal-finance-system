package agluzhin.personal_finance_system.core.entities.user;

import agluzhin.personal_finance_system.core.entities.wallet.Wallet;
import agluzhin.personal_finance_system.core.utils.PasswordUtil;

import java.util.UUID;

/**
 * Данный класс представляет собой сущность "User" (пользователь).
 * <p>Используется для хранения:</p>
 * - уникального идентификатора (userId);<br>
 * - логина (login);<br>
 * - хэшированного пароля (password);<br>
 * - статуса активности (isActive);<br>
 * - статуса авторизации (isAuthorized);<br>
 * - сущности "кошелек" (wallet).
 */
public class User {
    String userId;
    String login;
    String password;
    boolean isActive;
    boolean isAuthorized;
    Wallet wallet;

    public User(String login, String password) {
        userId = UUID.randomUUID().toString();
        this.login = login;
        this.password = PasswordUtil.encode(password);
        isActive = true;
        isAuthorized = false;
        wallet = new Wallet();
    }


    /**
     * Базовый getter для уникального идентификатора (userId).
     * @return значение userId.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Базовый getter для логина (login).
     * @return значение login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Базовый getter для пароля (password).
     * @return значение password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Базовый getter для кошелька (wallet).
     * @return сущность wallet.
     */
    public Wallet getWallet() {
        return wallet;
    }

    /**
     * Базовый getter для статуса активности (isActive).
     * @return значение isActive.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Базовый setter для статуса активности (isActive).
     * @param isActive принимает значения true/false.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Базовый getter для статуса авторизации (isAuthorized).
     * @return значение isAuthorized.
     */
    public boolean getIsAuthorized() {
        return isAuthorized;
    }

    /**
     * Базовый setter для статуса авторизации (isAuthorized).
     * @param isAuthorized принимает значения true/false.
     */
    public void setIsAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
    }
}
